package com.fboisier.dojoexam.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "usuario_id")
	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String rol;

	@Transient
	private String passwordConfirmation;

	@Column(updatable = false)
	private Date dateCreate;
	private Date dateUpdate;

	
	
	public Usuario() {

	}
		
	public Usuario(Long id, String firstname, String lastname, String email, String password, String rol,
			String passwordConfirmation) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.rol = rol;
		this.passwordConfirmation = passwordConfirmation;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	
	public String getRol() {
		return rol;
	}


	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
			
		return this.firstname + " " + this.lastname;
	}

	@PrePersist
	protected void onCreate() {
		this.dateCreate = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.dateUpdate = new Date();
	}
	
	
	public boolean sonIguales() {
		return this.passwordConfirmation.equals(this.password);
	}

	public String getNombreCompleto() {
		return this.firstname + " " + this.lastname;
	}
	
}
