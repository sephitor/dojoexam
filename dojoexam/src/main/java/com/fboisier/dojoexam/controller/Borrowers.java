package com.fboisier.dojoexam.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fboisier.dojoexam.entity.Borrower;
import com.fboisier.dojoexam.entity.Lender;
import com.fboisier.dojoexam.service.BorrowerService;

@Controller
@RequestMapping
public class Borrowers {

	private final BorrowerService borrowerService; 
	
	public Borrowers(BorrowerService borrowerService) {
		this.borrowerService = borrowerService;
	}
	
	
	@RequestMapping(value = "/crearBorrower", method = RequestMethod.POST)
	public String crearBorrower(@Validated  @ModelAttribute("borrower") Borrower borrower, Model model, HttpSession session) {

		final Logger LOGGER = Logger.getLogger("com.fboisier.dojoexam.controller.Borrowers");

		LOGGER.log(Level.INFO, "Borrower: " + borrower.toString());

		if(borrower.sonIguales()){
		
			borrower.setRol("borrower");
			
			Borrower b = borrowerService.registerBorrower(borrower);
			
			session.setAttribute("usuarioID", b.getId());
			
			return "redirect:/borrower";
		}else {
			model.addAttribute("lender", new Lender());
			model.addAttribute("borrower", borrower);
			model.addAttribute("error", "Contraseñas no son iguales");
			return "register";
		}
	}
	
	@GetMapping("/borrower")
	public String abrirBorrower(HttpSession session, Model model) {

		String salida = "";
		
		Long usuarioID = (Long) session.getAttribute("usuarioID");

		if (usuarioID == null) {
			salida = "error";
		} else {

			Borrower b = borrowerService.findBorrowerbyID(usuarioID);

			if (b == null) {
				salida = "error";
			} else {
		
				model.addAttribute("borrower", b);
				salida = "borrower";
			}

		}
		// si existe error digamos que no puede acceder a la pagina sin estar logeado
		if(salida == "error") {
			model.addAttribute("error", "Debes registrarte o tener el rol respectivo para poder acceder a la página.");
			return "login";
		}else
			return salida;
			
	}

}
