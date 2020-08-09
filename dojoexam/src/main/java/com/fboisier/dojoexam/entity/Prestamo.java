package com.fboisier.dojoexam.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="prestamos")
public class Prestamo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "prestamo_id")
	private Long id;
	private double prestado;
	

	
	@ManyToOne(fetch = FetchType.LAZY)
    private Lender lender;
	
	@ManyToOne(fetch = FetchType.LAZY)
    private Borrower borrower;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getPrestado() {
		return prestado;
	}
	public void setPrestado(double prestado) {
		this.prestado = prestado;
	}
	public Usuario getLender() {
		return lender;
	}
	public Usuario getBorrower() {
		return borrower;
	}
	public void setLender(Lender lender) {
		this.lender = lender;
	}
	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}
	
	
}
