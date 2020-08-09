package com.fboisier.dojoexam.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fboisier.dojoexam.entity.Lender;

@Repository
public interface LenderRepository extends CrudRepository<Lender, Long>{


}
