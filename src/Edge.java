
public class Edge{
	public final String from;
	public final String to;
	public final String idReceita;
	/*
	 * Construtor da classe Edge
	 * @param from nó de origem
	 * @param to nó de destino
	 * @param idReceita identificador da receita
	 */
	public Edge(String from, String to, String idReceita){
		assert from != to;
		this.from = from;
		this.to = to;
		this.idReceita = idReceita;
	}
	/*
	 * Metódo para inverter a aresta
	 * @return nova aresta com os nós invertidos
	 */

	public Edge reverse(){
		return new Edge(to, from, idReceita);
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getIdReceita() {
		return idReceita;
	}

	@Override
	public String toString() {
		return  from + "->"+ to+ " via "+idReceita;
	}

}