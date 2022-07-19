package melopee.enumeration;

import lombok.Getter;

public enum EtatEcho {

    OK("ok"),
    KO_RECHERCHE("Aucune correspondance trouvée"),
    KO_FONCTIONNEL("Saisie non exploitable"),
    KO_FICHIER("Impossible de charger le fichier de nomenclature"),
    KO_URL("L'url n'existe pas");

    @Getter
    private String libelle;

    private EtatEcho(String libelle) {
        this.libelle = libelle;
    }

}
