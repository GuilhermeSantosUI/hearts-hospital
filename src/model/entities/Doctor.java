package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Doctor implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer crm;
	private String nome;
	private String cpf;
	private String email;
	private String numcelular;
	private Date datanascimento;
	private String senhar;

	public Doctor() {

	}

	public Doctor(Integer crm, String nome, String cpf, String email, String numcelular, Date datanascimento,
			String senhar) {
		this.crm = crm;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.numcelular = numcelular;
		this.datanascimento = datanascimento;
		this.senhar = senhar;
	}

	public Integer getCrm() {
		return crm;
	}

	public void setCrm(Integer crm) {
		this.crm = crm;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumcelular() {
		return numcelular;
	}

	public void setNumcelular(String numcelular) {
		this.numcelular = numcelular;
	}

	public Date getDatanascimento() {
		return datanascimento;
	}

	public void setDatanascimento(Date datanascimento) {
		this.datanascimento = datanascimento;
	}

	public String getSenhar() {
		return senhar;
	}

	public void setSenhar(String senhar) {
		this.senhar = senhar;
	}

	@Override
	public String toString() {
		return "Medico [crm=" + crm + ", nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", numcelular="
				+ numcelular + ", datanascimento=" + datanascimento + ", senhar=" + senhar + "]";
	}

}
