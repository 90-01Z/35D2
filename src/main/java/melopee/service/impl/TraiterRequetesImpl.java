package melopee.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import melopee.enumeration.EtatEcho;
import melopee.enumeration.NomenclatureType;
import melopee.model.DeroulementRequete;
import melopee.model.Echo;
import melopee.model.LigneNomenclature;
import melopee.model.Nomenclature;
import melopee.model.RetourEchos;
import melopee.repository.NomenclatureRepository;
import melopee.service.IComparaisonChaines;
import melopee.service.ITraiterRequetes;
import melopee.utils.ConstantesEtProprietes;
import melopee.utils.ConversionChaine;

@RequiredArgsConstructor(onConstructor_ = { @Autowired })
@Service
public class TraiterRequetesImpl implements ITraiterRequetes {

	private static final Logger LOGGER = LogManager.getLogger(TraiterRequetesImpl.class);

	@NonNull
	private IComparaisonChaines comparaisonChaines;

	@NonNull
	private NomenclatureRepository nomenclatureRepository;

	@Override
	public RetourEchos traiterRequeteDeRechercheDansNomenclature(String saisie, Boolean probaDemandee,
			NomenclatureType type) {

		// Declaration des variables
		Long debutTraitement = System.currentTimeMillis();
		List<LigneNomenclature> listeDeNomenclatureEcho = new ArrayList<>();
		Nomenclature nomenclature = null;

		// Instanciation de l'objet DeroulementRequete
		DeroulementRequete deroulementRequete = new DeroulementRequete(saisie, (saisie != null && saisie.length() > 0),
				type != null && type != NomenclatureType.INTROUVABLE);

		// Opérations de chargements de fichiers et conversions des chaînes de
		// caractères
		if (deroulementRequete.isSaisieExiste() && deroulementRequete.isRequeteExiste()) {
			nomenclature = nomenclatureRepository.find(type);
			deroulementRequete.setFichierNomenclatureCharge(nomenclature != null);
			deroulementRequete.setNomenclature(nomenclature);

			deroulementRequete
					.setChaineRecherchee(this.transformerLaSaisiePourRechercheDansNomenclature(nomenclature, saisie));

			deroulementRequete.setSaisieEstExploitable(deroulementRequete.getChaineRecherchee() != null
					&& !deroulementRequete.getChaineRecherchee().isEmpty());

			// Si le fichier est présent et la saisie exploitable, opération de comparaison
			if (deroulementRequete.isFichierNomenclatureCharge() && deroulementRequete.isSaisieEstExploitable()) {
				listeDeNomenclatureEcho = comparaisonChaines.rechercherLesEchos(nomenclature,
						deroulementRequete.getChaineRecherchee());
			} else {
				LOGGER.info(() -> deroulementRequete + " pour nomenclature " + type + " ne peut etre traitee");
			}
		} else {
			LOGGER.info(() -> deroulementRequete + " pour nomenclature " + type + " inexploitable");
		}

		deroulementRequete.setAuMoinsUnEchoTrouve(!listeDeNomenclatureEcho.isEmpty());

		return this.construireLeRetourEcho(deroulementRequete, probaDemandee, listeDeNomenclatureEcho, nomenclature,
				debutTraitement);

	}

	private String transformerLaSaisiePourRechercheDansNomenclature(Nomenclature nomenclature, String saisie) {

		String saisieConvertie = ConversionChaine.conversionChainePourComparaison(saisie);
		return nomenclature.supprimerMotsAExclure(saisieConvertie);
	}

	private RetourEchos construireLeRetourEcho(DeroulementRequete deroulementRequete, boolean probaDemandee,
			List<LigneNomenclature> lignesNomenclaturesCorrespondantes, Nomenclature nomenclature,
			Long debutTraitement) {

		RetourEchos retour = new RetourEchos();
		int nbMotsSaisis;
		if (deroulementRequete.isSaisieEstExploitable()) {
			nbMotsSaisis = (deroulementRequete.getChaineRecherchee() == null ? ""
					: deroulementRequete.getChaineRecherchee()).split(ConstantesEtProprietes.CARACTERE_ESPACE).length;
		} else {
			nbMotsSaisis = 0;
		}
		// Mise à jour de toutes les variables de l'objet RetourEcho

		if (probaDemandee && nomenclature != null) {
			lignesNomenclaturesCorrespondantes
					.forEach(l -> l.setLigneCorrigee(nomenclature.supprimerMotsAExclure(l.getLigneCorrigee())));
		}

		retour.setListeEcho(this.ajouterEcho(lignesNomenclaturesCorrespondantes, probaDemandee, nbMotsSaisis));

		retour.setNbEcho((retour.getListeEcho() != null) ? retour.getListeEcho().size() : 0);

		retour.setDeroulementRequete(deroulementRequete);

		retour.setStatutRequete(this.retournerEtatEcho(deroulementRequete));

		// Calcul de la durée de la requête
		deroulementRequete.setDureTraitementEnMs(new SimpleDateFormat(ConstantesEtProprietes.FORMAT_DUREE_REQUETE)
				.format(System.currentTimeMillis() - debutTraitement));

		retour.setDeroulementRequete(deroulementRequete);

		return retour;
	}

	private List<Echo> ajouterEcho(List<LigneNomenclature> lignesNomenclaturesCorrespondantes, boolean probaDemandee,
			int nbMotsSaisis) {
		List<Echo> retour = new ArrayList<>();

		if (probaDemandee) {
			for (LigneNomenclature l : lignesNomenclaturesCorrespondantes) {
				int nbMotsNomenclatureExploitable = l.getLigneCorrigee()
						.split(ConstantesEtProprietes.CARACTERE_ESPACE).length;
				retour.add(new Echo(this.calculCorrespondanceItem(nbMotsSaisis, nbMotsNomenclatureExploitable),
						l.getIdNomenclature(), l.getLibelleNomenclature()));
			}

		} else {
			for (LigneNomenclature l : lignesNomenclaturesCorrespondantes) {
				retour.add(new Echo(l.getIdNomenclature(), l.getLibelleNomenclature()));
			}

		}

		return retour;
	}

	@Override
	public EtatEcho retournerEtatEcho(DeroulementRequete deroulementRequete) {

		EtatEcho etat;

		if (!deroulementRequete.isRequeteExiste()) {
			etat = EtatEcho.KO_URL;
		} else if (!deroulementRequete.isFichierNomenclatureCharge()) {
			etat = EtatEcho.KO_FICHIER;
		} else if (!(deroulementRequete.isSaisieExiste() && deroulementRequete.isSaisieEstExploitable())) {
			etat = EtatEcho.KO_FONCTIONNEL;
		} else if (!deroulementRequete.isAuMoinsUnEchoTrouve()) {
			etat = EtatEcho.KO_RECHERCHE;
		} else {
			etat = EtatEcho.OK;
		}
		return etat;
	}

	@Override
	public Double calculCorrespondanceItem(Integer nbDeMotsSaisis, Integer nbMotsNomenclatureExploitable) {

		return ((double) nbDeMotsSaisis) / nbMotsNomenclatureExploitable;
	}

}
