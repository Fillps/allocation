package new_allocator.dominio;

import java.util.ArrayList;
import java.util.List;

public class Turma implements Turma_Interface {

	char id;
	int indice_disciplina; // indice da disciplina da turma na LISTA_DISCIPLINAS do BancoDados
	
	String codigo_disciplina;
	int numAlunos;
	List<Aula> lista_aulas;
	List<Integer> lista_indices_professores;
	boolean estah_alocada;

	// *********************************************************************
	// construtores
	
	// usado na IMPORTA��O, pela classe TAG Group
	public Turma(Object id_turma, String codigo_Disciplina)
	{
		this.id = (char)id_turma;
		this.codigo_disciplina = codigo_Disciplina;
		this.lista_aulas = new ArrayList<Aula>();
		this.estah_alocada = false;
		this.lista_indices_professores = new ArrayList<Integer>();	
	}
	
	// usado na classe EXPORTA��O apenas para comparar turmas: s� precisa do id da turma e do id_disciplina
	public Turma(Object id_turma, int indice_disciplina)
	{
		this.id = (char)id_turma;
		this.indice_disciplina = indice_disciplina;
	}
		
	
	// *******************************************************************
	// getters
	public char getID() {
		return id;
	}
	
	// usado na classe Exporta��o
	public String ID_toString() {
		return id+"";
	}
	
	public int getID_Disciplina() {
		return indice_disciplina;
	}
	
	public String getCodigoDisciplina() {
		return codigo_disciplina;
	}

	public int getNumAlunos() {
		return numAlunos;
	}
	
	public List<Aula> getListaAulas() {
		return lista_aulas;
	}
	
	public int getNumAulas() {
		List<Aula> lista_aulas = getListaAulas();
		return lista_aulas.size();
	}	

	public List<Integer> getIndicesProfessores() {
		return lista_indices_professores;
	}

	// ver diretamente a flag da turma, que ser� true quando todas as suas aulas estiverem alocadas
	public boolean getEstahAlocada(){ 
		return estah_alocada;
	}

	public boolean getEstahAlocada_verAulas() { // uma turma s� est� alocada se todas as aulas estiverem
		
		int numAulas = this.getNumAulas();
			
		for (int indice = 0; indice < numAulas; indice++)
		{
			Aula aula = lista_aulas.get(indice);
			boolean aula_alocada = aula.getEstahAlocada();
			
			if(aula_alocada == false) 	// se tem uma aula nao alocada ...
				return false;			// ... a turma nao est� alocada
		}
		
		// se chegou aqui, todas as aulas est�o alocadas => turma alocada
		this.setAlocacao(true);
		return true; 
	}

	// *******************************************************************
	// setters
	
	public void setCodigoDisciplina(String codigoDisciplina) {
		this.codigo_disciplina = codigoDisciplina;
	}
	
	public void setID_Disciplina(int id_disciplina) {
		this.indice_disciplina = id_disciplina;
	}
	
	public void setNumAlunos(String numero_Alunos) { // lido do arquivo de entrada
		this.numAlunos = Integer.parseInt(numero_Alunos); // converte String para int
	}

	public void setNumAlunos(int numero_Alunos) {
		this.numAlunos = numero_Alunos;
	}

	public void setAlocacao(boolean nova_alocacao) {
		this.estah_alocada = nova_alocacao;
	}
	
	public void Marcar_Turma_como_Alocada() {
		setAlocacao(true);
	}
	
	public void Incluir_Aula(Aula aula) { // na lista de aulas da turma
		List<Aula> lista_aulas = getListaAulas();
		lista_aulas.add(aula);
	}

	public void Incluir_Indice_Professor(int indice_prof) {

		List<Integer> lista_indices_profs = this.getIndicesProfessores();

		int indice = lista_indices_profs.lastIndexOf(indice_prof); // busca indice de id_prof na lista de ids de professores da turma

		if (indice == -1) {
			lista_indices_profs.add(indice_prof);
			// System.out.printf("\t\tprof incluido\n");
		}
		// else
		// System.out.printf("\t\tprof jah existe\n");

	}

	public void Incluir_Professores(List<Integer> lista_indices_profs) // USADO NO CLASSE TAG_GROUP
	{
		//System.out.printf("\nturma %c de %s\n", this.id, this.codigo_disciplina);
		//ListaInteiros.Imprimir_Lista_Inteiros("lista de profs da turma", lista_ids_profs);

		int n = lista_indices_profs.size();

		for (int i = 0; i < n; i++) {
			int indice_prof = lista_indices_profs.get(i); // i-�simo professor da lista da entrada

			// System.out.printf("\tincluir prof %s (id = %d)\n",
			// professor.getNome(), professor.getID());

			Incluir_Indice_Professor(indice_prof); // incluir a ID do professor na lista de professores da turma (this)
		}
	}

	// *******************************************************************
	
	@Override
	public boolean equals(Object turma2)
	{
		if (turma2.getClass() == Turma.class)
		{
			Turma turma = (Turma)turma2;
		
			int indiceDisc1 = this.getID_Disciplina();
			int indiceDisc2 = turma.getID_Disciplina();
			
			char id_turma1 = this.getID();
			char id_turma2 = turma.getID();
				
			if(indiceDisc1 == indiceDisc2 && id_turma1 == id_turma2) // mesmo c�digo de disciplina
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	
	//***********************************************************************
	
	// Converter a string contendo as id's das turmas de um 'group' em uma lista de chars s� com as letras de A a Z
	public List<Object> Lista_ids_turmas(String ids_group)
	{
		List<Object> lista = new ArrayList<Object>();
			
		int tamanho = ids_group.length();	// tamanho da String de entrada
		char caracter;
			
		for (int i=0; i < tamanho; i++)
		{
			caracter = ids_group.charAt(i); // i-�simo caracter da String 'id_group'

			if ('A' <= caracter && caracter <= 'Z'){	// se o caracter for uma letra, inclui-lo na lista de sa�da
				lista.add(caracter);
			}
		}
		
		return lista;
	}
	
	// Retornar string com os nomes dos profs concatenados separados por v�rgula e um espa�o
	public String Concatenar_Nomes_Professores_Turma(List<Professor> LISTA_PROFESSORES)
	{
		String stringao = ""; // saida
		
		// lista com os indices dos professores da turma na LISTA_PROFESSORES do Banco de Dados
		List<Integer> lista_ids_professores = this.getIndicesProfessores();
		
		// numero de professores da turma
		int numProfs = lista_ids_professores.size();
		
		// percorre lista de professores da turma
		for(int indice=0; indice < numProfs; indice++)
		{
			// indice do professor na LISTA_PROFESSORES do Banco de Dados
			int indice_professor = lista_ids_professores.get(indice);
			
			// acessa o professor no Banco de Dados
			Professor professor = LISTA_PROFESSORES.get(indice_professor);
			
			// Extrai seu nome
			String nome = professor.getNome();
			
			// concatena na saida
			stringao = stringao + nome;
			
			if(indice < numProfs-1) // se nao chegou no ultimo professor, concatena ", "
				stringao = stringao + ", ";
		}
		
		return stringao;
	}
	
	//****************************************************************
	
	public static void Imprimir_Lista_Turmas_nome(List<Turma> lista_turmas)
	{
		int n = lista_turmas.size();

		for (int indice = 0; indice < n; indice++) {
			Turma turma = lista_turmas.get(indice);
			System.out.printf("\tDisc %s - Turma %c\n", turma.getCodigoDisciplina(), turma.getID());
		}

		System.out.println("\n");
	}
	
	

}
