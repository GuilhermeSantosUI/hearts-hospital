package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.VistaNavigator;
import db.DB;
import db.DbException;
import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.dao.DoctorDao;
import model.entities.Doctor;

public class DoctorDaoJDBC implements DoctorDao {

	private Connection conn = DB.getConnection();

	public DoctorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Doctor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("insert into medico (crm, nomemed, cpf, emailmed, numcelularmed, datanascimentomed, senha)"
					+ "values (?, ?, ?, ?, ?, ?, ?)");

			st.setInt(1, obj.getCrm());
			st.setString(2, obj.getNomemed());
			st.setString(3, obj.getCpf());
			st.setString(4, obj.getEmailmed());
			st.setString(5, obj.getNumcelularmed());
			Date x = obj.getDatanascimentomed();
			st.setDate(6, new java.sql.Date(x.getTime()));
			st.setString(7, obj.getSenha());
			st.execute();
			System.out.println(obj.toString());
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Doctor findBusyDoctor() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT medico.* FROM medico,consulta where medico.crm = consulta.medicoid GROUP BY medico.crm, consulta.medicoid ORDER BY COUNT(*) DESC LIMIT 1 \n"
							+ "");
			rs = st.executeQuery();
			Map<Integer, Doctor> mapDoc = new HashMap<>();
			if (rs.next()) {
				Doctor doc = mapDoc.get(rs.getInt("crm"));
				if (doc == null) {
					doc = instantiateDoctor(rs);
					mapDoc.put(rs.getInt("crm"), doc);
					return doc;
				}
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Doctor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM medico ORDER BY nomemed");
			rs = st.executeQuery();

			List<Doctor> list = new ArrayList<>();

			while (rs.next()) {
				Doctor obj = new Doctor();
				obj.setCrm(rs.getInt("crm"));
				obj.setNomemed(rs.getString("nomemed"));
				obj.setCpf(rs.getString("cpf"));
				obj.setEmailmed(rs.getString("emailmed"));
				obj.setNumcelularmed(rs.getString("numcelularmed"));
				obj.setDatanascimentomed(rs.getDate("datanascimentomed"));
				obj.setSenha(rs.getString("senha"));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void handleLogin(TextField crmDoctor, PasswordField passDoctor) {
		if (crmDoctor.getText().trim().isEmpty() || passDoctor.getText().trim().isEmpty()) {
			Alerts.showAlert("Acho que você esqueceu de algo!", "CRM e/ou senha estão nulos",
					"Digite os dados para entrar na plataforma ", AlertType.ERROR);
		} else {
			PreparedStatement st = null;
			ResultSet rs = null;

			int crm = Integer.parseInt(crmDoctor.getText());
			String pass = passDoctor.getText();

			try {
				st = conn.prepareStatement("SELECT nomemed FROM medico WHERE crm = ? and senha = ?");

				st.setInt(1, crm);
				st.setString(2, pass);
				rs = st.executeQuery();

				if (rs.next()) {
					VistaNavigator.loadVista(VistaNavigator.DASHBOARD);
				} else {
					Alerts.showAlert("Login Error", null, "CRM e/ou Senha incorretos", AlertType.ERROR);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
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

}