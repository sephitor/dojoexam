package com.fboisier.dojoexam.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fboisier.dojoexam.entity.Borrower;
import com.fboisier.dojoexam.entity.Lender;
import com.fboisier.dojoexam.service.BorrowerService;
import com.fboisier.dojoexam.service.LenderService;
import com.fboisier.dojoexam.service.PrestamoService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping
public class Lenders {

	private final LenderService lenderService;
	private final BorrowerService borrowerService;
	private final PrestamoService prestamoService;

	public Lenders(LenderService lenderService,BorrowerService borrowerService, PrestamoService prestamoService) {
		this.lenderService = lenderService;
		this.borrowerService = borrowerService;
		this.prestamoService = prestamoService;
	}
	

	@RequestMapping(value = "/crearLender", method = RequestMethod.POST)
	public String crearLender(@Validated @ModelAttribute("lender") Lender lender, Model model, HttpSession session) {

		final Logger LOGGER = Logger.getLogger("com.fboisier.dojoexam.controller.Lenders");
		LOGGER.log(Level.INFO, "Lender: " + lender.toString());
		LOGGER.log(Level.INFO, "Lender.sonIguales: " + lender.sonIguales());
		LOGGER.log(Level.INFO, "Lender.getPassword: " + lender.getPassword());
		LOGGER.log(Level.INFO, "Lender.getPasswordConfirmation: " + lender.getPasswordConfirmation());

		if(lender.sonIguales())
		{
			lender.setRol("lender");
					
			Lender l = lenderService.registerLender(lender);
	
			session.setAttribute("usuarioID", l.getId());
			session.setAttribute("tipo", "lender");
	
			return "redirect:/lender";
		}
		else {
			model.addAttribute("lender", lender);
			model.addAttribute("borrower", new Borrower());
			model.addAttribute("error", "Contraseñas no son iguales");
			return "register";
		}
	}

	@GetMapping("/lender")
	public String abrirLender(HttpSession session, Model model) {

		String salida = "";
		
		Long usuarioID = (Long) session.getAttribute("usuarioID");

		if (usuarioID == null) {
			salida = "error";
		} else {

			Lender l = lenderService.findLenderbyID(usuarioID);
			List<Borrower> borrowers = borrowerService.listar();

			if (l == null) {
				salida = "error";
			} else {

				model.addAttribute("lender", l);
				model.addAttribute("borrowers", borrowers);
				salida = "lender";
			}

		}
		// si existe error digamos que no puede acceder a la pagina sin estar logeado
		if(salida == "error") {
			model.addAttribute("error", "Debes registrarte o tener el rol respectivo para poder acceder a la página.");
			return "login";
		}else
			return salida;
			
	}
	
	@RequestMapping(value = "/agregarPrestamo", method = RequestMethod.POST)
	public String crearPrestamo(@RequestParam(name="prestar") String prestar, @RequestParam(name="borrowerID") String borrower, @RequestParam(name="lenderID") String lender,Model model) {

		String salida = "";
		String msg = "";
		
		Long l = Long.parseLong(lender);
		Lender oLender = lenderService.findLenderbyID(l);
		
		Long b = Long.parseLong(borrower);
		Borrower oBorrower = borrowerService.findBorrowerbyID(b);
		
		double p = 0;
		
		if(isDouble(prestar))
		{
		
			p = Double.parseDouble(prestar);
			
			// valida si el monto raised del borrower se pasa del monto necesitado needed
			double nuevoPresto = p + oBorrower.getRaised();
			
			if(nuevoPresto > oBorrower.getNeeded()){
				salida = "error";
				msg =  "La suma del monto indicado ("+ p +"), supera el monto solicitado(Amount Needed)";
			}
			
			double nuevoBalance = oLender.getBalance() - p;
			
			if(nuevoBalance < 0){
				salida = "error";
				msg =  "El monto indicado (" + p + "),  supera el balance actual.";
			}
			
		}
		else
		{
			salida = "error";
			msg =  "Numero indicado no es numerico.";
		}
		
		
		if(salida == "error") {
			List<Borrower> borrowers = borrowerService.listar();
			model.addAttribute("error",msg);
			model.addAttribute("lender", oLender);
			model.addAttribute("borrowers", borrowers);
			return "lender";
		}
		else {
			prestamoService.registerPrestamo(oLender, oBorrower, p);
			return "redirect:/lender";
		}
			
		
	}
	
    boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
