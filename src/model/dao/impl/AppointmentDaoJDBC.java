package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
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
	public void insert(Appointment obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO consulta (medicoid, pacienteid, dataconsulta, descricao) VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, obj.getMedicoid().getCrm());
			st.setInt(2, obj.getPacienteid().getIdpaciente());
			Date x = obj.getDataconsulta();
			st.setDate(3, new java.sql.Date(x.getTime()));
			st.setString(4, obj.getDescricao());
			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdconsulta(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected.");
			}
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	private Doctor instantiateDoctor(ResultSet rs) throws SQLException {
		Doctor doc = new Doctor();
		doc.setCrm(rs.getInt("crm"));
		doc.setNomemed(rs.getString("nomemed"));
		doc.setCpf(rs.getString("cpf"));
		doc.setEmailmed(rs.getString("emailmed"));
		doc.setNumcelularmed(rs.getString("numcelularmed"));
		doc.setDatanascimentomed(rs.getDate("datanascimentomed"));
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
		Appointment appoint = new Appointment();
		appoint.setIdconsulta(rs.getInt("idconsulta"));
		appoint.setMedicoid(doc);
		appoint.setPacienteid(pat);
		appoint.setDataconsulta(rs.getDate("dataconsulta"));
		appoint.setDescricao(rs.getString("descricao"));
		return appoint;
	}

	@Override
	public List<Appointment> findAll() {
		PreparedStatement st = null;
		List<Appointment> list = new ArrayList<Appointment>();
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM consulta, medico, paciente \n"
					+ "WHERE consulta.medicoid = medico.crm AND\n"
					+ "consulta.pacienteid = paciente.idpaciente \n"
					+ "order by dataconsulta");
			rs = st.executeQuery();
			Map<Integer, Doctor> mapDoc = new HashMap<>();
			Map<Integer, Patient> mapPat = new HashMap<>();

			while (rs.next()) {
				Doctor doc = mapDoc.get(rs.getInt("medicoid"));
				Patient pat = mapPat.get(rs.getInt("pacienteid"));
				if (doc == null && pat == null) {
					doc = instantiateDoctor(rs);
					pat = instantiatePatient(rs);
					mapDoc.put(rs.getInt("medicoid"), doc);
					mapPat.put(rs.getInt("pacienteid"), pat);
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

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM consulta WHERE idconsulta = ? ");
			st.setInt(1, id);
			st.executeUpdate();
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

}
