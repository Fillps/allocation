package tcp_trb_final.new_allocator.serviços;

import tcp_trb_final.new_allocator.dominio.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sala_servico implements Sala_Servico_Interface {
	
	public BANCO_DADOS BancoDados;
	
	private TipoRecurso TIPO_SALA_REGULAR; // id de um sala de sala de aula regular (sem recurso), obtido na leitura da TAG Feature
	
	private boolean USAR_TOLERANCIA = false;
	private double TOLERANCIA = 0.05; 
	private int duracao; // duracao da aula que chamou o m�todo Encontrar_Sala
	
	//*******************************************************	
	// construtor
	public Sala_servico(BANCO_DADOS BancoDados){
		this.BancoDados = BancoDados;
		this.TIPO_SALA_REGULAR = BancoDados.get_TIPO_SALA_REGULAR();
		this.USAR_TOLERANCIA = false;
	}
	
	// construtor vazio
	public Sala_servico(){
	}

	//*******************************************************
	// Comparador para ordenar lista de salas por capacidade da sala
	public static Comparator<Sala> SalaCapacidadeComparator = new Comparator<Sala>(){
		@Override
		public int compare(Sala sala1, Sala sala2) {
			
			int capacidade1 = sala1.getNumLugares();
			int capacidade2 = sala2.getNumLugares();
	    	
	        return (capacidade1 - capacidade2);
		}
	};
	
	// Comparador para ordenar lista de salas por tipos de recursos da sala
	public static Comparator<Sala> SalaComparatorTipo = new Comparator<Sala>(){
		@Override
		public int compare(Sala sala1, Sala sala2) {
			
			int qtd_tipos1 = sala1.getListaTipos().size();
			int qtd_tipos2 = sala2.getListaTipos().size();
	    	
	        return (qtd_tipos1 - qtd_tipos2);
		}
	};

	
	// **************************************************************
	// IMPORTACAO

	// Criar Salas com base na lista de Pr�dios do Banco de Dados: uma sala para cada sala dispon�vel para uso
	public void Preencher_LISTA_SALAS(List<Predio> LISTA_PREDIOS)
	{
		List<Sala> LISTA_SALAS = BancoDados.getListaSalas();
		
		int numHorarios = BancoDados.getListaHorarios().size();
        int numDias = BancoDados.getListaDias().size();
        
		int qtePredios = LISTA_PREDIOS.size();		// quantidade total de PREDIOS
		
		// para cada pr�dio
		for(int indicePredio=0; indicePredio < qtePredios; indicePredio++)
		{
			Predio predio = LISTA_PREDIOS.get(indicePredio);		// i-�simo pr�dio
			
			List<Sala> lista_salas_predio = predio.getListaSalas();		// lista de salas do pr�dio i
			int numSalas = lista_salas_predio.size();					// n�mero de salas do pr�dio i
			
			// para cada sala do pr�dio i
			for(int indiceSala=0; indiceSala < numSalas; indiceSala++)
			{
				Sala sala = lista_salas_predio.get(indiceSala);				// j-�sima sala do pr�dio i
				boolean disponibilidade_sala = sala.getDisponibilidade();	// disponibilidade da sala j
				
				// se a sala est� dispon�vel, criar sala para esta sala
				if (disponibilidade_sala == true)
				{					
					sala.setDadosSalaDisponivel(indicePredio, numHorarios, numDias);
					LISTA_SALAS.add(sala);
				}
			}
			
		}
	}
	
	
	//***************************************************************************
	// ALOCACAO
		
	//*****************************************************
	/* Encontrar um sala na LISTA_LOCAIS que satisfa�a as entradas: 
	 * contenha o tipo de recurso procurado,
	 * livre nos dias e hor�rios desejados
	 * comporte a capacidade m�nima (n�mero de alunos)
	 */
	public List<Sala> Preencher_Lista_Salas_Possiveis(TipoRecurso tipo_recurso, int capacidadeMinima, 
												List<Dia> lista_dias, List<Horario> lista_horarios)
	{
		boolean tipo_eh_simples = tipo_recurso.Eh_Tipo_Simples(); // ve se o tipo_recurso � simples (aula n�o requer recurso)
		
		int indice_tipo = -1; // calcular o indice de um tipo_recurso de uma sala, caso tipo_recurso != 0
		
		List<Sala> LISTA_SALAS = BancoDados.getListaSalas();
		int totalSalas = LISTA_SALAS.size();
		
		List<Sala> lista_salas_possiveis = new ArrayList<Sala>();
		
		// percorrer lista de salas
		for(int indiceSala=0; indiceSala < totalSalas; indiceSala++)
		{
			Sala sala = LISTA_SALAS.get(indiceSala);
			int numLugares = sala.getNumLugares();
						
			if(USAR_TOLERANCIA == true)
				numLugares = (int)((1.0 + TOLERANCIA) * numLugares);
			
			if(numLugares >= capacidadeMinima) // sala passou pelo teste da capacidade m�nima
			{			
				// se a aula requer recurso
				if(tipo_eh_simples == false){
					
					List<TipoRecurso> lista_tipos_recursos = sala.getListaTipos();
					indice_tipo = lista_tipos_recursos.lastIndexOf(tipo_recurso);
				}
				
				// se a aula n�o requer recurso ou a sala possui recurso requerido (n�o � contr�rio do if acima)
				if (tipo_eh_simples == true || indice_tipo != -1)
				{						
					boolean disponibilidade = this.Ver_Disponibilidade_Sala(sala, lista_dias, lista_horarios);
						
					if(disponibilidade == true)		// sala est� dispon�vel em todos os dias e hor�rios desejados
					{
						lista_salas_possiveis.add(sala);
					}
				}
			}
		}
		
		return lista_salas_possiveis;
	}
	
	
	//***********************************************************************************
	
	// ver se uma sala est� dispon�vel nos dias e hor�rios desejados
	public boolean Ver_Disponibilidade_Sala(Sala sala_corrente, List<Dia> lista_dias, List<Horario> lista_horarios)
	{
		List<Dia> LISTA_DIAS = BancoDados.getListaDias();
		List<Horario> LISTA_HORARIOS = BancoDados.getListaHorarios();
		
		boolean[][] matriz_diponib = sala_corrente.getMatriz_disponibilidade();
		
		int numDisponibilidades = lista_dias.size();
		
		// percorrer cada dia e hor�rio da aula que est� sendo alocada
		for(int indice = 0; indice < numDisponibilidades ; indice++)
		{
			Dia dia = lista_dias.get(indice);
			Horario horario = lista_horarios.get(indice);
			
			int primeira_linha = LISTA_HORARIOS.indexOf(horario);
			int coluna = LISTA_DIAS.indexOf(dia);
			
			// linha da matriz de disponib correspondente ao �ltimo hor�rio necess�rio para a aula
			Horario_Servico horario_qualquer = new Horario_Servico(); // s� para chamar o m�todo a seguir
			int ultima_linha = horario_qualquer.Quantidade_Horarios_Aula(duracao, LISTA_HORARIOS, primeira_linha);
	
			// vari�vel para acumular a disponibilidade da sala_corrente para o dia e hor�rios desejados
			boolean disponibilidade_total = true;
			
			// avalia o valor da matriz de disponibilidade em cada hor�rio (linha), a partir do inicial, do dia solicitado
			for(int linha=primeira_linha; linha <= ultima_linha; linha++) {
				disponibilidade_total = disponibilidade_total && matriz_diponib[linha][coluna];
			}

			if(disponibilidade_total == false)
				return false;
		}
		
		return true;	// se n�o achou false na matriz, � porque todas as c�ulas cont�m true!		
	}
	
	
	//***********************************************************************************
	
	// Se uma aula n�o requer sala com algum recurso, filtrar a lista de salas poss�veis para ela,
	// ou seja, tomar apenas os salas que contem recurso do TIPO_SALA_REGULAR
	public List<Sala> Filtrar_Lista_Salas_Aula_Regular(List<Sala> lista_salas_possiveis)
	{
		List<Sala> lista_salas_regulares = new ArrayList<Sala>();
		
		int numSalas = lista_salas_possiveis.size();
		
		for(int indice=0; indice < numSalas; indice++)
		{
			Sala sala = lista_salas_possiveis.get(indice);
			List<TipoRecurso> lista_tipos_recursos_sala = sala.getListaTipos();
			
			if(lista_tipos_recursos_sala.contains(TIPO_SALA_REGULAR))
				lista_salas_regulares.add(sala);
		}
		
		// se a lista_salas_regulares tiver mais de 1 elemento, rdeanr lista pela quantidade de tipos de recursos
		if (lista_salas_regulares.size() > 1) {
			Collections.sort(lista_salas_regulares, SalaComparatorTipo);
		}
		
		return lista_salas_regulares;
	}
		
	
	//***********************************************************************************
	// marcar o sala corrente como ocupado: colocar false na disponibilidade do sala em todos os hor�rios e dias das aulas alocadas
	public void Marcar_Sala_Ocupada(Sala sala_corrente, List<Dia> lista_dias, List<Horario> lista_horarios)
	{
		List<Dia> LISTA_DIAS = BancoDados.getListaDias();
		List<Horario> LISTA_HORARIOS = BancoDados.getListaHorarios();
				
		boolean[][] matriz_diponib = sala_corrente.getMatriz_disponibilidade();
	
		int numDisponibilidades = lista_dias.size();
		
		for(int indice = 0; indice < numDisponibilidades ; indice++)
		{
			Dia dia = lista_dias.get(indice);
			Horario horario = lista_horarios.get(indice);
			
			int primeira_linha = LISTA_HORARIOS.indexOf(horario);
			int coluna = LISTA_DIAS.indexOf(dia);
			
			Horario_Servico horario_qualquer = new Horario_Servico(); // s� para chamar o m�todo a seguir
			int ultima_linha = horario_qualquer.Quantidade_Horarios_Aula(duracao, LISTA_HORARIOS, primeira_linha);
		
			// marca cada hor�rio (linha) da matriz de disponibilidade como false
			for(int linha=primeira_linha; linha <= ultima_linha; linha++)
			{
				matriz_diponib[linha][coluna] = false;
			}

		}
	}
	
	// para teste: preenche a matriz de sstring com as turmas (codido+id) das turmas que ocupara o sala corrente
	public void Marcar_Sala_Ocupada_Codigos_Turmas(String turmas_reserva, Sala sala_corrente, 
													List<Dia> lista_dias, List<Horario> lista_horarios)
	{
		List<Dia> LISTA_DIAS = BancoDados.getListaDias();
		List<Horario> LISTA_HORARIOS = BancoDados.getListaHorarios();
		
		String[][] matriz_turmas = sala_corrente.getMatriz_Turmas();
	
		int numDisponibilidades = lista_dias.size();
		
		for(int indice = 0; indice < numDisponibilidades ; indice++)
		{
			Dia dia = lista_dias.get(indice);
			Horario horario = lista_horarios.get(indice);
			
			int primeira_linha = LISTA_HORARIOS.indexOf(horario);
			int coluna = LISTA_DIAS.indexOf(dia);
		
			Horario_Servico horario_qualquer = new Horario_Servico(); // s� para chamar o m�todo a seguir
			int ultima_linha = horario_qualquer.Quantidade_Horarios_Aula(duracao, LISTA_HORARIOS, primeira_linha);
		
			// marca cada hor�rio (linha) da matriz de disponibilidade com a String de reserva (com o c�digo da aula ou 'bigAula')
			for(int linha=primeira_linha; linha <= ultima_linha; linha++) {
				matriz_turmas[linha][coluna] = turmas_reserva;
			}
				
		}
	}
	
	
	//***************************************************************************************
	
	// M�todo principal
	
	// Encontrar sala para aulas de acordo com o tipo_recurso que requerem, sendo que 
	// tipo_recurso = 0 significa que a aula n�o requer recurso
	public Sala Encontrar_Sala(boolean alocar_aula_regular, TipoRecurso tipo_recurso, int capacidadeMinima, int duracao, List<Dia> lista_dias, List<Horario> lista_horarios)
	{
		USAR_TOLERANCIA = false;
		this.duracao = duracao;
		
		// Criar lista de salas poss�veis conforme os pr�-requisitos da entrada
		List<Sala> lista_salas_possiveis = Preencher_Lista_Salas_Possiveis(tipo_recurso, capacidadeMinima, lista_dias, lista_horarios);
		
		// se n�o encontrou sala poss�vel, usar tolerancia: supor que o numLugares de cada sala aumenta em TOLERANCIA %
		// e tentar encontrar sala poss�vel novamente
		if(lista_salas_possiveis.size() == 0)	{
			USAR_TOLERANCIA = true;
			lista_salas_possiveis = Preencher_Lista_Salas_Possiveis(tipo_recurso, capacidadeMinima, lista_dias, lista_horarios);
		}
		
		if(lista_salas_possiveis.size() == 0) {
			return null; 
		}
		else // se existe pelo menos um sala poss�vel
		{
			// ordenar lista de salas poss�veis em ordem crescente de capacidade
			Collections.sort(lista_salas_possiveis, SalaCapacidadeComparator);

			Sala primeiro_possivel = lista_salas_possiveis.get(0);
				
			Sala sala_selecionado; // ponteiro para o sala a ser selecionado
			boolean tipo_eh_simples = tipo_recurso.Eh_Tipo_Simples();
			
			if (tipo_eh_simples == false)	// se existe requerimento de recurso
				sala_selecionado = primeiro_possivel; // selecionar o de menor tamanho	
			else
			{
				// filtrar a lista de salas poss�veis, escolhendo apenas salas do tipo "sala de aula" regular 
				List<Sala> lista_salas_regulares = Filtrar_Lista_Salas_Aula_Regular(lista_salas_possiveis);
								
				if(lista_salas_regulares.size() == 0)
					sala_selecionado = primeiro_possivel;
				else
				{
					Sala primeiro_regular = lista_salas_regulares.get(0);

					if(alocar_aula_regular == true){
						sala_selecionado = primeiro_regular;
					}
					else
						return null; // vai colocar a turma na LISTA_ESPERA
				}
			}
	        
			// sinalizar sala_selecionado como ocupada (disponivel = false) em todos os dias a hor�rios solicitados
	        this.Marcar_Sala_Ocupada(sala_selecionado, lista_dias, lista_horarios);
	        	
	        return sala_selecionado;
		}		
	}
	
	
	
}
