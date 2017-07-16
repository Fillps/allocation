package tcp_trb_final.new_allocator.dominio;

import java.util.List;

public interface Disciplina_Interface {
	
	// getters
	public String getCodigo();
	public String Codigo_toString();		// se codigo n�o for String,
	public String getNome();
	public List<Turma> get_Lista_Turmas();
	
	// setters
	public void Incluir_Turma(Turma turma);
	public void Incluir_Lista_Turmas(List<Turma> lista_turmas);
	
	// para compara��es e buscas em listas
	public boolean equals(Object disciplina2);
}
