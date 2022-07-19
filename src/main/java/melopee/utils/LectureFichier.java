package melopee.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class LectureFichier {

	@Autowired
	private ConstantesEtProprietes proprietes;

	/**
	 * Lit un fichier à  partir du nom passé en paramêtre. Le dossier utilisé pour
	 * chercher le fichier est le dossier donné par
	 * ConstantesEtProprietes.getPourDossierFichiersNomenclatures ou si ce dernier
	 * est null, le package fr.insee.nomenclatures
	 * 
	 * @param cheminFichier
	 * @return list<String>, un objet String par ligne dans le fichier Si le fichier
	 *         est vide ou n'existe pas, la taille de la liste vaut 0
	 */
	public List<String> lireFichier(String nomFichier) {

		List<String> contenuFichier = new ArrayList<>();
		Pair<String, InputStream> paire = streamPourFichierNomenclature(nomFichier);
		if (paire != null) {
			InputStream stream = paire.getRight();
			try {
				contenuFichier = IOUtils.readLines(stream, StandardCharsets.UTF_8);
                    log.info(() -> "Contenu de "+ paire.getLeft()+"/"+ nomFichier + " chargé");
			} catch (IOException | InvalidPathException e) {
				log.warn(" Impossible de lire le fichier {} : " + e.getClass() + " " + e.getMessage(),
						paire.getLeft() + "/" + nomFichier);
			}
		} else {
            log.warn("Aucun fichier trouvé pour le nom "+nomFichier);
		}
		return contenuFichier;
	}

	/**
	 * Retourne une Inputstream vers le fichier contenant la nomenclature.
	 * <p/>
	 * La méthode recherche d'abord le dossier contenant le fichier :
	 * <ul>
	 * <li>Si la propriété
	 * ConstantesEtProprietes.getPourDossierFichiersNomenclatures est présente la
	 * méthode tente de d'ouvrir une InputStream vers le fichier nomFichier dans le
	 * dossier représenté par la propriété en question</li>
	 * <li>Sinon la méthode tente de retrouver le fichier dans le package
	 * fr.insee.nomenclatures</li>
	 * </ul>
	 * 
	 * @Param nomFichier : le nom de fichier de nomenclature qu'on doit trouver
	 * @return Une paire contenant le nom du dossier où le fichier a été trouvé
	 *         (soit la propriété
	 *         ConstantesEtProprietes.getPourDossierFichiersNomenclatures, soit le
	 *         package fr.insee.nomenclature) soit null. La recherche se fait dans
	 *         l'ordre suivant :
	 *         <ol>
	 *         <li>Une input stream vers le fichier nomFichier dans le dossier
	 *         désigné par la propriété
	 *         ConstantesEtProprietes.dossierFichiersNomenclatures correspondant à 
	 *         la propriété fr.insee.melauto.dossierFichiersNomenclatures</li>
	 *         <li>Une input stream vers le fichier nomFichier dans le package
	 *         fr.insee.nomenclature</li>
	 *         <li>null sinon</li>
	 *         </ol>
	 */
	public Pair<String, InputStream> streamPourFichierNomenclature(String nomFichier) {
		Pair<String, InputStream> retour = null;
		String dossierFichiersNomenclatures = proprietes.getDossierFichiersNomenclatures();
		if (dossierFichiersNomenclatures != null) {
			try {
				retour = Pair.of(dossierFichiersNomenclatures,
						new FileInputStream(Paths.get(dossierFichiersNomenclatures).resolve(nomFichier).toFile()));
			} catch (FileNotFoundException | InvalidPathException e) {
			    log.warn("Erreur dans la recherche du fichier {} : " + e.getMessage(),
                
						dossierFichiersNomenclatures + FileSystems.getDefault().getSeparator() + nomFichier);
			}
		} else {
			InputStream streamRetour = getClass()
					.getResourceAsStream("/" + ConstantesEtProprietes.DIRECTORY_RESOURCES_FR + "/"
							+ ConstantesEtProprietes.DIRECTORY_RESOURCES_INSEE + "/"
							+ ConstantesEtProprietes.DIRECTORY_RESOURCES_NOMENCLATURES + "/" + nomFichier);
			if (streamRetour != null) {
				retour = Pair.of(ConstantesEtProprietes.DIRECTORY_RESOURCES_FR + "."
						+ ConstantesEtProprietes.DIRECTORY_RESOURCES_INSEE + "."
						+ ConstantesEtProprietes.DIRECTORY_RESOURCES_NOMENCLATURES, streamRetour);
			} else {
                log.warn("Erreur dans la recherche du fichier " + nomFichier + " dans fr.insee.nomenclatures");
			}
		}
		return retour;

	}
}
