package tcp_trb_final.new_allocator.dominio;

import java.util.List;

public interface Professor_Interface {
	// getters
	public List<Turma> getTurmas();
	public String getNome();
	public int getID();
		
	// setters
	public void setID(int id_prof);
	public void Incluir_Turma(Turma turma);
	
	// para compara��es e buscas em listas
	public boolean equals(Object prof2);
}
