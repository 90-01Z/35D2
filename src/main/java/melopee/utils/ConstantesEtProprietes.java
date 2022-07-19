package melopee.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ConstantesEtProprietes {

    /**
     * Cet attribut contient un chemin absolu vers le dossier où doivent se trouver
     * les fichiers pour charger les nomenclatures. Normalement l'attribut doit être
     * valorisé par la propriété fr.insee.melauto.dossierFichiersNomenclatures qui
     * est définie dans un fichier de propriétés chargé par Spring (cf. classe
     * melopee.Application) Le chemin absolu vers le dossier doit être exprimé
     * suivant les règles de nommage de l'OS qui exécute l'application.
     */
    @Value("${fr.insee.melauto.dossierFichiersNomenclatures:#{null}}")
    private String dossierFichiersNomenclatures;

	@Value("${fr.insee.melauto.origine:#{null}}")
	private String origine;
    //
    // Types de nomenclatures possibles
    //
    public static final String NOMENC_FORMATION = "formation";
    public static final String NOMENC_DIPLOME = "diplome";
    public static final String NOMENC_PROFESSION_HOMME = "profession";
    public static final String NOMENC_PROFESSION_FEMME = "professionf";
    public static final String NOMENC_INCONNUE = "nomenclature-inconnue";

    //
    // Controller
    //
    public static final String URL_ACCUEIL = "/";
    public static final String URL_ERROR = URL_ACCUEIL + "error";
    public static final String URL_RECHERCHE_ECHOS = "/{nomenclature}";
    public static final String URL_HEALTHCHECK = URL_ACCUEIL + "healthcheck";
    public static final String PATH_VARIABLE_RECHERCHE_ECHO = "nomenclature";
    public static final String NOM_PARAM_SAISIE = "saisie";
    public static final String NOM_PARAM_CHAINE_VALIDEE = "enter";
    public static final String NOM_PARAM_PROBA_DEMANDEE = "probabilite";
    public static final String CONTENT_HOME_PAGE =
        "API de recherche dans nomenclature diplome, formations et professions pour MELOPEE";

    //
    // Fichiers
    //
    // Propriete PATH_RESOURCES_NOMENCLATURES à externaliser
    public static final String DIRECTORY_RESOURCES_FR = "fr";
    public static final String DIRECTORY_RESOURCES_INSEE = "insee";
    public static final String DIRECTORY_RESOURCES_NOMENCLATURES = "nomenclatures";
    public static final String EXTENSION_CSV = ".csv";
    public static final String PREF_FICHIER_EXCLUSIONS = "exclusions-";
    public static final Integer NB_CHAMPS_FICHIER_NOMENCLATURE = 2;

    //
    // RG pour conversion des chaînes de caractères
    //
    public static final String REGEX_CARACTERES_SPECIAUX = "(\\W)";
    public static final String ASCII_PLAGE_ACCENTS = "[\u0300-\u036F]";
    public static final String CARACTERE_ESPACE = " ";
    public static final String CARACTERE_SEPARATION_CSV = ";";
    public static final Integer TAILLE_MIN_MOT_EXPLOITABLE = 1;
    public static final String REGEX_CHIFFRES = "(\\d)";

    public static final String FORMAT_DUREE_REQUETE = "SSSSS";

}
