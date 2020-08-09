package com.fboisier.dojoexam.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fboisier.dojoexam.entity.Borrower;


@Repository
public interface BorrowerRepository extends CrudRepository<Borrower, Long>{
	
}
