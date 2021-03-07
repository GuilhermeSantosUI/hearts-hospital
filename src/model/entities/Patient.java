package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idpaciente;
	private String nomep;
	private String sexo;
	private Date datanascimento;
	private String endereco;
	private String telefone;
	private String numcelular;
	private String email;

	public Patient() {

	}

	public Patient(Integer idpaciente, String nome, String sexo, Date datanascimento, String endereco, String telefone,
			String numcelular, String email) {
		this.idpaciente = idpaciente;
		this.nomep = nome;
		this.sexo = sexo;
		this.datanascimento = datanascimento;
		this.endereco = endereco;
		this.telefone = telefone;
		this.numcelular = numcelular;
		this.email = email;
	}

	public Integer getIdpaciente() {
		return idpaciente;
	}

	public void setIdpaciente(Integer idpaciente) {
		this.idpaciente = idpaciente;
	}

	public String getNome() {
		return nomep;
	}

	public void setNome(String nome) {
		this.nomep = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getDatanascimento() {
		return datanascimento;
	}

	public void setDatanascimento(Date datanascimento) {
		this.datanascimento = datanascimento;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNumcelular() {
		return numcelular;
	}

	public void setNumcelular(String numcelular) {
		this.numcelular = numcelular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idpaciente == null) ? 0 : idpaciente.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		if (idpaciente == null) {
			if (other.idpaciente != null)
				return false;
		} else if (!idpaciente.equals(other.idpaciente))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Patient [idpaciente=" + idpaciente + ", nomep=" + nomep + ", sexo=" + sexo + ", datanascimento="
				+ datanascimento + ", endereco=" + endereco + ", telefone=" + telefone + ", numcelular=" + numcelular
				+ ", email=" + email + "]";
	}

}