package tcp_trb_final.new_allocator.dominio;

import java.util.List;

public interface Aula_Interface {

	// getters
	public String getCodDisc();
	public int getID_Disciplina();
	public char getIDTurma();
	public int getIndiceTurma();
	public List<Integer> getListaIDsProfs();
	public boolean getEstahAlocada();		// m�todo da superclasse AbstractAula
	
	// setters
	public void setSala(Sala sala);
	public void setAlocada();
	public void setCodDisc(String codigo);
	public void setID_Disc(int indice_disc);
	public void setIDTurma(char id_turma);
	
	// para compara��es e buscas em listas
	public boolean equals(Object aula);
}
