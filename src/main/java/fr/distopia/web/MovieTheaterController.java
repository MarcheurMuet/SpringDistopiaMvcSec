package fr.distopia.web;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.distopia.dao.MovieTheaterRepository;
import fr.distopia.entities.MovieTheater;
import fr.distopia.exceptions.ManageErrors;

@Controller
public class MovieTheaterController {
	@Autowired
	MovieTheaterRepository movietheaterRepository;
	
	private final Logger logger = LoggerFactory.getLogger(MovieTheaterController.class);
	
	/**
	 * Méthode qui contrôle si l'id de la catégorie existe avant de renvoyer vers l'affichage des articles par catégorie
	 * @param id de la catégorie
	 * @param model
	 * @return redirection vers ../index
	 */
	@GetMapping("/movietheater")
	public String movietheater(Long id, Model model) {
		int idCat = (int)-1;
		try {
			Optional<MovieTheater> cat = movietheaterRepository.findById(id+1);
			if(cat.isPresent()) {
				MovieTheater movietheater = cat.get();
				idCat = movietheater.getIdMovieTheater();
				model.addAttribute("idCat" , idCat);
			}
			else return "redirect:/index?error=" + ManageErrors.STR_ERROR;
		}
		catch(Exception e) {
			logger.error("[MOVIETHEATER CONTROLLER : MOVIETHEATER] : {} " , e.getMessage());
			return "redirect:/index?error=" + e.getMessage();
		}		
		return "redirect:/index?idCat=" + idCat;
	}
}
