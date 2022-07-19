package melopee.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = { "saisie", "nomenclature" })
public class DeroulementRequete {
	private String saisie;
	private String chaineRecherchee;
	private Nomenclature nomenclature;
	private String dureTraitementEnMs;
	private boolean saisieExiste;
	private boolean requeteExiste;
	private boolean fichierNomenclatureCharge = false;
	private boolean saisieEstExploitable = false;
	private boolean auMoinsUnEchoTrouve = false;

	public DeroulementRequete(String saisie, boolean saisieExiste, boolean requeteExiste) {
		this.saisie = saisie;
		this.saisieExiste = saisieExiste;
		this.requeteExiste = requeteExiste;
	}

}
