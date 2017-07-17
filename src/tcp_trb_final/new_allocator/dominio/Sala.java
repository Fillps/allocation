package tcp_trb_final.new_allocator.dominio;

import java.util.ArrayList;
import java.util.List;

public class Sala implements Sala_Interface {
	String id;
	List<TipoRecurso> lista_tipos_recurso;
	int numLugares;
	boolean disponivel;
	String observacao;
	int indice_Predio; // indice do Predio desta sala na LISTA_PREDIOS do BancoDados:
					   // valor obtido no momento da cria��o da LISTA_SALAS

	boolean[][] matriz_disponibilidade;
	int numLinhas; 		// DIMENS�O da matriz_disponibilidade
	int numColunas; 	// DIMENS�O da matriz_disponibilidade

	String[][] matriz_turmas; // para teste: guardar as disciplinas que reservaram o local

	// ******************************************************************
	// construtor
	// usado na classe Tag_Room
	public Sala(String available) {
		this.set_Disponibilidade(available); // seta o atributo 'disponivel'
		this.lista_tipos_recurso = new ArrayList<TipoRecurso>();
	}

	// ******************************************************************

	// getters
	public String getID() {
		return id;
	}

	// se id n�o for String, mas algum m�todo requer que seja, basta alterar
	// implementa��o deste m�todo
	public String ID_toString() {
		return id;
	}

	public List<TipoRecurso> getListaTipos() {
		return lista_tipos_recurso;
	}

	public int getNumLugares() {
		return numLugares;
	}

	public boolean getDisponibilidade() {
		return disponivel;
	}

	public String getObs() {
		return observacao;
	}

	public int getIndicePredio() {
		return indice_Predio;
	}

	public boolean[][] getMatriz_disponibilidade() {
		return matriz_disponibilidade;
	}

	public int getNumLinhas() {
		return numLinhas;
	}

	public int getNumColunas() {
		return numColunas;
	}

	public String[][] getMatriz_Turmas() { // para teste, talvez deixar
		return matriz_turmas;
	}

	// ******************************************************************
	// setters: setar dados da sala a partir da leitura do arquivo de entrada

	public void set_Disponibilidade(String available) {
		if(available == null){
			available = new String();
		}
		if (available.length() > 0)
			disponivel = Boolean.parseBoolean(available);
		else
			disponivel = true;
	}

	public void setID(String id_sala) {
		id = id_sala;
	}

	public void setNumLugares(String num_places) {
		numLugares = Integer.parseInt(num_places);
	}

	public void setObservacao(String observacaoSala) {
		observacao = observacaoSala;
	}

	public void setListaTipos(String feature_ids) {
		Extrair_lista_numeros_de_string(feature_ids);
	}

	// ----------------------------------------------------
	// outros setters

	public void Atualiza_Disponibilidade(boolean nova_disponibilidade) {
		disponivel = nova_disponibilidade;
	}

	// usado no momento de criar a LISTA_SALAS do Banco de Dados
	public void setDadosSalaDisponivel(int indicePredio, int numHorarios, int numDias) {
		this.indice_Predio = indicePredio;
		this.numLinhas = numHorarios;
		this.numColunas = numDias;

		this.matriz_disponibilidade = new boolean[numHorarios][numDias];
		Preenche_Matriz_Disponibilidade_com_TRUE();

		this.matriz_turmas = new String[numHorarios][numDias]; // para teste
	}

	// preencher matriz de disponibilidades com true
	public void Preenche_Matriz_Disponibilidade_com_TRUE() {
		for (int linha = 0; linha < numLinhas; linha++)
			for (int coluna = 0; coluna < numColunas; coluna++)
				matriz_disponibilidade[linha][coluna] = true;
	}

	// --------------------------------------------------------------------------------------

	// Dada uma string que representa uma lista de numeros inteiros (entre
	// v�rgulas),
	// converte-la para uma lista de inteiros
	public void Extrair_lista_numeros_de_string(String string) {
		List<TipoRecurso> lista_ids_tipos = getListaTipos(); // lista de tipos
																// da sala
		int indice = 0; // salvar o indice da primeira ocorr�ncia de v�rgula na
						// string
		TipoRecurso novo_tipo = null; // salvar o n�mero obtido pela convers�o
										// de uma substring em inteiro

		while (indice >= 0) {
			indice = string.indexOf(",");

			if (indice >= 0) {
				String numero_string = string.substring(0, indice); // primeiro
																	// numero da
																	// string
				novo_tipo = new TipoRecurso(numero_string);

				// string come�ar� a partir do proximo numero ap�s a v�rgula e
				// um espa�o
				string = string.substring(indice + 2);
			} else {
				// nao achou virgula, string cont�m o �ltimo n�mero
				novo_tipo = new TipoRecurso(string);
			}

			lista_ids_tipos.add(novo_tipo);
		}
	}

	// ******************************************************************

	// Retorna a ID do pr�dio da sala na LISTA_PREDIOS do Banco de Dados
	public String Extrair_ID_Predio(List<Predio> LISTA_PREDIOS) {
		int indice_predio = this.getIndicePredio();
		Predio predio = LISTA_PREDIOS.get(indice_predio);
		return predio.ID_toString();
	}

	// ****************************************************************************************

}
