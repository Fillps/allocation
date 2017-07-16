package new_allocator.serviços;

import new_allocator.dominio.*;
import new_allocator.dominio.BANCO_DADOS;

import java.util.ArrayList;
import java.util.List;

public class AbstractAula_Servico implements AbstractAula_Serv_Interface {

	private BANCO_DADOS BancoDados;
	boolean alocar_aula_regular;
	String tipo_aula;		// para teste: preencher matriz do sala como codigo e a id da turma => arquivo .csv 
	
	// construtor
	public AbstractAula_Servico(boolean alocar_aula_regular, BANCO_DADOS BancoDados, String tipo_aula)
	{
		this.BancoDados = BancoDados;
		this.alocar_aula_regular = alocar_aula_regular;
		
		// S� instanciar se tipo_aula for "Aula" ou "BigAula"
		try{
			if(tipo_aula.compareTo("Aula") != 0 && tipo_aula.compareTo("BigAula") != 0)
				throw new Exception("tipo de aula incorreto");
			
		} catch (Exception e) {
			System.out.printf("ERRO em instanciar AbstractAula: a entrada tipo_aula '%s' informada esta incorreta\n", tipo_aula);
			//System.exit(0);
		}
		finally {
			this.tipo_aula = tipo_aula;
		}
	}
	
	//*********************************************************************************************************
	// Usado nos m�todos de aloca��o para converter uma List<Aula> ou List<BigAula> em List<AbstractAula>
	public List<AbstractAula> Converter_Lista_para_ListAbstractAula(List<?> lista_aulas)
	{
		List<AbstractAula> lista_convertida = new ArrayList<AbstractAula>();
		
		int numElementos = lista_aulas.size();
		
		for(int indice=0; indice < numElementos; indice++){
			lista_convertida.add( (AbstractAula)lista_aulas.get(indice));
		}
		
		return lista_convertida;
	}
	
	
	/* Alocar as aulas de uma turma (sem colisao) que sejam do mesmo tipo, isto �, requerem o mesmo tipo de recurso em uma sala.
	*	Partindo da primeira aula da lista de aulas que n�o est� alocada, buscar no restante da lista as aulas que
	*	sejam do seu tipo. Ap�s alocar essas aulas num mesmo sala, retornar o n�mero de aulas do mesmo tipo.
	* Obs: o objeto 'alocacao' tem as LISTAS do BANCO DADOS necess�rias para efetuar a aloca��o das aulas
	*/
	public int Alocar_Aulas(List<?> lista_aulas)
	{
		List<AbstractAula> lista_aulas_convertida = Converter_Lista_para_ListAbstractAula(lista_aulas);
		
		//List<Aula> lista_aulas = turma_corrente.getListaAulas();
		int numAulas = lista_aulas.size();

		List<AbstractAula> aulas_mesmo_tipo = new ArrayList<AbstractAula>(); // lista vazia para salvar as aulas de mesmo tipo
		
		int indice = 0;
		int indiceInicial = 0;				// indice da primeira aula n�o alocada da lista
		boolean estah_alocada = true;		// sup�e que esta 1� aula est� alocada
		
		// busca a primeira aula n�o alocada da lista
		while(estah_alocada == true && indice < numAulas) 
		{
			AbstractAula aula_i = lista_aulas_convertida.get(indice);			// i-�sima aula da lista
			estah_alocada = aula_i.getEstahAlocada();			// valor da flag 'alocada' da aula i
			
			if(estah_alocada == false)			// se a aula i N�O est� alocada, ...
				indiceInicial = indice;			// ... vai para a pr�xima aula

			indice++;
		}

		
		if (estah_alocada == false) // se for true, todas as aulas estarao alocadas
		{
			// Encontramos o ponto de partida: a primeira aula n�o alocada da turma
			AbstractAula aula0 = lista_aulas_convertida.get(indiceInicial); 	// primeira aula n�o alocada
			aulas_mesmo_tipo.add(aula0); 					// adiciona essa primeira aula na lista de aulas_mesmo_tipo
			TipoRecurso tipo_procurado = aula0.getTipo(); 			// tipo procurado = tipo da aula0

			// Buscar aulas do mesmo tipo da aula0
			for (indice = 1; indice < numAulas; indice++)
			{
				AbstractAula aula_i = lista_aulas_convertida.get(indice);		// i-�sima aula da lista
				TipoRecurso tipo_aula_i = aula_i.getTipo();			// tipo de recurso requerido pela aula i

				if (tipo_aula_i.equals(tipo_procurado) == true) {		// se a aula i � do mesmo tipo da aula0 ...
					aulas_mesmo_tipo.add(aula_i);			// ... adiciona a aula i na lista de aulas do mesmo tipo
					//Aula.Imprimir_Lista(aulas_mesmo_tipo, "aulas do mesmo tipo");
				}
			}

			// Aloca aulas da lista aulas_mesmo_tipo: encontrar um sala livre em todos os dias/horarios dessas aulas
			Alocar_aulas_mesmo_tipo(aulas_mesmo_tipo, tipo_procurado);
		}
		
		return aulas_mesmo_tipo.size(); // retornar o n�mero de aulas do mesmo tipo que foram alocadas
	}
		
