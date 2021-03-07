package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Doctor implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer crm;
	private String nomemed;
	private String cpf;
	private String emailmed;
	private String numcelularmed;
	private Date datanascimentomed;
	private String senha;

	public Doctor() {

	}

	public Doctor(Integer crm, String nomemed, String cpf, String emailmed, String numcelularmed,
			Date datanascimentomed, String senha) {
		super();
		this.crm = crm;
		this.nomemed = nomemed;
		this.cpf = cpf;
		this.emailmed = emailmed;
		this.numcelularmed = numcelularmed;
		this.datanascimentomed = datanascimentomed;
		this.senha = senha;
	}

	public Integer getCrm() {
		return crm;
	}

	public void setCrm(Integer crm) {
		this.crm = crm;
	}

	public String getNomemed() {
		return nomemed;
	}

	public void setNomemed(String nomemed) {
		this.nomemed = nomemed;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmailmed() {
		return emailmed;
	}

	public void setEmailmed(String emailmed) {
		this.emailmed = emailmed;
	}

	public String getNumcelularmed() {
		return numcelularmed;
	}

	public void setNumcelularmed(String numcelularmed) {
		this.numcelularmed = numcelularmed;
	}

	public Date getDatanascimentomed() {
		return datanascimentomed;
	}

	public void setDatanascimentomed(Date datanascimentomed) {
		this.datanascimentomed = datanascimentomed;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "Doctor [crm=" + crm + ", nomemed=" + nomemed + ", cpf=" + cpf + ", emailmed=" + emailmed
				+ ", numcelularmed=" + numcelularmed + ", datanascimentomed=" + datanascimentomed + ", senha=" + senha
				+ "]";
	}

	

}