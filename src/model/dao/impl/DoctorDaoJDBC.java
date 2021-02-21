package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DoctorDao;
import model.entities.Doctor;

public class DoctorDaoJDBC implements DoctorDao {

	DB con = new DB();
	private Connection conn = con.getConnection();

	public DoctorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Doctor obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"DELETE FROM medico WHERE crm = ? ");
			st.setInt(1, id);
			st.executeUpdate();
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Doctor findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
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
				obj.setSenhar(rs.getString("senha"));
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
