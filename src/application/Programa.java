package application;

import java.util.Date;

import model.domain.Departamento;
import model.domain.Vendedor;

public class Programa {
	
	public static void main(String[] args) {
		
		Departamento obj = new Departamento(1, "Livro");
		
		Vendedor vendedor = new Vendedor(1, "João", "joao@gmail.com", new Date(), 3000.0, obj);
		
		System.out.println(vendedor);
	}

}
