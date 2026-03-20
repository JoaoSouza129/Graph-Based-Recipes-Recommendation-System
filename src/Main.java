

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

	public static void main(String[] args)  {
		Set<String> availableIngredients = new HashSet<>();

		RecomendaReceitas rec=new RecomendaReceitas("receitas.txt");
		String resposta = null;
		Scanner sc=new Scanner(System.in);
		System.out.println("Quais os ingredientes disponiveis?");
		resposta=sc.nextLine();
		String[] respostas=resposta.toLowerCase().split(" ");
		for (String element : respostas) {
			availableIngredients.add(element);
		}

		
		rec.encotraReceitascompletas(availableIngredients);
		rec.encotraReceitasincompletas(availableIngredients);
		
	}

}
