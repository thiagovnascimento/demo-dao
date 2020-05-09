package model.dao;

import java.util.List;

import model.domain.Departamento;
import model.domain.Vendedor;

public interface VendedorDao {
	
	void inserir (Vendedor obj);
	void atualizar (Vendedor obj);
	void deletarPeloId (Integer id);
	Vendedor encontrarpeloId (Integer id);
	List <Vendedor> encontrarTudo ();
	List <Vendedor> encontrarPeloDepartamento(Departamento departamento);

}
