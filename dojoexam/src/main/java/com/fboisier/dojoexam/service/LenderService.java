package com.fboisier.dojoexam.service;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.fboisier.dojoexam.entity.Lender;
import com.fboisier.dojoexam.repositories.LenderRepository;

@Service
public class LenderService {
	private final LenderRepository lenderRepository;
	
	public LenderService(LenderRepository lenderRepository){
		this.lenderRepository = lenderRepository;
	}
	
	
	 public Lender registerLender(Lender lender) {
	        String hashed = BCrypt.hashpw(lender.getPassword(), BCrypt.gensalt());
	        lender.setPassword(hashed);
	        return lenderRepository.save(lender);
    }
	 
	 public Lender saveLender(Lender lender) {
	        return lenderRepository.save(lender);
	 }
	
	public Lender findLenderbyID(Long id) {
		Optional<Lender> l = lenderRepository.findById(id);
		
		if(l.isPresent()) {
			return l.get();
		}else {
			return null;
		}
	}
	
}
