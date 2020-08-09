package com.fboisier.dojoexam.service;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.fboisier.dojoexam.entity.Borrower;
import com.fboisier.dojoexam.repositories.BorrowerRepository;

@Service
public class BorrowerService {
	private final BorrowerRepository borrowerRepository;

	public BorrowerService(BorrowerRepository borrowerRepository) {
		this.borrowerRepository = borrowerRepository;
	}

	public Borrower registerBorrower(Borrower borrower) {
		String hashed = BCrypt.hashpw(borrower.getPassword(), BCrypt.gensalt());
		borrower.setPassword(hashed);
		return borrowerRepository.save(borrower);
	}
	
	
	public Borrower saveBorrower(Borrower borrower) {
		return borrowerRepository.save(borrower);
	}
	
	
	public Borrower findBorrowerbyID(Long id) {
		Optional<Borrower> b = borrowerRepository.findById(id);
		
		if(b.isPresent()) {
			return b.get();
		}else {
			return null;
		}
	}
	
	
	public List<Borrower> listar() {
		return (List<Borrower>) borrowerRepository.findAll();
	}
	
}
