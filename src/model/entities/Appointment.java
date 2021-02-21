package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {

	private static final long serialVersionUID = 1L;

	private Doctor crm;
	private Patient idpaciente;
	private Date dataconsulta;
	private String descricao;

	public Appointment() {

	}

	public Appointment(Doctor crm, Patient idpaciente, Date dataconsulta, String descricao) {
		this.crm = crm;
		this.idpaciente = idpaciente;
		this.dataconsulta = dataconsulta;
		this.descricao = descricao;
	}

	public Doctor getCrm() {
		return crm;
	}

	public void setCrm(Doctor crm) {
		this.crm = crm;
	}

	public Patient getIdpaciente() {
		return idpaciente;
	}

	public void setIdpaciente(Patient idpaciente) {
		this.idpaciente = idpaciente;
	}

	public Date getDataconsulta() {
		return dataconsulta;
	}

	public void setDataconsulta(Date dataconsulta) {
		this.dataconsulta = dataconsulta;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "Consulta [crm=" + crm + ", idpaciente=" + idpaciente + ", dataconsulta=" + dataconsulta + ", descricao="
				+ descricao + "]";
	}

}