package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.AppointmentDao;
import model.entities.Appointment;
import model.entities.Doctor;
import model.entities.Patient;

public class AppointmentDaoJDBC implements AppointmentDao {

	private Connection conn = DB.getConnection();

	public AppointmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(AppointmentDao obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById() {
		// TODO Auto-generated method stub

	}

	private Doctor instantiateDoctor(ResultSet rs) throws SQLException {
		Doctor doc = new Doctor();
		doc.setCrm(rs.getInt("crm"));
		doc.setNome(rs.getString("nome"));
		doc.setCpf(rs.getString("cpf"));
		doc.setEmail(rs.getString("email"));
		doc.setNumcelular(rs.getString("numcelular"));
		doc.setDatanascimento(rs.getDate("datanascimento"));
		doc.setSenha(rs.getString("senha"));
		return doc;
	}

	private Patient instantiatePatient(ResultSet rs) throws SQLException {
		Patient pat = new Patient();
		pat.setIdpaciente(rs.getInt("idpaciente"));
		pat.setNome(rs.getString("nome"));
		pat.setSexo(rs.getString("sexo"));
		pat.setDatanascimento(rs.getDate("datanascimento"));
		pat.setEndereco(rs.getString("endereco"));
		pat.setTelefone(rs.getString("telefone"));
		pat.setNumcelular(rs.getString("numcelular"));
		pat.setEmail(rs.getString("email"));
		return pat;
	}

	private Appointment instantiateApointment(ResultSet rs, Doctor doc, Patient pat) throws SQLException {
		Appointment apoint = new Appointment();
		apoint.setCrm(doc);
		apoint.setIdpaciente(pat);
		apoint.setDataconsulta(rs.getDate("dataconsulta"));
		apoint.setDescricao(rs.getString("descricao"));
		return apoint;
	}

	@Override
	public List<Appointment> findAll() {
		PreparedStatement st = null;
		List<Appointment> list = new ArrayList<Appointment>();
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM medico, consulta, paciente WHERE medico.crm=consulta.crm AND paciente.idpaciente = consulta.idpaciente");
			rs = st.executeQuery();
			Map<Integer, Doctor> mapDoc = new HashMap<>();
			Map<Integer, Patient> mapPat = new HashMap<>();

			while (rs.next()) {
				Doctor doc = mapDoc.get(rs.getInt("crm"));
				Patient pat = mapPat.get(rs.getInt("idpaciente"));
				if (doc == null && pat == null) {
					doc = instantiateDoctor(rs);
					pat = instantiatePatient(rs);
					mapDoc.put(rs.getInt("crm"), doc);
					mapPat.put(rs.getInt("idpaciente"), pat);
				}
				Appointment apoint = instantiateApointment(rs, doc, pat);
				list.add(apoint);
			}
			return list;
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
