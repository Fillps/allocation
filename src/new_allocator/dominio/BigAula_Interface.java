package new_allocator.dominio;

import java.util.List;

public interface BigAula_Interface {

	//getters
	public List<Aula> getListaAulas();
	public List<Integer> getListaIndicesTurmas();
	public boolean getEstahAlocada(); // m�todo da superclasse AbstractAula
	
	// setters
	public void setSala(Sala sala);
	public void setAlocada();
	
	// para compara��es e buscas em listas
	public boolean equals(Object bigAula);
	
	// Usado no construtor
	public int Extrair_Maior_Duracao(List<Aula> lista_aulas);
		
}
