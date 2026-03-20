
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class RecomendaReceitas {
	HashMap<String, Set<String>> livroReceitas;
	Graph graph = new Graph();
	List<String> linhas;
	String[] palavras = null;
	/**
	 * Construtor da classe RecomendaReceitas
	 * @param caminho caminho do ficheiro com as receitas
	 */
	public RecomendaReceitas(String caminho) {

		this.livroReceitas = new HashMap<>();
		this.graph = new Graph();
		this.linhas = new ArrayList<>();
		try {
			File file = new File(ClassLoader.getSystemResource(caminho).getFile());
			linhas = Files.readAllLines(file.toPath());

		} catch (java.io.IOException e) {
			System.err.println("Erro ao ler o ficheiro: " + e.getMessage());
			e.printStackTrace();
		}
		for (String i : linhas) {
			palavras = i.split(",");
			String id = palavras[0];
			Set<String> ingredients = new HashSet<>();
			for (int k = 1; k < palavras.length; k++) {
				ingredients.add(palavras[k].trim());


			}
			String[] ingredientArray = ingredients.toArray(new String[0]);

			for (int j = 0; j < ingredientArray.length; j++) {
				for (int k = j + 1; k < ingredientArray.length; k++) {
					String ing1 = ingredientArray[j];
					String ing2 = ingredientArray[k];

					graph.addEdge(new Edge(ing1, ing2, id));

				}
			}
			
			livroReceitas.put(id, new HashSet<>(ingredients));
		}
	}
	/**
	 * Método que encontra receitas completas com os ingredientes disponíveis
	 * @param ingredientesdisponiveis conjunto de ingredientes disponíveis
	 * @return um mapa com as receitas completas e os seus ingredientes
	 */
	public HashMap<String, Set<String>> encotraReceitascompletas(Set<String> ingredientesdisponiveis) {
		HashMap<String, Set<String>> receitasCompletas = new HashMap<>();
		//int i = 0;

	    for (Map.Entry<String, Set<String>> entry : livroReceitas.entrySet()) {
	        Set<String> recipeIngredients = entry.getValue();
	        Set<String> ingredientes = new HashSet<>(recipeIngredients);
	        ingredientes.retainAll(ingredientesdisponiveis);

	        if (ingredientes.equals(recipeIngredients)) {
	            receitasCompletas.put(entry.getKey(), ingredientes);
	        }
	    }
	    
	    List<Map.Entry<String, Set<String>>> ordenado = new ArrayList<>(receitasCompletas.entrySet());
	    Collections.sort(ordenado, new Comparator<Map.Entry<String, Set<String>>>() {
	        @Override
	        public int compare(Map.Entry<String, Set<String>> r1, Map.Entry<String, Set<String>> r2) {
	            String id1 = r1.getKey();
	            String id2 = r2.getKey();
	            return id1.compareTo(id2);
	        }
	    });
	    // Print results correctly
	    for (Map.Entry<String, Set<String>> entry : receitasCompletas.entrySet()) {
	        System.out.println("Com esses ingrediente pode fazer: "+entry.getKey() + " " + entry.getValue());
	    }
	    System.out.println();
		return receitasCompletas;
	}

	/**
	 * Método que encontra receitas incompletas com os ingredientes disponíveis
	 * @param ingredientesdisponiveis conjunto de ingredientes disponíveis
	 * @return um mapa com as receitas incompletas e os seus ingredientes em falta
	 */

	public HashMap<Set<String>, Set<String>> encotraReceitasincompletas(Set<String> ingredientesdisponiveis) {

		HashMap<Set<String>, Set<String>> receitasIncompletas = new HashMap<>();
		HashMap<Set<String>, String> receitaParaId = new HashMap<>();
		Set<String> nodes = graph.getNodes();
		for (Map.Entry<String, Set<String>> entry : livroReceitas.entrySet()) {
			String receitaId = entry.getKey();
		    Set<String> r = entry.getValue();
		    receitaParaId.put(r, receitaId);
			
			Set<String> ingredientesdografo = new HashSet<>(r);
			Set<String> ingredientes = new HashSet<>(r);
			ingredientesdografo.retainAll(nodes);
			Set<String> ingredientesemfalta = new HashSet<>(ingredientesdografo);
			ingredientesemfalta.removeAll(ingredientesdisponiveis);
			if (ingredientesemfalta.size() <= 2 && ingredientesdografo.equals(r)) {
				boolean alcanca=true;
				for (String emfalta : ingredientesemfalta) {
					boolean conecta = false;
					for (String disponivel : ingredientes) {
						if (graph.temCaminho(disponivel, emfalta)) {
							conecta=true;
							break;
						}
					}
					if(!conecta) {
						alcanca=false;
						break;
					}
				}
				if(alcanca && !ingredientesemfalta.isEmpty()) {
					receitasIncompletas.put(r,ingredientesemfalta);
				}

			}

		}
	    List<Map.Entry<Set<String>, Set<String>>> ordenado = new ArrayList<>(receitasIncompletas.entrySet());
	    Collections.sort(ordenado, new Comparator<Map.Entry<Set<String>, Set<String>>>() {
	        @Override
	        public int compare(Map.Entry<Set<String>, Set<String>> r1, Map.Entry<Set<String>, Set<String>> r2) {
	            String id1 = receitaParaId.get(r1.getKey());
	            String id2 = receitaParaId.get(r2.getKey());
	            return id1.compareTo(id2);
	        }
	    });
		for (Map.Entry<Set<String>, Set<String>> entry : ordenado) {
			String receitaId = receitaParaId.get(entry.getKey());
		    System.out.print("Se comprar " + entry.getValue() + " pode fazer "+receitaId+":" + entry.getKey() +"\n");
		}
		return receitasIncompletas;

	}
}

