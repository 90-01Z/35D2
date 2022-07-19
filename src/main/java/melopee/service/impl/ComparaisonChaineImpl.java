package melopee.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import melopee.model.LigneNomenclature;
import melopee.model.Nomenclature;
import melopee.service.IComparaisonChaines;
import melopee.utils.ConstantesEtProprietes;

@Service
public class ComparaisonChaineImpl implements IComparaisonChaines {

    @Override
    public List<LigneNomenclature> rechercherLesEchos(Nomenclature nomenclature, String recherche) {

	List<LigneNomenclature> retour = new ArrayList<>();

	if ((nomenclature != null && recherche != null && !recherche.isEmpty())) {
	    retour = this.rechercherCorrespondanceExacteDesMots(nomenclature, recherche);
	}

	return retour;
    }

    /**
     * Pour chaque ligne de la nomenclature, cherche un libellé contenant tous les
     * mots recherchés
     * 
     * @param nomenclature
     * @param recherche
     * @return une liste @LigneNomenclature Peut être vide si aucun écho trouvé
     */
    protected List<LigneNomenclature> rechercherCorrespondanceExacteDesMots(Nomenclature nomenclature, String recherche) {

	List<LigneNomenclature> listeDesEchosTrouves = new ArrayList<>();
	List<LigneNomenclature> listeDesEchosTrouvesMotEnPremier = new ArrayList<>();
	List<LigneNomenclature> listeDesEchosTrouvesReste = new ArrayList<>();

	String[] tabMots = recherche.split(ConstantesEtProprietes.CARACTERE_ESPACE);

	nomenclature.forEachLigne(ligneNomenclature -> {
	    String ligneNomenclatureCorrigee = ligneNomenclature.getLigneCorrigee();    
	    if (ligneNomenclatureCorrigee != null && !ligneNomenclatureCorrigee.isEmpty()) {
		String[] tabMotsLigneNomenclatureCorrigee = ligneNomenclatureCorrigee.split(ConstantesEtProprietes.CARACTERE_ESPACE);
		boolean motTrouve = false;
		boolean tousLesMotsTrouves = false;

		for (String motAChercher : tabMots) {
		    for (String motLigneNomenclatureCorrigee : tabMotsLigneNomenclatureCorrigee) {
			if (motLigneNomenclatureCorrigee.startsWith(motAChercher)) {
			    motTrouve = true;
			    break;
			}
		    }
		    if (motTrouve == false) {
			tousLesMotsTrouves = false;
			break;
		    } else {
			tousLesMotsTrouves = true;
		    }
		    motTrouve = false;
		}
		if (tousLesMotsTrouves) {
		    if (tabMots[0].length() <= 2) {
			listeDesEchosTrouvesReste.add(ligneNomenclature);
		    } else {
			if (ligneNomenclatureCorrigee.startsWith(tabMots[0].substring(0, 3))) {
			    listeDesEchosTrouvesMotEnPremier.add(ligneNomenclature);
			} else {
			    listeDesEchosTrouvesReste.add(ligneNomenclature);
			}
		    }
		}

	    }
	});
	listeDesEchosTrouves.addAll(listeDesEchosTrouvesMotEnPremier);
	listeDesEchosTrouves.addAll(listeDesEchosTrouvesReste);
	return listeDesEchosTrouves;

    }

    @Override
    public List<LigneNomenclature> appliquerAlgoDeCorrection(String saisieTransformee) {
	// TODO Auto-generated method stub
	return null;
    }
}
