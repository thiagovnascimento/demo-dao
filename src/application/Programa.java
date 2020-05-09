package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.domain.Departamento;
import model.domain.Vendedor;

public class Programa {
	
	public static void main(String[] args) {
		
		Scanner leia = new Scanner (System.in);
		
		VendedorDao vendedorDao = DaoFactory.criarVendedorDao();
		
		System.out.println("--- TESTE 1: Vendedor encontrarpeloId ---");
		Vendedor vendedor = vendedorDao.encontrarpeloId(3);
		System.out.println(vendedor);
		
		System.out.println();
		
		System.out.println("--- TESTE 2: Vendedor encontrarPeloDepartamento ---");
		Departamento departamento = new Departamento(2, null);
		List<Vendedor> lista = vendedorDao.encontrarPeloDepartamento(departamento);
		for (Vendedor obj : lista) {
			System.out.println(obj);
		}
		
		System.out.println();
		System.out.println("--- TESTE 3: Vendedor encontrarTudo ---");
		lista = vendedorDao.encontrarTudo();
		for (Vendedor obj : lista) {
			System.out.println(obj);
		}
		
		System.out.println();
		System.out.println("--- TESTE 4: Vendedor inserir ---");
		Vendedor novoVendedor = new Vendedor(null, "Cleusa", "cleusa@gmail.com", new Date(), 4000.0, departamento);
		vendedorDao.inserir(novoVendedor);
		System.out.println("Inserido! Novo ID: " + novoVendedor.getId());
		
		System.out.println();
		System.out.println("--- TESTE 5: Vendedor atualizar ---");
		vendedor = vendedorDao.encontrarpeloId(1);
		vendedor.setNome("Marcos Afonso");
		vendedorDao.atualizar(vendedor);
		System.out.println("Atualização Completa!");
		
		System.out.println();
		System.out.println("--- TESTE 6: Vendedor DeletarPeloId ---");
		System.out.print("Digite o Id que será deletado: ");
		int id = leia.nextInt();
		vendedorDao.deletarPeloId(id);
		System.out.println("Id deletado!");
		
		leia.close();
	}
	
	

}
