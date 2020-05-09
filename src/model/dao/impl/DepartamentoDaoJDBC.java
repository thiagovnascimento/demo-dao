package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartamentoDao;
import model.domain.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDao{
	
	private Connection conn;
	
	public DepartamentoDaoJDBC (Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Departamento obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO department (Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			
			int linhasAfetadas = st.executeUpdate();
			
			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.fecharResultSet(rs);
			}
			else {
				throw new DbException("Erro! Nenhuma linha afetada");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.fecharStatement(st);
		}
		
	}

	@Override
	public void atualizar(Departamento obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE department "
					+ "SET Name = ? " 
					+ "WHERE Id = ?");
			st.setString(1, obj.getNome());
			st.setInt(2, obj.getId());
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.fecharStatement(st);
		}
		
	}

	@Override
	public void deletarPeloId(Integer id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.fecharStatement(st);
		}
		
	}

	@Override
	public Departamento encontrarPeloId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Departamento departamento = instanciarDepartamento(rs);
				return departamento;
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.fecharStatement(st);
			DB.fecharResultSet(rs);
		}
		return null;
	}

	@Override
	public List<Departamento> encontrarTudo() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		List <Departamento> lista = new ArrayList<>();
		
		try {
			st = conn.prepareStatement("SELECT * FROM department ORDER BY Name");
			rs = st.executeQuery();
			
			while(rs.next()) {
				Departamento departamento = instanciarDepartamento(rs);
				lista.add(departamento);
			}
			return lista;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.fecharStatement(st);
			DB.fecharResultSet(rs);
		}
	}
	
	private Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
		Departamento departamento = new Departamento();
		departamento.setId(rs.getInt("Id"));
		departamento.setNome(rs.getString("Name"));
		return departamento;
	}


}
