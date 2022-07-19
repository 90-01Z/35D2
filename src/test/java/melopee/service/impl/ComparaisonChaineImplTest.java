package melopee.service.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import melopee.enumeration.NomenclatureType;
import melopee.model.LigneNomenclature;
import melopee.model.Nomenclature;

@RunWith(SpringJUnit4ClassRunner.class)
public class ComparaisonChaineImplTest {

    @InjectMocks
    ComparaisonChaineImpl comparaison;

    private Nomenclature nomenclatureDeTest;

    @Before
    public void init() {
        nomenclatureDeTest = new Nomenclature(NomenclatureType.PROFESSION_HOMME);
        nomenclatureDeTest.ajouterLigne(new LigneNomenclature("id", "aide-maçon", "AIDE MACON"));
        nomenclatureDeTest.ajouterLigne(new LigneNomenclature("id", "professeur des écoles", "PROFESSEUR ECOLES"));
        nomenclatureDeTest.ajouterLigne(new LigneNomenclature("id", "maçon vérandiste", "MACON VERANDISTE"));
        nomenclatureDeTest.ajouterLigne(new LigneNomenclature("id", "test nomenclature invalide", ""));
        nomenclatureDeTest.ajouterLigne(new LigneNomenclature("id", "test nomenclature invalide", null));

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testerRienARechercher() {
        Nomenclature nomenclature = new Nomenclature(NomenclatureType.PROFESSION_HOMME);
        String recherche = "Coucou";
        assertTrue(
            comparaison.rechercherLesEchos(nomenclature, recherche)
                .isEmpty());
        nomenclature.ajouterLigne(new LigneNomenclature("id", "lib1", "lib2"));
        assertTrue(
            comparaison.rechercherLesEchos(nomenclature, "")
                .isEmpty());
        assertTrue(
            comparaison.rechercherLesEchos(null, recherche)
                .isEmpty());
        assertTrue(
            comparaison.rechercherLesEchos(nomenclature, null)
                .isEmpty());
    }

    @Test
    public void testerRecherchePossible() {
        Nomenclature nomenclature=new Nomenclature(NomenclatureType.PROFESSION_HOMME);
        nomenclature.ajouterLigne(new LigneNomenclature("id", "lib1", "lib2"));
        String recherche = "lib";
        assertTrue(
            ! comparaison.rechercherLesEchos(nomenclature, recherche)
                .isEmpty());
    }

    @Test
    public void testerQqchoseARetourner() {
        String recherche = "MACON";

        assertTrue(
            comparaison.rechercherCorrespondanceExacteDesMots(nomenclatureDeTest, recherche)
                .size() == 2);

    }

    @Test
    public void testerRienARetourner() {

        String recherche = "AUTRE";
        assertTrue(
            comparaison.rechercherCorrespondanceExacteDesMots(nomenclatureDeTest, recherche)
                .isEmpty());
    }

}
