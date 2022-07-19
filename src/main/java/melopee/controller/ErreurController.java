package melopee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import melopee.enumeration.NomenclatureType;
import melopee.model.RetourEchos;
import melopee.service.ITraiterRequetes;
import melopee.utils.ConstantesEtProprietes;

/**
 * Personnalisation page 404
 * Retourne un objet de type @RetourEcho signalant que l'URL demandée est introuvable
 * @author ycpgj2
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = {
    @Autowired
})
public class ErreurController implements ErrorController {

    @NonNull
    private ITraiterRequetes recherche;

    @Override
    public String getErrorPath() {
        return ConstantesEtProprietes.URL_ERROR;
    }

    @RequestMapping(
        value = ConstantesEtProprietes.URL_ERROR,
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    List<RetourEchos> recevoirSaisieNonTraitable() {
        List<RetourEchos> erreur = new ArrayList<>();
        erreur.add(recherche.traiterRequeteDeRechercheDansNomenclature(null, false, NomenclatureType.INTROUVABLE));
        return erreur;
    }

}
