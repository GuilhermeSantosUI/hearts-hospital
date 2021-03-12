package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idconsulta;
	private Doctor medicoid;
	private Patient pacienteid;
	private Date dataconsulta;
	private String descricao;

	public Appointment() {

	}

	public Appointment(Integer idconsulta, Doctor medicoid, Patient pacienteid, Date dataconsulta, String descricao) {
		this.idconsulta = idconsulta;
		this.medicoid = medicoid;
		this.pacienteid = pacienteid;
		this.dataconsulta = dataconsulta;
		this.descricao = descricao;
	}

	public Integer getIdconsulta() {
		return idconsulta;
	}

	public void setIdconsulta(Integer idconsulta) {
		this.idconsulta = idconsulta;
	}

	public Doctor getMedicoid() {
		return medicoid;
	}

	public void setMedicoid(Doctor medicoid) {
		this.medicoid = medicoid;
	}

	public Patient getPacienteid() {
		return pacienteid;
	}

	public void setPacienteid(Patient pacienteid) {
		this.pacienteid = pacienteid;
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
		return "Appointment [idconsulta=" + idconsulta + ", medicoid=" + medicoid + ", pacienteid=" + pacienteid
				+ ", dataconsulta=" + dataconsulta + ", descricao=" + descricao + "]";
	}

}