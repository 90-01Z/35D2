
package melopee.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import melopee.enumeration.NomenclatureType;
import melopee.model.RetourEchos;
import melopee.service.ITraiterRequetes;
import melopee.utils.ConstantesEtProprietes;
@CrossOrigin
@RestController
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
public class ApiController {
	@NonNull
	private ITraiterRequetes recherche;
	private static Logger LOGGER = LogManager.getLogger(ApiController.class);

	/**
	 * * Traite une requête sous la forme <adresse appli>/<nom
	 * nomenclature>?saisie=<saisie de l'enquêté> * @param saisie: correspond au
	 * paramètre <code> saisie</code> de l'URL de la requête : la chaîne saisie par
	 * l'enquêté * @param probaDemandee : correspond au paramètre
	 * <code> probabilite</code> de l'URL de la requête * @param
	 * elementNomenclatureDansUrl : c'est la ressource demandée par la requête. doit
	 * correspondre * à l'une des nomenclatures disponibles (formation, diplome,
	 * profession). La ressource demandée permet de * cibler la bonne nomenclature
	 * pour la recherche. * @return les réponses au format JSON à la recherche
	 * d'échos correspondant au libellé * <saisie de l'enquêté> dans la nomenclature
	 * <nom nomenclature>
	 */
	@GetMapping(value = ConstantesEtProprietes.URL_RECHERCHE_ECHOS, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RetourEchos rechercherSaisie(
			@RequestParam(value = ConstantesEtProprietes.NOM_PARAM_SAISIE, required = false) String saisie,
			@RequestParam(value = ConstantesEtProprietes.NOM_PARAM_PROBA_DEMANDEE, required = false, defaultValue = "false") Boolean probaDemandee,
			@PathVariable(ConstantesEtProprietes.PATH_VARIABLE_RECHERCHE_ECHO) String elementNomenclatureDansUrl) {
		LOGGER.debug(() -> "Appel pour recherche de \"" + saisie + "\" dans " + elementNomenclatureDansUrl);
		RetourEchos retour = recherche.traiterRequeteDeRechercheDansNomenclature(saisie, probaDemandee,
				NomenclatureType.findByLibelle(elementNomenclatureDansUrl));
		LOGGER.debug(() -> "Fin recherche de \"" + saisie + "\" dans " + elementNomenclatureDansUrl);
		return retour;
	}

	/** * Affiche un message d'accueil pour la homepage * @return */
	@RequestMapping(value = ConstantesEtProprietes.URL_ACCUEIL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String hello() {
		return ConstantesEtProprietes.CONTENT_HOME_PAGE;
	}

	@GetMapping(value = ConstantesEtProprietes.URL_HEALTHCHECK, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> healthcheck() {
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
}
