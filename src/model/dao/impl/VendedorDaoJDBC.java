package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.domain.Departamento;
import model.domain.Vendedor;

public class VendedorDaoJDBC implements VendedorDao {
	
	private Connection conn;
	
	public VendedorDaoJDBC (Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Vendedor obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getAniversario().getTime()));
			st.setDouble(4, obj.getSalarioBase());
			st.setInt(5, obj.getDepartamento().getId());
			
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
	public void atualizar(Vendedor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ?");
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getAniversario().getTime()));
			st.setDouble(4, obj.getSalarioBase());
			st.setInt(5, obj.getDepartamento().getId());
			st.setInt(6, obj.getId());
			
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
			st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
			
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
	public Vendedor encontrarpeloId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?"	);
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()){
				Departamento departamento = instanciarDepartamento(rs);
				
				Vendedor vendedor = instanciarVendedor(rs, departamento);
				
				return vendedor;
			}
			return null;
		}
		
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.fecharStatement(st);
			DB.fecharResultSet(rs);
		}

	}

	@Override
	public List<Vendedor> encontrarTudo() { //ENCONTRAR TODOS OS VENDEDORES E SEUS DEPARTAMENTOS
		PreparedStatement st = null;
		ResultSet rs = null;
		
		List <Vendedor> lista = new ArrayList<>();
		Map <Integer, Departamento> map = new HashMap<>();
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name"); // EM ORDEM ALFABÉTICA
			
			rs = st.executeQuery();
			
			while (rs.next()) {
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instanciarDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Vendedor vendedor = instanciarVendedor(rs, dep);
				lista.add(vendedor);
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

	@Override
	public List<Vendedor> encontrarPeloDepartamento(Departamento departamento) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		List <Vendedor> lista = new ArrayList<Vendedor>();
		Map <Integer, Departamento> map = new HashMap<>(); // CONTROLAR SE JÁ EXISTE O DEPARTAMENTO
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name"); //EM ORDEM ALFABÉTICA
			
			st.setInt(1, departamento.getId());
			rs = st.executeQuery();
			
			while (rs.next()) { //PODE TER 0 OU MAIS [POR ISSO O WHILE]
				
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instanciarDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep); //SALVAR O DEPARTAMENTO NO MAP
				}
				Vendedor vendedor = instanciarVendedor(rs, dep);
				lista.add(vendedor);
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


	private Vendedor instanciarVendedor(ResultSet rs, Departamento departamento) throws SQLException {
		Vendedor vendedor = new Vendedor();
		vendedor.setId(rs.getInt("Id"));
		vendedor.setNome(rs.getString("Name"));
		vendedor.setEmail(rs.getString("Email"));
		vendedor.setAniversario(rs.getDate("BirthDate"));
		vendedor.setSalarioBase(rs.getDouble("BaseSalary"));
		vendedor.setDepartamento(departamento);
		return vendedor;
	}

	private Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
		Departamento departamento = new Departamento();
		departamento.setId(rs.getInt("DepartmentId"));
		departamento.setNome(rs.getString("DepName"));
		return departamento;
	}

}
