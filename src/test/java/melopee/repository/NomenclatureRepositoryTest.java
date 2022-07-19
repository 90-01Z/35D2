package melopee.repository;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import melopee.enumeration.NomenclatureType;
import melopee.utils.ConstantesEtProprietes;
import melopee.utils.LectureFichier;

@RunWith(SpringJUnit4ClassRunner.class)
public class NomenclatureRepositoryTest {

    @InjectMocks
    NomenclatureRepository nomenclatureRepository;
    
    @Mock
    private LectureFichier lecture;
    
    @Mock
    private ConstantesEtProprietes proprietes; 

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testerQueMetthodeDentreeRetourneToujoursUneListeDeNomenclature() {
        List<String> listeDeMotsDeLaNomenclature = new ArrayList<>();
        listeDeMotsDeLaNomenclature.add("ligne1;qquchose");
        listeDeMotsDeLaNomenclature.add("ligne2;autrechose");
        when(lecture.lireFichier(NomenclatureType.PROFESSION_HOMME.getLibelle() + ConstantesEtProprietes.EXTENSION_CSV))
            .thenReturn(listeDeMotsDeLaNomenclature);

        assertTrue(
            nomenclatureRepository.find(NomenclatureType.PROFESSION_HOMME).getNombreLignesNomenclature()
                 == listeDeMotsDeLaNomenclature.size());
    }


    @Test
    public void testerFormatCsvCorrect() {
        List<String> listeOk = new ArrayList<String>();
        listeOk.add("ligne;ok");
        when(lecture.lireFichier(NomenclatureType.PROFESSION_HOMME.getLibelle() + ConstantesEtProprietes.EXTENSION_CSV))
        .thenReturn(listeOk);
        assertTrue(
            nomenclatureRepository.find(NomenclatureType.PROFESSION_HOMME)
                .getNombreLignesNomenclature()== listeOk.size());
    }

    @Test
    public void testerRetourVideSiPasDeNomenclatureChargee() {
        when(lecture.lireFichier(NomenclatureType.PROFESSION_HOMME.getLibelle() + ConstantesEtProprietes.EXTENSION_CSV))
        .thenReturn(new ArrayList<>());
        assertTrue(
            nomenclatureRepository.find(NomenclatureType.PROFESSION_HOMME)
            .isCollectionDeLignesEmpty());

    }

    @Test
    public void testerPasDeMotsAExclureRetourneListeVide() {
        List<String> motsAExclureVide = new ArrayList<>();
        when(
            lecture.lireFichier(
                ConstantesEtProprietes.PREF_FICHIER_EXCLUSIONS + NomenclatureType.PROFESSION_HOMME.getLibelle() + ConstantesEtProprietes.EXTENSION_CSV))
                    .thenReturn(motsAExclureVide);
        assertTrue(
            nomenclatureRepository.find(NomenclatureType.PROFESSION_HOMME)
                .isCollectionMotsExclusEmpty());
    }

    @Test
    public void testerDesMotsAExclure() {
        List<String> motsAExclure = new ArrayList<>();
        motsAExclure.add("absdefg");
        motsAExclure.add("hijklmn");
        motsAExclure.add("opqrstu");
        motsAExclure.add("vxxyz");
        when(
            lecture.lireFichier(
                ConstantesEtProprietes.PREF_FICHIER_EXCLUSIONS + NomenclatureType.PROFESSION_HOMME.getLibelle() + ConstantesEtProprietes.EXTENSION_CSV))
                    .thenReturn(motsAExclure);
        assertTrue(
            nomenclatureRepository.find(NomenclatureType.PROFESSION_HOMME)
                .getNombreMotsExclus() == motsAExclure.size());
    }
}
