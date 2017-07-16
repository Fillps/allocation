package tcp_trb_final.new_allocator.serviços;

import tcp_trb_final.new_allocator.dominio.*;
import tcp_trb_final.new_allocator.dominio.BANCO_DADOS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Turma_servico implements Turma_Servico_Interface
{
	public BANCO_DADOS BancoDados;
	private Turma turma_corrente;					// turma que est� sendo alocada
	private List<Turma> lista_turmas_com_colisao;	// lista de turma que colidem com a turma corrente
	public List<Colisao> lista_colisoes;			// lista de colis�es com as aulas da turma corrente
	
	// lista de aulas da turma corrente que n�o colidem com aulas de outras turmas da lista_turmas_com_colisao
	// ou seja, apenas algumas aulas das turmas da lista colidem
	public List<Aula> lista_aulas_nao_colidem;
	
	//*********************************************************************************
	
	// construtores
	public Turma_servico(BANCO_DADOS BancoDados){
		this.BancoDados = BancoDados;
	}
	
	public Turma_servico(Turma turma_corrente, BANCO_DADOS BancoDados)
	{
		this.BancoDados = BancoDados;
		this.turma_corrente = turma_corrente;
		this.lista_turmas_com_colisao = new ArrayList<Turma>();
		this.lista_colisoes = new ArrayList<Colisao>();
		this.lista_aulas_nao_colidem = new ArrayList<Aula>();
	}

	//*********************************************************************************
	// getters
	public Turma getTurmaServico(){ 
		return turma_corrente;
	}
	
	public List<Colisao> getListaColisoes(){
		return lista_colisoes;
	}
	
	public List<Turma> getListaTurmasComColisao(){ 
		return lista_turmas_com_colisao;
	}
	
	public List<Aula> getAulasNaoColidentes(){
		return lista_aulas_nao_colidem;
	}


	//*********************************************************************************
	// IMPORTACAO
	
	// Comparador para poder ordenar lista de turmas por numero de alunos
	public static Comparator<Turma> TurmaNumAlunosComparator = new Comparator<Turma>(){
		@Override
		public int compare(Turma turma1, Turma turma2) {
			int numAlunos1 = turma1.getNumAlunos();
			int numAlunos2 = turma2.getNumAlunos();
		    	
		       return (numAlunos2 - numAlunos1); // ordem decrescente
		}
	};
	
	// *****************************************************************************

	// Preencher LISTA_TURMAS do BancoDados, varrendo a sua LISTA_DISCIPLINAS 
	public void Preencher_LISTA_TURMAS()
	{
		List<Turma> LISTA_TURMAS = BancoDados.getListaTurmas();
		
		List<Disciplina> LISTA_DISCIPLINAS = BancoDados.getListaDisciplinas();
		int numDisciplinas = LISTA_DISCIPLINAS.size();
				
		// percorrer disciplinas
		for(int indiceDisc = 0; indiceDisc < numDisciplinas; indiceDisc++)
		{
			Disciplina disciplina = LISTA_DISCIPLINAS.get(indiceDisc);		// i-�sima disciplina
			
			List<Turma> Lista_Turmas_Disc = disciplina.get_Lista_Turmas(); // lista de turmas da disciplina corrente
			int numTurmas = Lista_Turmas_Disc.size();
			
			// percorre turmas da disciplina
			for(int indiceTurma = 0; indiceTurma < numTurmas; indiceTurma++)
			{
				Turma turma = Lista_Turmas_Disc.get(indiceTurma);	// n-�sima turma da disciplina corrente
				LISTA_TURMAS.add(turma);
			}
		}
		
		// ordenar a LISTA_TURMAS de forma decrescente pelo numero de alunos (para alocar turmas maiores primeiro)
		Collections.sort(LISTA_TURMAS, TurmaNumAlunosComparator);
	}
	

	//-------------------------------------------------------------------------------------------

	public int Contar_Aulas_Nao_Alocadas_da_Turma()
	{
		int contador = 0;
		List<Aula> lista_aulas = turma_corrente.getListaAulas();	// lista de aulas da turma corrente
		int numAulas = turma_corrente.getNumAulas();				// numero de aulas da turma corrente
		
		// percorrer as aulas da turma
		for(int indice=0; indice < numAulas; indice++)
		{
			Aula aula = lista_aulas.get(indice);			// i-�sima aula da turma corrente
			boolean alocada = aula.getEstahAlocada();			// valor de aloca��o da aula i
			
			// se a i-�sima aula n�o est� alocada, ent�o a turma como um todo n�o est� alocada
			if(alocada == true){
				contador++;
			}
		}
		
		return contador;
	}

	// ****************************************************************************************
	// ALOCA��O
	
	// colis�o de hor�rio entre turmas de mesma disciplina e professor

	// ver se alguma turma de algum professor da turma corrente tem colis�o com esta (this). 
	// Se sim, adicionar as turmas que colidem na  lista_turmas_com_colisao
	public void Ver_Colisao_com_Lista_Turmas_Professor(Turma turma)
	{
		List<Professor> LISTA_PROFESSORES = BancoDados.getListaProfs();
		List<Integer> lista_ids_profs = turma.getIndicesProfessores(); 	// lista dos IDs dos professores da turma (this)
		int numProfsTurma = lista_ids_profs.size(); 				// n�mero de professores da turma

		// percorre professores da turma
		for (int indiceProf = 0; indiceProf < numProfsTurma; indiceProf++)
		{
			int id_prof = lista_ids_profs.get(indiceProf); 		// i-�sima ID de professor da turma corrente

			Professor professor = LISTA_PROFESSORES.get(id_prof); 	// professor correspondente � ID i

			List<Turma> lista_turmas_prof = professor.getTurmas(); 	// lista de turmas do professor

			/* ver se a turma procurada colide com as turmas do professor i.
			 * Se sim, colocar as turmas do professor que colidirem na lista_turmas_com_colisao */
			Preencher_Lista_Colisao_Turma(turma, lista_turmas_prof);
			
			if(lista_colisoes.size() > 0)
			{
				// depois de preenchida, completar: preencher lista de aulas colidentes de cada colisao da lista_colisoes
				Colisao_Servico colisao_qualquer = new Colisao_Servico(); // s� para chamar o m�todo a seguir
				colisao_qualquer.Completar_Lista_Colisoes(lista_colisoes, lista_turmas_com_colisao);
				
				Preencher_Lista_Aulas_Nao_Colidentes();
			}
		}
	}
	
	//-------------------------------------------------------------------------------------------

	// procurar turmas de um professor cujo hor�rio de aula colida com o
	//  'horario_procurado' da aula que est� sendo alocada
	public void Preencher_Lista_Colisao_Turma(Turma turma, List<Turma> lista_turmas_prof)
	{
		int numTurmas = lista_turmas_prof.size(); 			// n�mero de turmas do professor

		// percorre todas as turmas do professor
		for (int indiceTurma = 0; indiceTurma < numTurmas; indiceTurma++)
		{
			Turma turma_i = lista_turmas_prof.get(indiceTurma); 		// i-�sima turma do professor
		
			if (turma_i.equals(turma) == false) // se n�o for a turma procurada ...
			{
				// ... e se houver colis�o entre a turma procurada e a turma i ...
				if (Colide_com_a_turma(turma, turma_i) == true)
					if (lista_turmas_com_colisao.contains(turma_i) == false)
					{
						lista_turmas_com_colisao.add(turma_i); // ... adiciona a  turma i na lista_turmas_com_colisao
						
						// adicionar a turma corrente na lista de turmas que colidem com ela
						// se a turma tiver mais de um prof, evitar nova inclus�o da turma corrente
						if(lista_turmas_com_colisao.contains(turma) == false)
							lista_turmas_com_colisao.add(turma);
					}
			}
		}

	}

	//-------------------------------------------------------------------------------------------

	/* Ver se duas turmas de mesma disciplina e professor(es) colidem de horario
	 * Exemplo: Se as turmas A e B colidem em duas aulas, mas n�o na 3�, a lista_colisoes da turma A ser�:
	 * { {2, 10:30, (A,B) }, colis�o
	 *   {4, 10:30, (A,B) }, colis�o 
	 *  } 
	 *   Essa forma ser� �til no momento da aloca��o.
	 */
	public boolean Colide_com_a_turma(Turma turma, Turma turma2)
	{
		boolean colide = false; // sa�da: em princ�pio n�o colide

		List<Aula> lista_aulas = turma.getListaAulas(); // lista de aulas da turma this
		int numAulas = lista_aulas.size();

		List<Aula> lista_aulas2 = turma2.getListaAulas(); // lista de aulas da turma2
		int numAulas2 = lista_aulas2.size();

		// percorre aulas da turma this
		for (int indiceAula = 0; indiceAula < numAulas; indiceAula++)
		{
			Aula aula = lista_aulas.get(indiceAula); // aula = i-�sima aula da turma this
			Dia dia = aula.getDia(); // dia da semana da aula i
			Horario horario = aula.getHorario(); // horario = hor�rio de in�cio da aula i

			// percorre aulas da turma2
			for (int indiceAula2 = 0; indiceAula2 < numAulas2; indiceAula2++)
			{
				Aula aula2 = lista_aulas2.get(indiceAula2); // aula = j-�sima aula da turma this
				Dia dia2 = aula2.getDia(); 					// dia da semana da aula j
				Horario horario2 = aula2.getHorario(); 		// horario2 = hor�rio de in�cio da aula j
				
				boolean dias_iguais = dia.equals(dia2);
				boolean horarios_iguais = horario.equals(horario2);

				if (dias_iguais == true && horarios_iguais == true) // se os horarios e dias das Aulas 'aula' e 'aula2' forem iguais ...
				{
					colide = true; // setar sa�da para true
					
					// criar nova colis�o
					Colisao colisao = new Colisao(aula);
					colisao.Incluir_Lista_Colisoes(lista_colisoes); // lista de colisoes da turma corrente
				}
			}
			
		}
		
		return colide;
	}
	
	//-------------------------------------------------------------------------------------------
	
	public void Preencher_Lista_Aulas_Nao_Colidentes() // da turma corrente
	{
		//List<Aula> lista_aulas_colidentes = lista_colisoes.ge
		List<Aula> lista_aulas = turma_corrente.getListaAulas();
		int numAulas = turma_corrente.getNumAulas();
		
		// percorre lista de aula da turma corrente
		for(int indice=0; indice < numAulas; indice++)
		{
			Aula aula_procurada = lista_aulas.get(indice);	// i-�sima aula
			
			Colisao_Servico colisao_qualquer = new Colisao_Servico(); // s� para chamar o m�todo a seguir
			int busca = colisao_qualquer.Busca_Aula_numa_Lista_Colisoes(aula_procurada, lista_colisoes);
			
			// se a aula i nao est� em nenhuma lista de aulas das listas de colisoes ...
			if (busca == -1)
			{
				// ... adicionar na lista de aulas da turma corrennte que nao colidem
				// caso ja n�o tenha sido inclu�da (por ter mais de um professor)
				if(lista_aulas_nao_colidem.contains(aula_procurada) == false)
					lista_aulas_nao_colidem.add(aula_procurada);			
			}			
		}
	}

	// ****************************************************************************
	

}
