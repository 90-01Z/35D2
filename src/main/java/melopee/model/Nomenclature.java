package melopee.model;



import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import melopee.enumeration.NomenclatureType;
import melopee.utils.ConstantesEtProprietes;
import melopee.utils.ConversionChaine;

@RequiredArgsConstructor
public class Nomenclature {
    
    private static final Logger logger = LogManager.getLogger(Nomenclature.class);
    
    @Getter
    @NonNull
    private NomenclatureType type;
    @JsonIgnore
    private List<LigneNomenclature> lignes = new ArrayList<>();
    @JsonIgnore
    private List<String> motsExclus=new ArrayList<>();
    
    public void ajouterLigne(String s){
        String[] splitLigne = s.split(ConstantesEtProprietes.CARACTERE_SEPARATION_CSV);
        if (splitLigne.length == ConstantesEtProprietes.NB_CHAMPS_FICHIER_NOMENCLATURE) {

            String libelleCorrige = ConversionChaine.conversionChainePourComparaison(splitLigne[1]);

            lignes.add(new LigneNomenclature(splitLigne[0], splitLigne[1], libelleCorrige));
        }
        else {
            logger.warn("Erreur dans le format de la ligne {} ", s);
        }
    }
    
    public void ajouterMotExclu(String motExclu) {
        motsExclus.add(ConversionChaine.conversionChainePourComparaison(motExclu));
    }
    
    public void forEachLigne(Consumer<LigneNomenclature> consumer) {
        lignes.forEach(consumer);
    }

    /**
     * 
     * @param chaineAVerifier
     * @return une copie de chaineAVerifier de laquelle sont retirés tous
     * les mots à exclure
     **/
    public String supprimerMotsAExclure(String chaineAVerifier) {
        List<String> motsOk = new ArrayList<>();
        String[] tabDeMotsAVerifier = chaineAVerifier.split(ConstantesEtProprietes.CARACTERE_ESPACE);

        for (String s : tabDeMotsAVerifier) {
            boolean motARetourner = true;
            for (String exclu : motsExclus) {
                if (exclu.equals(s)) {
                    motARetourner = false;
                }
            }
            if (motARetourner) {
                motsOk.add(s);
            }
        }
        return String.join(ConstantesEtProprietes.CARACTERE_ESPACE, motsOk);
    }

    /**
     * Ajoute l'instance de LigneNomenclature en paramètre à this.lignes
     * @param ligneNomenclature : ne doit pas être null
     */
    public void ajouterLigne(@NotNull LigneNomenclature ligneNomenclature) {
        lignes.add(ligneNomenclature);
    }
    
    @JsonIgnore
    public LigneNomenclature get(int index) {
        return lignes!=null && 0<index && index < lignes.size() ? lignes.get(index):null;
    }
    
    @JsonIgnore
    public int getNombreLignesNomenclature() {
        return lignes==null?0:lignes.size();
    }
    
    @JsonIgnore
    public boolean isCollectionDeLignesEmpty() {
        return lignes == null || lignes.size() == 0;
    }

    @JsonIgnore
    public boolean isCollectionMotsExclusEmpty() {
        return motsExclus==null || motsExclus.isEmpty();
    }
    
    @JsonIgnore
    public int getNombreMotsExclus() {
        return motsExclus==null?0:motsExclus.size();
    }

}
