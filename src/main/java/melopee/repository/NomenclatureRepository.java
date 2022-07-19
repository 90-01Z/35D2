package melopee.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import melopee.enumeration.NomenclatureType;
import melopee.model.Nomenclature;
import melopee.utils.ConstantesEtProprietes;
import melopee.utils.LectureFichier;

/**
 * Service qui fournit des Nomenclatures à ses clients.
 * La classe gère :
 * <ul>
 * <li>La récupération et l'instanciation des
 * nomenclatures (liste des libellés et mots à exclure) depuis les fichiers</li>
 * <li>La conservation en mémoire des instances chargées (une
 * seule instance par nomenclature)</li>
 * <li>L'accès aux nomenclatures</li>
 * </ul>
 * @author xrmfux
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = {
    @Autowired
})
public class NomenclatureRepository {

    private Map<NomenclatureType, Nomenclature> nomenclatures = new HashMap<>();
    
    private static Logger LOGGER=LogManager.getLogger(NomenclatureRepository.class);

    @NonNull
    private LectureFichier lectureFichier;
    
    @NonNull
    private ConstantesEtProprietes proprietes;

    /**
     * Si aucune nomenclature correspondant à nomenclatureType
     * (s'il n'existe aucune entrée avec nomenclatureType dans
     * la map des nomenclatures) n'existe encore, charge depuis le
     * fichiers correspondant la nomenclature demandée : un fichier
     * pour charger les libellés et un fichier pour charger les mots exlus.
     * Ne faire rien si le paramètre est null ou si nomenclatureType==INTROUVABLE
     * @param nomenclatureType
     */
    public void initialiserNomenclature(NomenclatureType nomenclatureType) {
        if (nomenclatureType != null && nomenclatureType != NomenclatureType.INTROUVABLE) {
            if (nomenclatures.get(nomenclatureType) == null) {
                LOGGER.info(()->"Initialisation de la nomenclature "+nomenclatureType+" from "+proprietes.getOrigine());
                Nomenclature nomenclature = new Nomenclature(nomenclatureType);
                nomenclature = initialiserLibelles(nomenclature);
                nomenclature = initialiserMotsExclus(nomenclature);
                nomenclatures.put(nomenclatureType, nomenclature);
            }
        }else {
            
        }
    }

    private Nomenclature initialiserLibelles(Nomenclature nomenclature) {
        lectureFichier.lireFichier(nomenclature.getType().getLibelle() + ConstantesEtProprietes.EXTENSION_CSV).forEach(
            nomenclature::ajouterLigne);
        return nomenclature;
    }

    private Nomenclature initialiserMotsExclus(Nomenclature nomenclature) {
        lectureFichier
            .lireFichier(
                ConstantesEtProprietes.PREF_FICHIER_EXCLUSIONS + nomenclature.getType().getLibelle() + ConstantesEtProprietes.EXTENSION_CSV)
            .forEach(nomenclature::ajouterMotExclu);
        return nomenclature;
    }

    /**
     * Retourne la nomenclature correspondant au type demandée.
     * Si la nomenclature n'est pas encore chargée, elle le sera au préalable.
     * Si aucune nomenclature n'est trouvée, retourne null.
     * Retourne directement null si type == null ou bien si type==INTROUVALBLE.
     * @param type
     * @return
     */
    public Nomenclature find(NomenclatureType type) {
        Nomenclature retour=null;
        if (type!=null && type!=NomenclatureType.INTROUVABLE) {
            retour=nomenclatures.get(type);
            if (retour==null) {
                initialiserNomenclature(type);
                retour=nomenclatures.get(type);
            }
        }else {
            LOGGER.info(()->"Demande de nomenclature null ou introuvable");
        }
        return retour;
    }

}
