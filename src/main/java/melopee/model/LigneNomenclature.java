package melopee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 1 objet par ligne dans le fichier de nomenclature
 * ligneNomenclature correspond à la ligne exacte telle qu'elle est renseignée dans le fichier et qui sera retournée
 * ligneCorrigee correspond à la correction une fois appliquee les mêmes règles de transformation qu'à la saisie
 * @author ycpgj2
 */
@Getter
@Setter
@AllArgsConstructor
public class LigneNomenclature {

    private String idNomenclature;
    private String libelleNomenclature;
    private String ligneCorrigee;

}
