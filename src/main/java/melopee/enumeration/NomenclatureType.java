package melopee.enumeration;

import java.util.Arrays;

import lombok.Getter;
import melopee.utils.ConstantesEtProprietes;

public enum NomenclatureType {

    DIPLOME(ConstantesEtProprietes.NOMENC_DIPLOME),
    PROFESSION_HOMME(ConstantesEtProprietes.NOMENC_PROFESSION_HOMME),
    PROFESSION_FEMME(ConstantesEtProprietes.NOMENC_PROFESSION_FEMME),
    FORMATION(ConstantesEtProprietes.NOMENC_FORMATION),
    INTROUVABLE(ConstantesEtProprietes.NOMENC_INCONNUE);

    @Getter
    private String libelle;

    private NomenclatureType(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Méthode qui retourne la nomenclature de l'enum
     * dont le libellé correspond au paramètre.
     * @param libelleNomenclature
     * @return une instance de Nomenclature dont l'attribut libelle
     * est égal (au sens de equals) à libelleNomenclature. INTROUVABLE
     * si libelleNomenclature est null ou ne correspond au libellé
     * d'aucun élément de l'enum.
     */
    public static NomenclatureType findByLibelle(String libelleNomenclature) {
        return Arrays.stream(values()).filter(n -> n.libelle.equals(libelleNomenclature)).findFirst().orElse(
            INTROUVABLE);
    }

}
