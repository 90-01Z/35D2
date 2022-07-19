package melopee.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import melopee.enumeration.EtatEcho;
import melopee.enumeration.NomenclatureType;
import melopee.model.DeroulementRequete;
import melopee.model.LigneNomenclature;
import melopee.model.Nomenclature;
import melopee.model.RetourEchos;
import melopee.repository.NomenclatureRepository;
import melopee.service.IComparaisonChaines;

@RunWith(SpringJUnit4ClassRunner.class)
public class TraiterRequetesImplTest {

    @InjectMocks
    TraiterRequetesImpl traitementRequete;

    @Mock
    private IComparaisonChaines comparaisonChaine;
    @Mock
    private NomenclatureRepository nomenclatureRepository;

    private Nomenclature nomenclature;
    private List<LigneNomenclature> retourEchosValides;
    private String saisieValide = "Lieutenant de police";
    private String saisieNonValideExploitable = "qqchose";

    

    @Before
    public void init() {
        nomenclature= new Nomenclature(NomenclatureType.PROFESSION_HOMME);
        retourEchosValides = new ArrayList<>();
        nomenclature.ajouterLigne(new LigneNomenclature("id1", "Accordéoniste", "ACCORDEONISTE"));
        nomenclature.ajouterLigne(
            new LigneNomenclature(
                "id2",
                "Adjoint technique de recherche et de formation de l'Éducation nationale (ATRF)",
                "ADJOINT TECHNIQUE RECHERCHE FORMATION EDUCATION NATIONALE ATRF"));
        nomenclature.ajouterLigne(new LigneNomenclature("id3", "Lieutenant de police", "LIEUTENANT POLICE"));
        nomenclature.ajouterLigne(new LigneNomenclature("id4", "Lieutenant de port", "LIEUTENANT PORT"));
        nomenclature
            .ajouterLigne(new LigneNomenclature("id5", "Lieutenant-colonel des pompiers", "LIEUTENANT COLONEL POMPIERS"));

        nomenclature.ajouterMotExclu("DE");

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testerNomenclatureNexistePas() {
        assertTrue(
            ! traitementRequete
                .traiterRequeteDeRechercheDansNomenclature(saisieNonValideExploitable, false, NomenclatureType.INTROUVABLE)
                .getDeroulementRequete()
                .isRequeteExiste());
    }

    @Test
    public void testerPasDeSaisie() {
        assertTrue(
            ! traitementRequete.traiterRequeteDeRechercheDansNomenclature("", false, NomenclatureType.PROFESSION_HOMME)
                .getDeroulementRequete()
                .isSaisieExiste());
    }

    @Test
    public void testerSaisieSansResultatEtNomenclatureExiste() {
        when(nomenclatureRepository.find(NomenclatureType.PROFESSION_HOMME)).thenReturn(nomenclature);
        when(comparaisonChaine.rechercherLesEchos(nomenclature, saisieNonValideExploitable))
            .thenReturn(retourEchosValides);
        RetourEchos retour =
            traitementRequete
                .traiterRequeteDeRechercheDansNomenclature(saisieNonValideExploitable, false, NomenclatureType.PROFESSION_HOMME);
        assertTrue(
            retour.getDeroulementRequete()
                .isSaisieExiste()
                && retour.getDeroulementRequete()
                    .isFichierNomenclatureCharge()
                && retour.getDeroulementRequete()
                    .isSaisieEstExploitable());
    }

    @Test
    public void testerSuppressionDesMotsAExclureDansLaSaisie() {
        String chaineARecherchee = "LIEUTENANT POLICE";
        when(nomenclatureRepository.find(NomenclatureType.PROFESSION_HOMME)).thenReturn(nomenclature);
        /*when(conversion.supprimerMotsAExclureDeLaChaine(motsAExclure, saisieValide.toUpperCase()))
            .thenReturn(chaineARecherchee);*/
        when(comparaisonChaine.rechercherLesEchos(nomenclature, saisieValide)).thenReturn(retourEchosValides);
        RetourEchos retour =
            traitementRequete.traiterRequeteDeRechercheDansNomenclature(saisieValide, false, NomenclatureType.PROFESSION_HOMME);
        assertTrue(
            retour.getDeroulementRequete()
                .getChaineRecherchee()
                .equals(chaineARecherchee));
    }

    @Test
    public void verifierRetourEcho() {
        DeroulementRequete deroulementRequete = new DeroulementRequete("saisie", false, false);
        assertTrue(traitementRequete.retournerEtatEcho(deroulementRequete) == EtatEcho.KO_URL);
        deroulementRequete.setRequeteExiste(true);
        deroulementRequete.setFichierNomenclatureCharge(false);
        assertTrue(traitementRequete.retournerEtatEcho(deroulementRequete) == EtatEcho.KO_FICHIER);
        deroulementRequete.setFichierNomenclatureCharge(true);
        assertTrue(traitementRequete.retournerEtatEcho(deroulementRequete) == EtatEcho.KO_FONCTIONNEL);
        deroulementRequete.setSaisieExiste(true);
        deroulementRequete.setSaisieEstExploitable(false);
        assertTrue(traitementRequete.retournerEtatEcho(deroulementRequete) == EtatEcho.KO_FONCTIONNEL);
        deroulementRequete.setSaisieEstExploitable(true);
        deroulementRequete.setAuMoinsUnEchoTrouve(false);
        assertTrue(traitementRequete.retournerEtatEcho(deroulementRequete) == EtatEcho.KO_RECHERCHE);
        deroulementRequete.setAuMoinsUnEchoTrouve(true);
        assertTrue(traitementRequete.retournerEtatEcho(deroulementRequete) == EtatEcho.OK);
    }

    @Test
    public void verifierCalculProbabilite() {
        assertTrue(traitementRequete.calculCorrespondanceItem(2, 4) == 0.5d);
    }

    @Test
    public void verifierProbaCalculeeSiDemandee() {
        String chaineARecherchee = "LIEUTENANT POLICE";
        when(nomenclatureRepository.find(NomenclatureType.PROFESSION_HOMME)).thenReturn(nomenclature);
        /*when(conversion.supprimerMotsAExclureDeLaChaine(motsAExclure, saisieValide.toUpperCase()))
            .thenReturn(chaineARecherchee);*/
        retourEchosValides.add(nomenclature.get(2));
        when(comparaisonChaine.rechercherLesEchos(nomenclature, chaineARecherchee))
            .thenReturn(retourEchosValides);
        /*when(
            conversion.supprimerMotsAExclureDeLaChaine(
                motsAExclure,
                retourEchosValides.get(0)
                    .getLigneCorrigee())).thenReturn(
                        retourEchosValides.get(0)
                            .getLigneCorrigee());*/
        RetourEchos retour =
            traitementRequete.traiterRequeteDeRechercheDansNomenclature(saisieValide, true, NomenclatureType.PROFESSION_HOMME);
        double proba =
            retour.getListeEcho()
                .get(0)
                .getProbabilite();
        assertTrue(proba > 0);
    }

    @Test
    public void verifierProbaNonCalculeeSiPasDemandee() {
        String chaineARecherchee = "LIEUTENANT POLICE";
        when(nomenclatureRepository.find(NomenclatureType.PROFESSION_HOMME)).thenReturn(nomenclature);
        /*when(conversion.supprimerMotsAExclureDeLaChaine(motsAExclure, saisieValide.toUpperCase()))
            .thenReturn(chaineARecherchee);*/
        retourEchosValides.add(nomenclature.get(2));
        when(comparaisonChaine.rechercherLesEchos(nomenclature, chaineARecherchee))
            .thenReturn(retourEchosValides);
        /*when(
            conversion.supprimerMotsAExclureDeLaChaine(
                motsAExclure,
                retourEchosValides.get(0)
                    .getLigneCorrigee())).thenReturn(
                        retourEchosValides.get(0)
                            .getLigneCorrigee());*/
        RetourEchos retour =
            traitementRequete.traiterRequeteDeRechercheDansNomenclature(saisieValide, false, NomenclatureType.PROFESSION_HOMME);

        assertTrue(
            retour.getListeEcho()
                .get(0)
                .getProbabilite() == null);
    }

}
