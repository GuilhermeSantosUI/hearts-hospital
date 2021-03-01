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
			st = conn.prepareStatement("INSERT INTO medico" + "crm, nome, cpf, email,numcelular, datanascimento, senha"
					+ "VALUES" + "(?,?,?,?,?,?,?)");

			st.setInt(0, obj.getCrm());
			st.setString(1, obj.getNome());
			st.setString(2, obj.getCpf());
			st.setString(3, obj.getEmail());
			st.setString(4, obj.getNumcelular());
			Date x = obj.getDatanascimento();
			st.setDate(5, new java.sql.Date(x.getTime()));
			st.setString(6, obj.getSenha());

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
					"SELECT medico.* FROM medico,consulta where medico.crm = consulta.crm GROUP BY medico.crm, consulta.crm ORDER BY COUNT(*) DESC LIMIT 1 \n"
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
			st = conn.prepareStatement("SELECT * FROM medico ORDER BY nome");
			rs = st.executeQuery();

			List<Doctor> list = new ArrayList<>();

			while (rs.next()) {
				Doctor obj = new Doctor();
				obj.setCrm(rs.getInt("crm"));
				obj.setNome(rs.getString("nome"));
				obj.setCpf(rs.getString("cpf"));
				obj.setEmail(rs.getString("email"));
				obj.setNumcelular(rs.getString("numcelular"));
				obj.setDatanascimento(rs.getDate("datanascimento"));
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
				st = conn.prepareStatement("SELECT nome FROM medico WHERE crm = ? and senha = ?");

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
		doc.setNome(rs.getString("nome"));
		doc.setCpf(rs.getString("cpf"));
		doc.setEmail(rs.getString("email"));
		doc.setNumcelular(rs.getString("numcelular"));
		doc.setDatanascimento(rs.getDate("datanascimento"));
		doc.setSenha(rs.getString("senha"));
		return doc;
	}

}
