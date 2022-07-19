package melopee.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import melopee.enumeration.EtatEcho;

@Getter
@Setter
public class RetourEchos {

    private DeroulementRequete deroulementRequete;

    private EtatEcho statutRequete;

    private Integer nbEcho;

    private List<Echo> listeEcho;

}
