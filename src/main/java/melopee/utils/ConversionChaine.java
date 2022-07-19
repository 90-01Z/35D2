package melopee.utils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class ConversionChaine{


    /**
     * Appelle les différentes méthodes servant à transformer les chaines de caractères
     * @param saisie
     * @return list de String (1 String par mot)
     */

    public static String conversionChainePourComparaison(String saisie) {

        List<String> retour = new ArrayList<>();
        String[] tabMotsSaisis = null;

        // Transformation de la chaine de caractères
        if (saisie != null && ! saisie.isEmpty()) {
            String saisieConvertie =
                remplacerCaracteresSpeciauxParEspace(suppressionAccents(saisie))
                    .toUpperCase();
            tabMotsSaisis = saisieConvertie.split(ConstantesEtProprietes.CARACTERE_ESPACE);
        }

        if (tabMotsSaisis != null && tabMotsSaisis.length > 0) {
            for (String s : tabMotsSaisis) {
                if (estUnMotExploitable(s)) {
                    retour.add(s);
                }
            }
        }

        return String.join(ConstantesEtProprietes.CARACTERE_ESPACE, retour);

    }

    /**
     * Prend un mot en paramètre
     * Retourne la même chaîne sans accents
     * @param mot
     * @return String
     */

    public static String suppressionAccents(String mot) {
        String normalized = Normalizer.normalize(mot, Normalizer.Form.NFD);
        return normalized.replaceAll(ConstantesEtProprietes.ASCII_PLAGE_ACCENTS, "");
    }

    /**
     * Prend un mot en paramètre
     * Retourne la même chaîne sans caracteres qui ne soit pas une lettre
     * @param mot
     * @return String
     */

    public static String remplacerCaracteresSpeciauxParEspace(String mot) {
        return mot.replaceAll(ConstantesEtProprietes.REGEX_CARACTERES_SPECIAUX, ConstantesEtProprietes.CARACTERE_ESPACE);
    }

    /**
     * Prend un mot en paramètre
     * Si le mot n'a pas assez de caracteres pour être exploitable, c'est un mot pauvre
     * @param mot
     * @return true si le mot est pauvre
     * @return false si c'est ok
     */

    public static boolean estUnMotExploitable(String mot) {
        return (mot.length() >= ConstantesEtProprietes.TAILLE_MIN_MOT_EXPLOITABLE);
    }
    

}
