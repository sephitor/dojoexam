package com.fboisier.dojoexam.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fboisier.dojoexam.entity.Borrower;
import com.fboisier.dojoexam.entity.Lender;
import com.fboisier.dojoexam.entity.Usuario;
import com.fboisier.dojoexam.service.UsuarioService;

@Controller
@RequestMapping
public class Controlador {

	private final UsuarioService usuarioService;

	public Controlador(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	

	@GetMapping({ "/", "login" })
	public String login(HttpSession session) {
		
		String tipo = (String) session.getAttribute("tipo");

		if (tipo == null) {
			return "login";
		} else {
			return "redirect:/"+tipo;
		}
			
		
	}
	
	

	@GetMapping("/register")
	public String registrar(Model model) {
		model.addAttribute("lender", new Lender());
		model.addAttribute("borrower", new Borrower());
		return "register";
	}
	
	@GetMapping({"logout"})
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUsuario(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
		boolean isLoged = usuarioService.authenticateUsuario(email, password);
		
		if(isLoged) {
			
			Usuario u = usuarioService.findByEmail(email);
			
			session.setAttribute("usuarioID", u.getId());
			session.setAttribute("tipo", u.getRol());
			
			return "redirect:/"+ u.getRol();
			
		}else {
			model.addAttribute("error", "No existe usuario o la contraseña indicada no es correcta.");
			return "login";
		}
		
	}
	

}
