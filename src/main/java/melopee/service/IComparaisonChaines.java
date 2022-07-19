package melopee.service;

import java.util.List;

import melopee.model.LigneNomenclature;
import melopee.model.Nomenclature;

public interface IComparaisonChaines {

    /**
     * Vérifie que les listes contiennent bien des éléments pour la recherche<br>
     * Si oui appelle la méthode de recherche
     * @param nomenclature
     * @param recherche
     * @return une liste @Echo Peut être vide si aucun écho trouvé
     */
    public List<LigneNomenclature> rechercherLesEchos(Nomenclature nomenclature, String recherche);

    /**
     * Non implemente
     * @param saisieTransformee
     * @return
     */
    public List<LigneNomenclature> appliquerAlgoDeCorrection(String saisieTransformee);

}
