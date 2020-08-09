package com.fboisier.dojoexam.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.fboisier.dojoexam.entity.Borrower;
import com.fboisier.dojoexam.entity.Lender;
import com.fboisier.dojoexam.entity.Prestamo;
import com.fboisier.dojoexam.repositories.PrestamoRepository;

@Service
public class PrestamoService {

	private final PrestamoRepository prestamoRepository;
	private final LenderService lenderService;
	private final BorrowerService borrowerService;
	
	
	public PrestamoService(PrestamoRepository prestamoRepository,LenderService lenderService , BorrowerService borrowerService) {
		this.prestamoRepository = prestamoRepository;
		this.lenderService = lenderService;
		this.borrowerService = borrowerService;
		
	}
	
	public Prestamo registerPrestamo(Prestamo prestamo) {
		return prestamoRepository.save(prestamo);
	}
	
	public void registerPrestamo(Lender lender, Borrower borrower, double prestado ) {
		
		Long cuantos = prestamoRepository.existeRegistro(lender.getId(), borrower.getId());
		
		final Logger LOGGER = Logger.getLogger("com.fboisier.dojoexam.controller.Borrowers");

		LOGGER.log(Level.INFO, "Cuantos: " + cuantos);
		
		if(cuantos > 0) {
			double prestadoDB = prestamoRepository.obtenerPrestado(lender.getId(), borrower.getId());
			
			LOGGER.log(Level.INFO, "prestadoDB: " + prestadoDB);
			
			prestamoRepository.updateDirect(lender.getId(), borrower.getId(), prestado + prestadoDB);
		}else {
			prestamoRepository.saveDirect(lender.getId(), borrower.getId(), prestado);
		}
		
		
		// despues de actualizar actualizamos el balance 
		lender.setBalance(lender.getBalance() - prestado);
		LOGGER.log(Level.INFO, "lender.getBalance(): " + lender.getBalance());
		lenderService.saveLender(lender);
		
		//y el amount raised
		borrower.setRaised(borrower.getRaised() + prestado);
		LOGGER.log(Level.INFO, "borrower.getRaised(): " + borrower.getRaised());
		borrowerService.saveBorrower(borrower);
		
	}
			
	
	
}
