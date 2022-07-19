package melopee.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

/**
 * 1 ligne par item pouvant correspondre à la saisie
 * probabilite vaut la correspondance probable entre la saisie et l'item
 * contenu correspond à la ligne de la nomenclature
 * @author ycpgj2
 */
@Getter
@Setter
public class Echo {

    @Max(1)
    @Min(0)
    private Double probabilite;
    private String idLibelle;
    private String libelle;

    public Echo(String idLibelle, String libelle) {
        this.idLibelle = idLibelle;
        this.libelle = libelle;
    }

    public Echo(Double probabilite, String idLibelle, String libelle) {
        this.probabilite = probabilite;
        this.idLibelle = idLibelle;
        this.libelle = libelle;
    }

}
