package melopee.utils;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class LectureFichierTest {

    @InjectMocks
    LectureFichier lecture;

    private String fichierNonReadable;
    private List<String> retour;
    
    @Mock(answer=Answers.CALLS_REAL_METHODS)
    private ConstantesEtProprietes proprietes;

    @Before
    public void init() {
        fichierNonReadable = "test.txt";
        retour = new ArrayList<>();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testerAccesFichierImpossible() {
        assertTrue(
            lecture.lireFichier(fichierNonReadable)
                .equals(retour));
    }

}
