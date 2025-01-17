package fr.distopia.web;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fr.distopia.business.IBusinessImpl;
import fr.distopia.entities.Movie;
//import fr.distopia.entities.Category;
import fr.distopia.exceptions.ManageErrors;

@Controller
public class MovieController {	
	@Autowired
	IBusinessImpl businessImpl;
	
	private final Logger logger = LoggerFactory.getLogger(MovieController.class);
	/**
	 * Méthode en GET correspondant à l'url .../index ou page d'accueil de notre application
	 * @param model sert à ajouter des éléments partagés avec la vue
	 * @param page correspond à la page active côté front, cela assure la pagination / par défaut vaut 0
	 * @param kw est un mot dont on souhaite afficher tous les movies le contenant / par défaut chaine vide
	 * @param idCat est l'identifiant de la catégorie dont on souhaite afficher tous les movies / par défaut vaut 0 
	 * @param cart correspond au nombre d'movies dans mon panier
	 * @param error contient l'élément positionné par redirectAttrs dans le cas d'une exception voir delete, save...
	 * @return la page movies.html 
	 */
	@GetMapping("/index")
	public String index(Model model, @RequestParam(name="page" , defaultValue = "0") int page,
									 @RequestParam(name="keyword" , defaultValue = "") String kw,
									 @RequestParam(name="idCat" , defaultValue = "0") Long idCat,
									 @RequestParam(name="nbcart" , defaultValue = "0") int cart,
									 @ModelAttribute(name="error") String error) {
		Page<Movie> movies = null;
		model.addAttribute("error", model.getAttribute("error"));
		try {
			if(idCat > 0)	{	
				movies = businessImpl.getMoviesByMovieTheaterPage(idCat,page);
				if(movies.isEmpty())
					model.addAttribute("error", ManageErrors.STR_ERROR);
			}
			else movies = businessImpl.getMoviesPages(kw,page); 
						
			model.addAttribute("idCat",idCat);
			model.addAttribute("listMovie",movies.getContent());	
			model.addAttribute("pages", new int[movies.getTotalPages()]);
			model.addAttribute("currentPage",page);
			model.addAttribute("keyword",kw);
			model.addAttribute("movietheater",businessImpl.getMovieTheater());
			model.addAttribute("nbcart", businessImpl.getNbCart());
			
			String username;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				username = ((UserDetails)principal).getUsername();
			} else {
				username = principal.toString();
				if(username.contains("anonymous"))
					username = "";
			}
			model.addAttribute("username", " " +username);
		}
		catch(Exception e) {
			model.addAttribute("error",e.getMessage());
			logger.error("[MOVIE CONTROLLER : INDEX] : {} " , e.getMessage());
		}		
		return "movies";
	}
	
	/**
	 * Méthode en GET correspondant à l'url .../delete consistant à supprimer un movie à partir de son id
	 * @param id de l'movie
	 * @param page
	 * @param keyword
	 * @param idCat
	 * @param redirectAttrs sert à injecter un paramètre dans la redirection, delors ../index pourra le récupérer via le modelAttribute
	 * @return une redirection vers ../index avec les éléments permettant de garder le contexte actuel
	 */
	@GetMapping("/delete")	
	public String delete(Movie id, int page, String keyword , Long idCat, RedirectAttributes redirectAttrs) {				
		try {
			businessImpl.saveMovie(id);
		} catch (Exception e) {
			redirectAttrs.addAttribute("error",e.getMessage());
			logger.error("[MOVIE CONTROLLER : DELETE] : {} " , e.getMessage());
		}
		return "redirect:/index?page="+page+"&keyword="+keyword + "&idCat=" + idCat;
	}
	/**
	 * Méthode en GET correspondant à l'url .../edit permettant d'afficher un movie en vue de sa mise à jour
	 * @param id de l'movie
	 * @param model
	 * @return page edit.html pour l'édition d'un movie
	 */
	@GetMapping("/edit")	
	public String edit(Long id, Model model) {
		Movie movie;
		try {
			movie = businessImpl.getMovieById(id);
			model.addAttribute("movietheater",businessImpl.getMovieTheater());
			model.addAttribute("movie", movie);
		} catch (Exception e) {
			model.addAttribute("error",e.getMessage());
			logger.error("[MOVIE CONTROLLER : EDIT] : {} " , e.getMessage());
		}
		return "edit";
	}
	
	/**
	 * Méthode en GET correspondant à l'url .../movie permettant d'ajouter un nouvel movie
	 * @param model
	 * @return page movie.html
	 */
	@GetMapping("/movie")		
	public String movie(Model model) {
		model.addAttribute("movie" , new Movie(0, null, 0, null, 0, 0, null));
		try {
			model.addAttribute("movietheater",businessImpl.getMovieTheater());
		} catch (Exception e) {
			model.addAttribute("error",e.getMessage());
			logger.error("[MOVIE CONTROLLER : MANAGE NEW MOVIE] : {} " , e.getMessage());
		}
		return "movie";
	}
	
	/**
	 * Méthode en POST correspondant à l'url .../save visant à sauvegarder ou mettre à jour un movie
	 * @param movie
	 * @param bindingResult sert à la gestion des problèmes de saisie ne correspondant pas à ce qui est attendu / voir validation dans l'entité Movie
	 * @param model
	 * @param redirectAttrs sert à ajouter un paramètre de redirection récupéré dans le modelAttribute afin d'afficher les erreurs suite à une exception
	 * @return redirection vers ../index
	 */
	@PostMapping("/save")		
	public String save(@Valid Movie movie, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {
		try {
			if(bindingResult.hasErrors()) {
				model.addAttribute("movietheater",businessImpl.getMovieTheater());
				return "movie";
			}	
			businessImpl.saveMovie(movie);		
		}
		catch(Exception e) {
			redirectAttrs.addAttribute("error",e.getMessage());
			logger.error("[MOVIE CONTROLLER : SAVE MOVIE] : {} " , e.getMessage());
		}
		return "redirect:/index";
	}
	
	@RequestMapping("/greating")
	public @ResponseBody String greating() {
		return businessImpl.great();
	}	
}
