package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	public void insert(PatientDao obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(PatientDao obj) {
		// TODO Auto-generated method stub

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
