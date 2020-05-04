package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	
	private static Connection conexao = null;
	
	private static Properties carregarPropriedades() {
		try (FileInputStream fs = new FileInputStream("db.properties")){
			Properties propriedades = new Properties();
			propriedades.load(fs);
			return propriedades;
		}
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	//Obter conexao com o banco de dados:
	public static Connection conectar () {
		if (conexao == null) {
			try {
				Properties propriedades = carregarPropriedades();
				String url = propriedades.getProperty("dburl");
				conexao = DriverManager.getConnection(url, propriedades);
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conexao;
	}
	
	//Fechando conexão com o banco de dados:
	public static void fecharConexao() {
		if (conexao != null) {
			try {
				conexao.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}	
	}
	
	//Fechar Statement
	public static void fecharStatement (Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	//Fechar ResultSet
	public static void fecharResultSet (ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		
	}

}
