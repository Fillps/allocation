package tcp_trb_final.new_allocator.serviços;

import tcp_trb_final.new_allocator.dominio.Aula;
import tcp_trb_final.new_allocator.dominio.Colisao;
import tcp_trb_final.new_allocator.dominio.Turma;

import java.util.List;

public interface Turma_Servico_Interface {

	// getters
	public Turma getTurmaServico();
	public List<Colisao> getListaColisoes();		
	public List<Turma> getListaTurmasComColisao();		
	public List<Aula> getAulasNaoColidentes();
	
	//public static Comparator<Turma> TurmaNumAlunosComparator = new Comparator<Turma>()
	
	public void Preencher_LISTA_TURMAS();
	
	public int Contar_Aulas_Nao_Alocadas_da_Turma();
	
	// ver se alguma turma de algum professor da turma corrente tem colis�o com esta (this). 
	// Se sim, adicionar as turmas que colidem na  lista_turmas_com_colisao
	public void Ver_Colisao_com_Lista_Turmas_Professor(Turma turma);
	
	
	// procurar turmas de um professor cujo hor�rio de aula colida com o
	//  'horario_procurado' da aula que est� sendo alocada
	public void Preencher_Lista_Colisao_Turma(Turma turma, List<Turma> lista_turmas_prof);
	
	/* Ver se duas turmas de mesma disciplina e professor(es) colidem de horario
	 * Exemplo: Se as turmas A e B colidem em duas aulas, mas n�o na 3�, a lista_colisoes da turma A ser�:
	 * { {2, 10:30, (A,B) }, colis�o
	 *   {4, 10:30, (A,B) }, colis�o 
	 *  } 
	 *   Essa forma ser� �til no momento da aloca��o.
	 */
	public boolean Colide_com_a_turma(Turma turma, Turma turma2);
	
	
	public void Preencher_Lista_Aulas_Nao_Colidentes();  // da turma corrente;
	
}
