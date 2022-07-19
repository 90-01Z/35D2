package melopee.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ConversionChaineTest {

	@Test
	public void testerSuppressionAccentDuneChaine() {
		assertTrue(ConversionChaine.suppressionAccents("éèàçù").equals("eeacu"));

	}

	@Test
	public void testerRemplacementCaracteresSpeciauxDansUnMot() {
		assertTrue(ConversionChaine.remplacerCaracteresSpeciauxParEspace("coucou%^").equals("coucou  "));

	}

	@Test
	public void testerSuppressionMotsMoinsDeTroisCaracteres() {
		assertTrue(ConversionChaine.estUnMotExploitable("l"));
		assertTrue(ConversionChaine.estUnMotExploitable("coucou"));
	}

	@Test
	public void testerTiretGenere2Mots() {
		String saisie = "abat-jour";
		assertTrue(ConversionChaine.remplacerCaracteresSpeciauxParEspace(saisie).equals("abat jour"));
	}

	@Test
	public void testerTransformationDuneChaine() {
		assertTrue(ConversionChaine.conversionChainePourComparaison("aide-maçon").equals("AIDE MACON"));
		assertTrue(ConversionChaine.conversionChainePourComparaison(null).equals(""));
		assertTrue(ConversionChaine.conversionChainePourComparaison("").equals(""));
		assertTrue(ConversionChaine.conversionChainePourComparaison("a").equals("A"));
		assertTrue(ConversionChaine.conversionChainePourComparaison("%ù").equals("U"));
	}

}
