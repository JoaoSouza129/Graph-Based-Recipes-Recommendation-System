

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class RecomendaReceitasTest {

	@Test
	public void testEncotraReceitascompletas() {
		Set<String> availableIngredients = new HashSet<>();

		 availableIngredients.add("butter");
		 availableIngredients.add("cinnamon");
		 availableIngredients.add("onion");
		 availableIngredients.add("yeast");
		 availableIngredients.add("lemon");
		 availableIngredients.add("wheat");
		 availableIngredients.add("chicken");


		 RecomendaReceitas rec = new RecomendaReceitas("receitasteste.txt");
		 HashMap<String, Set<String>> receitasCompletas = rec.encotraReceitascompletas(availableIngredients);
		 String esperado="{r009=[chicken, onion]}";
		 
		 assertEquals(receitasCompletas.toString().equals(esperado),true);
	}
	
	@Test
	public void testEncotraReceitasincompletas() {
		Set<String> availableIngredients = new HashSet<>();


		 availableIngredients.add("butter");
		 availableIngredients.add("cinnamon");
		 availableIngredients.add("onion");
		 availableIngredients.add("yeast");
		 availableIngredients.add("lemon");
		 availableIngredients.add("wheat");
		 availableIngredients.add("chicken");



		 RecomendaReceitas rec = new RecomendaReceitas("receitasteste.txt");
		 HashMap<Set<String>,Set<String>> receitasincompletas = rec.encotraReceitasincompletas(availableIngredients);
		 String esperado="[[cassava], [macaroni], [soy_sauce, ginger], [lemon_juice, olive_oil], [honey]]";

		 assertEquals(receitasincompletas.values().toString().equals(esperado),true);
	}
	

}
