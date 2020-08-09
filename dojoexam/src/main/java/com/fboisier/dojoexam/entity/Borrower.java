package com.fboisier.dojoexam.entity;


import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "borrower")
public class Borrower  extends Usuario {

	private String needfor; 
	private String description;
	private double needed;
	private double raised;
	
	@OneToMany(mappedBy="borrower", fetch = FetchType.LAZY)
    private List<Prestamo> prestamos;
	
	public Borrower() {
		super();
	}
	
	public String getNeedfor() {
		return needfor;
	}
	public void setNeedfor(String needfor) {
		this.needfor = needfor;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getNeeded() {
		return needed;
	}
	public void setNeeded(double needed) {
		this.needed = needed;
	}
	public double getRaised() {
		return raised;
	}
	public void setRaised(double raised) {
		this.raised = raised;
	}
	public List<Prestamo> getPrestamos() {
		return prestamos;
	}
	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}
	
	
	
}
