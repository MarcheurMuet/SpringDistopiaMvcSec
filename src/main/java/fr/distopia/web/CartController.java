package fr.distopia.web;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.distopia.business.IBusinessImpl;
import fr.distopia.dao.MovieRepository;
import fr.distopia.dao.UserRepository;
import fr.distopia.dao.SessionListItemRepository;
import fr.distopia.dao.SessionListRepository;
import fr.distopia.entities.Movie;
import fr.distopia.entities.User;
import fr.distopia.entities.SessionList;

@Controller
//@RequestMapping("/cart")
public class CartController {
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	SessionListRepository sessionlistRepository;
	
	@Autowired
	SessionListItemRepository sessionlistItemRepository;
	
	@Autowired
	IBusinessImpl business;
	
	private final Logger logger = LoggerFactory.getLogger(CartController.class);
	
	/**
	 * Méthode en GET correspondant à l'url .../vcart permettant de visualiser le panier
	 * @param model
	 * @return cart.html
	 */
	@GetMapping("/vcart")	//Convention REST : l'URL indique la ressource, et le verbe HTTP indique l'action.
	public String viewCaddy(Model model) {
		model.addAttribute("cart",business.getCart());		
		double total = business.getTotalAmount();
		model.addAttribute("total",total);
		model.addAttribute("nbArt",business.getNbCart());
		return "cart";
	}
	
	/**
	 * Méthode en GET permettant d'ajouter un movie au panier, tous les paramètres ci dessous permettent de garder le contexte pour redirection
	 * @param id
	 * @param page
	 * @param keyword
	 * @param idCat
	 * @param redirectAttrs sert à injecter un paramètre dans la redirection, delors ../index pourra le récupérer via le modelAttribute
	 * @return ../index
	 */
	@GetMapping("/acart")
	public String addMovieCart(Long id, int page, String keyword , Long idCat, RedirectAttributes redirectAttrs) {	
		try {
			business.addMovToCart(business.getMovieById(id));
		}
		catch(Exception e) {
			redirectAttrs.addAttribute("error",e.getMessage());
			logger.error("[CART CONTROLLER : ADD MOVIE TO CART] : {} " , e.getMessage());
		}
		return "redirect:/index?page="+page+"&keyword="+keyword + "&idCat=" + idCat + "&cart=" + business.getNbCart(); 
	}
	
	/**
	 * Méthode en GET correspondant à l'url .../dcart consistant à supprimer un movie du panier
	 * @param id de l'movie à supprimer
	 * @return redirection vers la gestion de l'affichage du panier
	 */
	@GetMapping("/dcart")
	public String deleteMovieCart(Long id) {
		//ToDo : ajouter une demande de confirmation en JS sur la vue
		//ToDo : ajouter ici une gestion d'exception puisqu'accès Bdd
		business.delMovFromCart(id);
		return "redirect:/vcart"; 
	}	
	
	/**
	 * Méthode en GET correspondant à l'url .../sessionlist gérant la saisi d'un client dans le cadre de la commande en cours 
	 * @param model
	 * @return sessionlist.html
	 */
	@GetMapping("/sessionlist")		
	public String sessionlist(Model model) {		
		if(business.isEmpty())	return "redirect:/index";
		model.addAttribute("user", new User());
		return "sessionlist";
	}
	
	/**
	 * Méthode en POST correspondant à ../psessionlist postant toutes les infos valides d'un client
	 * @param user
	 * @param bindingResult gestion de la validation des saisies
	 * @param model
	 * @return recap.html soit un récapitulatif de la commande complète
	 */
	@PostMapping("/psessionlist")
	public String postSessionList(@Valid User user , BindingResult bindingResult , Model model) {	
		if(bindingResult.hasErrors())	return "sessionlist";
		
		model.addAttribute("cart",business.getCart());
		double total = business.getTotalAmount();
		model.addAttribute("total",total);		
		model.addAttribute("user",user);		
		business.setUser(user);
		return "recap";
	}
	
	/**
	 * Méthode en GET ../confirm correspondant au bouton de validation du récapitulatif de la commande, ici la commande est validée en base avec les infos associées
	 * @return thanks.html si tout va bien, si exception redirection vers /index avec message d'erreur
	 */
	@GetMapping("/confirm")
	@Transactional
	public String confirm(RedirectAttributes redirectAttrs) {
		if(business.isEmpty())	return "redirect:/index";
		
		try {
		//ToDo : ajout d'un client en base, s'il existe déjà ?
		User user = business.getUser();
		userRepository.save(user);
		
		SessionList sessionlist = new SessionList();
		sessionlistRepository.save(sessionlist);
		
		for(Movie movie : business.getCart()) {
			SessionList sessionlistItem = new SessionList();
			sessionlistItem.setSessionList(sessionlist);
			sessionlistItem.setMovie(movie);
			sessionlistItem.setPrice(movie.getPrice());
			sessionlistItem.setQuantity(movie.getPlace());			
			sessionlistItemRepository.save(sessionlistItem);
		}		 
		business.delCart();
		//ToDo renvoi vers une page de paiement suivi envoi mail de confirmation...
		}
		catch(Exception e) {
			redirectAttrs.addAttribute("error",e.getMessage());
			logger.error("[CART CONTROLLER : CONFIRM SESSIONLIST] : {} " , e.getMessage());
			//return "500";
			return "redirect:/index";
		}		
		return "thanks";
	}
}