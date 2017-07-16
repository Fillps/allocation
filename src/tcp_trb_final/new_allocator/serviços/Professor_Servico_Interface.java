package tcp_trb_final.new_allocator.serviços;

import tcp_trb_final.new_allocator.dominio.Professor;

import java.util.List;

public interface Professor_Servico_Interface {

	// Extrair as Indices dos professores de uma lista (usado na classe TAG Group)
	public List<Integer> Extrair_Indices_Lista_Professores(List<Professor> lista_professores);
	
	// Usado na classe IMPORTACAO
	public void Incluir_Professores_nas_suas_Turmas(List<Professor> lista_professores);
	
}
