package tcp_trb_final.new_allocator.serviços;

import tcp_trb_final.new_allocator.dominio.*;
import tcp_trb_final.new_allocator.dominio.BANCO_DADOS;

import java.util.List;

public class BigAula_Servico implements BigAula_Servico_Interface {
	
	BANCO_DADOS BancoDados;

	// construtor
	public BigAula_Servico(BANCO_DADOS BancoDados){
		this.BancoDados = BancoDados;
	}
	
	
	// ***********************************************************************************************
	// ALOCACAO
	
	// A lista_big_aulas est� inicialmente vazia
	public void Preencher_Lista_Big_Aulas(List<BigAula> lista_big_aulas, List<Colisao> lista_colisoes)
	{
		int numColisoes = lista_colisoes.size();

		for (int indice = 0; indice < numColisoes; indice++)
		{
			Colisao colisao = lista_colisoes.get(indice); // i-�sima colisao da lista de colisoes
			
			BigAula big_aula = new BigAula(colisao); // cria uma Big Aula para esta colisao i
			
			boolean tem_big_aula = lista_big_aulas.contains(big_aula);
			
			if(tem_big_aula == false){
				lista_big_aulas.add(big_aula); // adiciona a Big Aula na lista de big aulas
			}
		}
	}
	
	// TESTE: Concatenar a string "bigAula-(numAulunos)" com o c�diga da disciplina, id da turma e num de alunos de cada
	// aula da bigAula
	// usado no m�todo Alocar_aulas_mesmo_tipo da classe AbstractAula_servico
	public String getDados_ListaAulas(BigAula bigAula)
	{
		String codDisc = "bigAula-" + bigAula.getNumAlunos() + ":";
		
		List<Aula> lista_aulas = bigAula.getListaAulas();
		
		int numAulas = lista_aulas.size();
		
		for (int indice = 0; indice < numAulas; indice++)
		{
			Aula aula = lista_aulas.get(indice);
			String codigo = aula.getCodDisc();
			char id_turma = aula.getIDTurma();
			int numAlunos = aula.getNumAlunos();
			
			codDisc = codDisc + codigo + "-" + id_turma + "-" + numAlunos + " | ";
		}
		
		return codDisc;
	}
	


}
