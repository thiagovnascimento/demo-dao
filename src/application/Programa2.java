package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartamentoDao;
import model.domain.Departamento;

public class Programa2 {

	public static void main(String[] args) {
		Scanner leia = new Scanner (System.in);
		
		DepartamentoDao departamentoDao = DaoFactory.criarDepartamentoDao();
		
		System.out.println("--- TESTE 1: Departamento encontrarPeloID ---");
		Departamento departamento = departamentoDao.encontrarPeloId(2);
		System.out.println(departamento);
		
		System.out.println();
		System.out.println("---TESTE 2: Departamento encontrarTudo ---");
		List <Departamento> lista = departamentoDao.encontrarTudo();
		for (Departamento dep : lista) {
			System.out.println(dep);
		}
		
		System.out.println();
		System.out.println("TESTE 3: Departamento inserir ---");
		Departamento novoDepartamento = new Departamento(null, "Food");
		departamentoDao.inserir(novoDepartamento);
		System.out.println("Inserido! Novo Id: " + novoDepartamento.getId());
		
		System.out.println();
		System.out.println("TESTE 4: Departamento atualizar ---");
		departamento = departamentoDao.encontrarPeloId(1);
		departamento.setNome("Material de construção");
		departamentoDao.atualizar(departamento);
		System.out.println("Atualização completa!");
		
		System.out.println();
		System.out.println("TESTE 5: Departamento deletarPeloId ---");
		System.out.print("Digite o Id que será deletado: ");
		int id = leia.nextInt();
		departamentoDao.deletarPeloId(id);
		System.out.println("Id deletado!");
		
		
		leia.close();
	}

}
