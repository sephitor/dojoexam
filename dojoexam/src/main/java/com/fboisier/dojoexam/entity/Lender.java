package com.fboisier.dojoexam.entity;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "lender")
public class Lender extends Usuario {
	
	private double money;
	private double balance;
	
	@OneToMany(mappedBy="lender", fetch = FetchType.LAZY)
    private List<Prestamo> prestamos;
	
		
	public Lender() {
		super();
	}


	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public List<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}
	
}
