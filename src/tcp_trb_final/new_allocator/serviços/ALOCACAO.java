package tcp_trb_final.new_allocator.serviços;

import tcp_trb_final.new_allocator.dominio.*;
import tcp_trb_final.new_allocator.dominio.BANCO_DADOS;

import java.util.ArrayList;
import java.util.List;

//import Impressoes.IMPRESSOES;


public class ALOCACAO implements ALOCACAO_Interface{

	private BANCO_DADOS BancoDados;

	public ALOCACAO(BANCO_DADOS BancoDados) {
		this.BancoDados = BancoDados;
	}
	
	//****************************************************************************
	public void Alocar_lista_turmas(boolean alocar_aula_regular)
	{	
		List<Turma> LISTA_TURMAS = BancoDados.getListaTurmas();
		int numTurmas = LISTA_TURMAS.size();
		
		// percorre turmas da LISTA_TURMAS
		for (int indiceTurma = 0; indiceTurma < numTurmas; indiceTurma++)
		{
			Turma turma_corrente = LISTA_TURMAS.get(indiceTurma); // i-�sima turma da LISTA_TURMAS
			
			// se a turma n�o est� alocada (pode ter sido alocada por colidir com outra alocada anteriormente)
			// 1� verifica a flag 'estah_alocada' da turma: se for false, ver se todas as alocacoes das aulas s�o false
			if (turma_corrente.getEstahAlocada() == false && turma_corrente.getEstahAlocada_verAulas() == false)
			{
				Turma_servico turma_servico = new Turma_servico(turma_corrente, BancoDados);
				
				int numAulasTurma = turma_corrente.getNumAulas();
				int numAulasAlocadasTurma = turma_servico.Contar_Aulas_Nao_Alocadas_da_Turma();

				/*
				 * Se 0 < numAulasAlocadasTurma < numAulasTurma, as aulas j� alocadas da turma corrente o foram 
				 * devido colis�o com outra turma anterior. Ent�o, essas aulas n�o foram alocadas pois
				 * n�o colidiram com nenhuma outra aula do(s) prof(s). Logo, basta alocar a lista de aulas restantes.
				 */
				if (0 < numAulasAlocadasTurma && numAulasAlocadasTurma < numAulasTurma)
				{
					//System.out.printf("num aulas alocadas = %d\n", numAulasAlocadasTurma);
					List<Aula> lista_aulas_turma = turma_corrente.getListaAulas();					
					
					AbstractAula_Servico AbstAula_Servico = new AbstractAula_Servico(alocar_aula_regular, BancoDados,"Aula");
					
					// converte lista de aulas da turma corrente para lista de AbstractAula
					List<AbstractAula> lista_convertida = AbstAula_Servico.Converter_Lista_para_ListAbstractAula(lista_aulas_turma);
					
					// aloca aulas ainda n�o alocadas da lista
					AbstAula_Servico.Alocar_Aulas(lista_convertida);
				}
				else // num Aulas Alocadas == 0
				{
					// ver se alguma turma de algum professor da turma corrente tem aula nesse hor�rio
					turma_servico.Ver_Colisao_com_Lista_Turmas_Professor(turma_corrente);

					List<Colisao> lista_colisoes = turma_servico.getListaColisoes();
					int numColisoes = lista_colisoes.size();
				
					/*
					 * Se tem colis�o, a lista_colisoes tem as aulas da turma
					 * corrente que colidem e n�o colidem (ver exemplo que
					 * antecede a implementa��o da fun��o Colide_com_a_turma)
					 */
					if (numColisoes == 0) // se a lista_turmas_com_colisao for vazia, alocar as aulas da turma corrente
					{
						Alocar_Turma_Sem_Colisao(alocar_aula_regular, turma_corrente);
					}
					else // se a lista_colisoes n�o � vazia, ...
					{
						Alocar_Turma_Com_Colisao(alocar_aula_regular, turma_servico, lista_colisoes);
					}
				}
				
				// ver se todas as aulas da turma corrente est�o alocadas.
				// se sim, j� seta a flag 'estah_alocada' da turma para 'true'						
				turma_corrente.getEstahAlocada_verAulas();
				
			}
		}


		
		if(alocar_aula_regular == false)
			BancoDados.getListaEspera().clear();
	}
	
	// ----------------------------------------------------------------------------------------------
	
	public void Alocar_Turma_Sem_Colisao(boolean alocar_aula_regular, Turma turma_corrente)
	{
		//System.out.println("sem colisoes\n");
		List<Aula> lista_aulas = turma_corrente.getListaAulas();

		Aula_Servico aula_qualquer = new Aula_Servico(); // s� para chamar o m�todo a seguir
		List<TipoRecurso> lista_tipos = aula_qualquer.Contar_Tipos_Aula(lista_aulas);
		int numTipos = lista_tipos.size();
		
		AbstractAula_Servico abstAula_Servico = new AbstractAula_Servico(alocar_aula_regular, BancoDados, "Aula");

		if (numTipos == 1)
			abstAula_Servico.Alocar_Aulas(lista_aulas);
		else
		{
			List<List<Aula>> lista_de_listas_aula = aula_qualquer.Separar_Aulas_Turma_por_Tipo(lista_aulas, lista_tipos);

			// A cada itera��o, aloca uma sublista das aulas da turma (as aulas do mesmo tipo)
			for (int indiceTipo = 0; indiceTipo < numTipos; indiceTipo++)
			{
				List<Aula> sublista_aulas = lista_de_listas_aula.get(indiceTipo);
				abstAula_Servico.Alocar_Aulas(sublista_aulas);
			}
		}
	}

	// ----------------------------------------------------------------------------------------------
	
	public void Alocar_Turma_Com_Colisao(boolean alocar_aula_regular, Turma_servico turma_servico, List<Colisao> lista_colisoes)
	{
		List<BigAula> lista_big_aulas = new ArrayList<BigAula>();
		
		BigAula_Servico big_qualquer = new BigAula_Servico(BancoDados);		// s� para chamar o m�todo a seguir
		big_qualquer.Preencher_Lista_Big_Aulas(lista_big_aulas, lista_colisoes);
		
		// alocar as BIG aulas
		int numBigAulas = lista_big_aulas.size();
		int numBigAulasAlocadas = 0;
				
		AbstractAula_Servico aula_Servico = new AbstractAula_Servico(true, BancoDados, "BigAula");

		// A cada itera��o, aloca uma parte das big-aulas da turma (as aulas do mesmo tipo)
		while (numBigAulasAlocadas < numBigAulas)
		{
			// numero de aulas alocadas nessa itera��o
			int numAlocacoes = aula_Servico.Alocar_Aulas(lista_big_aulas);

			if (numAlocacoes == 0)
				break;
			else
				numBigAulasAlocadas += numAlocacoes;
		}

		// se existir, alocar as aulas nao colidentes
		List<Aula> aulas_nao_colidentes = turma_servico.getAulasNaoColidentes();
		

		if (aulas_nao_colidentes.size() > 0)
		{
			int numAulasTotal = aulas_nao_colidentes.size();
			int numAulasAlocadas = 0;

			// A cada itera��o, aloca uma parte das aulas da turma (as aulas do mesmo tipo)
			while (numAulasAlocadas < numAulasTotal)
			{
				AbstractAula_Servico aula_Servico2 = new AbstractAula_Servico(alocar_aula_regular, BancoDados, "Aula");
				numAulasAlocadas += aula_Servico2.Alocar_Aulas(aulas_nao_colidentes);
				// retorna o numero de aulas alocadas nessa itera��o
			}
		}
		
	}

}
