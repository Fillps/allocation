package new_allocator.dominio;

import java.util.List;

public interface Turma_Interface {
	// getters
	public char getID();
	public String ID_toString();
	public int getID_Disciplina();
	public String getCodigoDisciplina();
	public int getNumAlunos();
	public List<Aula> getListaAulas();
	public int getNumAulas();
	public List<Integer> getIndicesProfessores();
	public boolean getEstahAlocada();			// ver diretamente a flag da turma
	public boolean getEstahAlocada_verAulas();  // ver aula por aula
	
	// setters
	public void setCodigoDisciplina(String codigoDisciplina);
	public void setID_Disciplina(int id_disciplina);
	public void setNumAlunos(String numero_Alunos);
	public void setNumAlunos(int numero_Alunos);
	public void setAlocacao(boolean nova_alocacao);
	public void Marcar_Turma_como_Alocada();
	public void Incluir_Aula(Aula aula); // na lista de aulas da turma
	public void Incluir_Indice_Professor(int indice_prof);
	public void Incluir_Professores(List<Integer> lista_indices_profs);  // USADO NO CLASSE TAG_GROUP
	
	// para compara��es e buscas em listas
	public boolean equals(Object turma2);
	
	// Converter a string contendo as id's das turmas de um 'group' em uma lista de chars s� com as letras de A a Z
	public List<Object> Lista_ids_turmas(String ids_group);
	
	// Retornar string com os nomes dos profs concatenados separados por v�rgula e um espa�o
	public String Concatenar_Nomes_Professores_Turma(List<Professor> LISTA_PROFESSORES);
}
