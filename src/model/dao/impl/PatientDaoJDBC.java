package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.PatientDao;
import model.entities.Patient;

public class PatientDaoJDBC implements PatientDao {

	private Connection conn = DB.getConnection();

	public PatientDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Patient obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO paciente (nome, sexo, datanascimento, endereco, telefone, numcelular, email) VALUES (?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getNome());
			st.setString(2, obj.getSexo());
			Date x = obj.getDatanascimento();
			st.setDate(3, new java.sql.Date(x.getTime()));
			st.setString(4, obj.getEndereco());
			st.setString(5, obj.getTelefone());
			st.setString(6, obj.getNumcelular());
			st.setString(7, obj.getEmail());
			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdpaciente(id);
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

	@Override
	public List<Patient> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM paciente ORDER BY nome");
			rs = st.executeQuery();

			List<Patient> list = new ArrayList<>();
			
			while (rs.next()) {
				Patient obj = new Patient();
				obj.setIdpaciente(rs.getInt("idpaciente"));
				obj.setNome(rs.getString("nome"));
				obj.setSexo(rs.getString("sexo"));
				obj.setDatanascimento(rs.getDate("datanascimento"));
				obj.setEndereco(rs.getString("endereco"));
				obj.setTelefone(rs.getString("telefone"));
				obj.setNumcelular(rs.getString("numcelular"));
				obj.setEmail(rs.getString("email"));
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

}
