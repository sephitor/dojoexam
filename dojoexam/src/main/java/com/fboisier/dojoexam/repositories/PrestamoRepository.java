package com.fboisier.dojoexam.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fboisier.dojoexam.entity.Prestamo;


@Repository
public interface PrestamoRepository extends CrudRepository<Prestamo, Long>{
	
	@Modifying
	@Transactional
	@Query(value = "insert into prestamos (lender_usuario_id,borrower_usuario_id,prestado) VALUES (:lender_id,:borrower_id,:prestado)", nativeQuery = true)
    void saveDirect(@Param("lender_id") Long lender_id, @Param("borrower_id") Long borrower_id, @Param("prestado") double prestado);
	
	@Modifying
	@Transactional
	@Query(value = "update prestamos set  prestado = :prestado where lender_usuario_id = :lender_id and borrower_usuario_id = :borrower_id ", nativeQuery = true)
    void updateDirect(@Param("lender_id") Long lender_id, @Param("borrower_id") Long borrower_id, @Param("prestado") double prestado);

	@Query(value = "SELECT COUNT(1) FROM prestamos where lender_usuario_id = :lender_id and borrower_usuario_id = :borrower_id", nativeQuery = true)
	Long existeRegistro(@Param("lender_id") Long lender_id, @Param("borrower_id") Long borrower_id);

	@Query(value = "SELECT prestado FROM prestamos where lender_usuario_id = :lender_id and borrower_usuario_id = :borrower_id", nativeQuery = true)
	double obtenerPrestado(@Param("lender_id") Long lender_id, @Param("borrower_id") Long borrower_id);

	
	
	
	
}
