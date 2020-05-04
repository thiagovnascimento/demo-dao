package model.dao;

import java.util.List;

import model.domain.Departamento;

public interface DepartamentoDao {
	
	void inserir (Departamento obj);
	void atualizar (Departamento obj);
	void deletarPeloId (Integer id);
	Departamento encontrarPeloId (Integer id);
	List <Departamento> encontrarTudo ();

}
