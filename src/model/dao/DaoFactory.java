package model.dao;

import db.DB;
import model.dao.impl.DepartamentoDaoJDBC;
import model.dao.impl.VendedorDaoJDBC;

/* ESSA CLASSE VAI TER OPERAÇÕES ESTÁTICAS PARA INSTANCIAR OS Dao'S (DATA ACESS OBJECT) */

public class DaoFactory {
	
	public static VendedorDao criarVendedorDao() {
		return new VendedorDaoJDBC(DB.conectar());
	}
	
	public static DepartamentoDao criarDepartamentoDao() {
		return new DepartamentoDaoJDBC(DB.conectar());
	}

}
