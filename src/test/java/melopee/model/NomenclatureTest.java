package melopee.model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import melopee.enumeration.NomenclatureType;

public class NomenclatureTest {
    
    private Nomenclature nomenclature;
    
    @Before
    public void initialiser() {
        nomenclature=new Nomenclature(NomenclatureType.PROFESSION_HOMME);
        nomenclature.ajouterMotExclu("de");
        nomenclature.ajouterMotExclu("à");
    }
    
    @Test
    public void motsAExclure_should_be_deleted() {
        assertTrue(
            nomenclature.supprimerMotsAExclure("MACON DE NIORT")
                .equals("MACON NIORT"));
    }
    
    @Test
    public void ligneNomenclature_should_be_added() {
        
    }

}
