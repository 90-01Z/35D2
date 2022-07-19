package melopee.service;

import melopee.enumeration.EtatEcho;
import melopee.enumeration.NomenclatureType;
import melopee.model.DeroulementRequete;
import melopee.model.RetourEchos;

public interface ITraiterRequetes {

    /**
     * Vérifie que la nomenclature est bien chargée et qu'il y a des mots à rechercher<br>
     * Si oui, appelle la méthode pour rechercher les échos dans la nomenclature
     * @param saisie : chaine de caracteres à rechercher dans la nomenclature
     * @param saisieTermine : boolean indiquant si l'utilisateur a termine sa saisie
     * @param type : type de nomenclature dans laquelle faire la rechercher
     * @return @RetourEchos entièrement mis à jour
     */
    public RetourEchos traiterRequeteDeRechercheDansNomenclature(
        String saisie,
        Boolean probaDemandee,
        NomenclatureType type);

    /**
     * Détermine l'état de la recherche :
     * Si erreur d'ans l'accès URL EtatEcho = <em>KO_URL</em><br>
     * Si erreur d'ans l'accès aux fichiers EtatEcho = <em>KO_FICHIER</em><br>
     * Si aucun mot exploitable dans la saisie EtatEcho = <em>KO_FONCTIONNEL</em><br>
     * Si un appel à l'algo a été tenté EtatEcho = <em>SUGGESTION</em><br>
     * Si aucun libelle correspondant trouve EtatEcho = <em>KO_RECHERCHE</em><br>
     * Sinon <em>OK</em><br>
     * @param @DeroulementRequete
     * @return @EtatEcho
     */
    EtatEcho retournerEtatEcho(DeroulementRequete deroulementRequete);

    /**
     * Calcule une probabilite entre 0 et 1 pour que la profession recherchée par l'enquêté soit bien celle proposee
     * En attente d'une vraie RG, la probabilite correspond au pourcentage de mots saisis trouves dans le libelle
     * Si tous les mots saisis correspondent à tous les mots du libelle, la proba vaut 100
     * @param nbDeMotsSaisis
     * @param libelleTrouve : mots exploitables de la nomenclature
     * @return un chiffre compris entre 0 et 1
     * <em>Pour des raisons de perf, les mots à exclure n'ont pas été corrigé dans le libellé</em>
     * <em>Le calcul de la probabilité peut être affecté</em>
     */
    public Double calculCorrespondanceItem(Integer nbDeMotsSaisis, Integer nbMotsNomenclatureExploitable);

}
