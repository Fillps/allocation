package tcp_trb_final.new_allocator.serviços;

import tcp_trb_final.new_allocator.dominio.*;

import java.util.List;

public class Colisao_Servico implements Colisao_Servico_Interface {
	
	// construtor
	public Colisao_Servico(){
		
	}
		
	//****************************************************************
	// ALOCACAO
	
	// BUSCAS ESPEC�FICAS EM UMA COLIS�O
	
	// Busca a aula_procurada na colisao, retornando seu indice na lista ou -1 se n�o estiver na lista
	public int Busca_Aula_numa_Colisao(Aula aula_procurada, Colisao colisao)
	{
		List<Aula> lista_aulas_colidem = colisao.getAulasColidem();	// lista de aulas da colisao (this)
		
		// retorna o resultado da busca da aula_procurada na lista de aulas da colis�o
		return lista_aulas_colidem.indexOf(aula_procurada);
	}
	
	
	public int Busca_Aula_numa_Lista_Colisoes(Aula aula_procurada, List<Colisao> lista_colisoes)
	{
		int numColisoes = lista_colisoes.size();
			
		for(int indice=0; indice < numColisoes; indice++)
		{
			Colisao colisao_i = lista_colisoes.get(indice);
				
			int busca = Busca_Aula_numa_Colisao(aula_procurada, colisao_i);
				
			if (busca >= 0)
				return busca;
		}
			
		return -1; // se nao encontrar
	}
	
	
	//------------------------------------------------------------------------------------------
	
	// Adicionar as aulas na colis�o
	
	// preencher lista de aulas colidentes de cada colisao da lista_colisoes
	public void Completar_Lista_Colisoes(List<Colisao> lista_colisoes, List<Turma> lista_turmas_com_colisao)
	{
		int numColisoes = lista_colisoes.size();
		for(int indice=0; indice < numColisoes; indice++)
		{
			Colisao colisao = lista_colisoes.get(indice);
			Completar_Colisao(colisao, lista_turmas_com_colisao);
		}
	}	
	
	// Completar a colis�o: preencher lista de aulas que colidem de acordo com o dia e horario
	public void Completar_Colisao(Colisao colisao, List<Turma> lista_turmas_com_colisao)
	{
		Dia diaColisao = colisao.getDiaColisao();				// dia da colis�o
		Horario horarioColisao = colisao.getHorarioColisao();	// hor�rio da colis�o
		List<Aula> lista_aulas = colisao.getAulasColidem();	// lista de aulas (inicialmente vazia) dessas turmas que ocorrem nesses dias e horarios
		
		int numTurmas = lista_turmas_com_colisao.size();
		for(int indiceT=0; indiceT < numTurmas; indiceT++)	// percorrer lista de turmas com colis�o
		{
			Turma turma = lista_turmas_com_colisao.get(indiceT);			// i-�sima turma da colis�o
			int numAulas = turma.getNumAulas();					// n�mero de aulas dessa turma i
			List<Aula> aulas_turma = turma.getListaAulas();		// lista de aulas da turma i
			
			// percorrer lista de aulas da turma i
			for(int indiceA=0; indiceA < numAulas; indiceA++)
			{
				Aula aula = aulas_turma.get(indiceA);			// j-�sima aula da turma i
				Dia diaAula = aula.getDia();					// dia dessa aula j
				Horario horarioAula = aula.getHorario();		// horario da aula j
				
				boolean dias_iguais = diaAula.equals(diaColisao);
				boolean horarios_iguais = horarioAula.equals(horarioColisao);
				
				// se o par (dia,horario) da aula j "bate" com o par (dia,horario) da colis�o, ...
				if (dias_iguais == true && horarios_iguais == true)
				{
					if(lista_aulas.contains(aula) == false)
						lista_aulas.add(aula);				// ... incluir aula na lista de aulas da colis�o
				}
					
			}
		}
		
	}
	
	
	
	
	
}
