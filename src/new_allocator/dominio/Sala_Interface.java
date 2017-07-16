package new_allocator.dominio;

import java.util.List;

public interface Sala_Interface {
	
	// getters
	public String getID();
	public String ID_toString(); 	// se id n�o for String,
	public List<TipoRecurso> getListaTipos();
	public int getNumLugares();
	public boolean getDisponibilidade();
	public String getObs();
	public int getIndicePredio();
	public boolean[][] getMatriz_disponibilidade();
	public int getNumLinhas();
	public int getNumColunas();
	public String[][] getMatriz_Turmas();
	
	// setters: setar dados da sala a partir da leitura do arquivo de entrada
	public void set_Disponibilidade(String available);
	public void setID(String id_sala);
	public void setNumLugares(String num_places);
	public void setObservacao(String observacaoSala);
	public void setListaTipos(String feature_ids);
		
	// outros setters
	public void Atualiza_Disponibilidade(boolean nova_disponibilidade);
		
	// usado no momento de criar a LISTA_SALAS do Banco de Dados
	public void setDadosSalaDisponivel(int indicePredio, int numHorarios, int numDias);
	
	public void Preenche_Matriz_Disponibilidade_com_TRUE();
	
	// Dada uma string que representa uma lista de numeros inteiros (entre v�rgulas), 
	// converte-la para uma lista de inteiros
	public void Extrair_lista_numeros_de_string(String string);
	
	// Retorna a ID do pr�dio da sala na LISTA_PREDIOS do Banco de Dados
	public String Extrair_ID_Predio(List<Predio> LISTA_PREDIOS);
	
}