	//**************************************************************************************************************
	
	public void Alocar_aulas_mesmo_tipo(List<AbstractAula> lista_aulas_mesmo_tipo, TipoRecurso tipo_recurso)
	{			
		List<Dia> lista_dias = new ArrayList<Dia>();
		List<Horario> lista_horarios = new ArrayList<Horario>();
		int totalAlunos = 0;
		int duracao = 0;
		
		int numAulas = lista_aulas_mesmo_tipo.size();
		
		for(int indice=0; indice < numAulas; indice++)
		{
			AbstractAula aula = lista_aulas_mesmo_tipo.get(indice);
			
			Dia dia = aula.getDia();
			Horario horario = aula.getHorario();
			int numAlunos = aula.getNumAlunos();
			duracao = aula.getDuracao();
			
			lista_dias.add(dia);
			lista_horarios.add(horario);
			
			if(numAlunos > totalAlunos) // o totalAlunos ter� a maior quantidade de alunos dentre as aulas da lista
				totalAlunos = numAlunos;
		}
						
		// Encontrar sala para as aulas
		Sala_servico sala_servico = new Sala_servico(BancoDados); // para chamar o m�todo Encontrar_Sala
	
		Sala sala = sala_servico.Encontrar_Sala(alocar_aula_regular, tipo_recurso, totalAlunos, duracao, lista_dias, lista_horarios);
				
		if(sala != null)
		{		
			String codTurmaReserva = ""; // para teste: preencher matriz do sala como codigo e a id da turma
			
				// incluir sala em cada uma das aulas, marcando cada aula como alocada
				for(int indice=0; indice < numAulas; indice++)
				{
					AbstractAula aula = lista_aulas_mesmo_tipo.get(indice);		// i-esima aula da lista de aulas do mesmo tipo
					aula.setSala(sala);								// incluir o sala da aula i
					aula.setAlocada();									// setar para true a flag 'alocada' da aula i
				
					// para teste: preencher matriz do sala como codigo e a id da turma
					if(tipo_aula.compareTo("Aula") == 0)
					{
						Aula aulacast = ((Aula)aula);
						int id_disc = aulacast.getID_Disciplina(); // id da discipina da aula
						String codigo = BancoDados.getListaDisciplinas().get(id_disc).getCodigo(); // codigo da disciplina
						codTurmaReserva = codigo + "-" + aulacast.getIDTurma() + "-" + aulacast.getNumAlunos();
					}
					else{
						
						BigAula_Servico big_qualquer = new BigAula_Servico(BancoDados);		// s� para chamar o m�todo a seguir
						codTurmaReserva = big_qualquer.getDados_ListaAulas((BigAula)aula);
					}
						
				}
				
				// teste: preencher matriz do sala como codigo e a id da turma
				sala_servico.Marcar_Sala_Ocupada_Codigos_Turmas(codTurmaReserva, sala, lista_dias, lista_horarios);
		}
		else
		{
			if(tipo_aula.compareTo("Aula") == 0){
				
				List<Turma> LISTA_ESPERA = BancoDados.getListaEspera();
				Turma turma = Busca_Turma_de_uma_Aula(lista_aulas_mesmo_tipo);
				
				boolean tem_turma = LISTA_ESPERA.contains(turma);
				
				if(tem_turma == false)
					LISTA_ESPERA.add(turma);
			}
		}		
		
	}
	
	//***************************************************************************************
	
	// Usar apenas para uma List<Aula>
	public Turma Busca_Turma_de_uma_Aula(List<AbstractAula> lista_aulas)
	{
		Aula aula = (Aula)lista_aulas.get(0);
		String codDisciplina = aula.getCodDisc();	// c�digo da Disciplina da aula
		char id_turma = aula.getIDTurma();			// id da turma da aula
		
		List<Disciplina> LISTA_DISCIPLINAS = BancoDados.getListaDisciplinas();
		
		// buscar a disciplina na LISTA_DISCIPLINAS cujo c�digo � o procurado
		int numDisciplinas = LISTA_DISCIPLINAS.size();
		Disciplina disciplina = LISTA_DISCIPLINAS.get(0);
		for(int indice=0; indice < numDisciplinas; indice++)
		{
			disciplina = LISTA_DISCIPLINAS.get(indice);
			
			if(disciplina.getCodigo() == codDisciplina)
				break;
		}
		// para a disciplina encontrada acima, buscar na sua lista de turmas a turma da aula
		List<Turma> lista_turmas_disc = disciplina.get_Lista_Turmas();
		int numTurmas = lista_turmas_disc.size();
		
		Turma turma = lista_turmas_disc.get(0);;
		for(int indice=0; indice < numTurmas; indice++)
		{
			turma = lista_turmas_disc.get(indice);
			
			if(turma.getID() == id_turma)
				break;
		}
		
		return turma;
	}
	
}
