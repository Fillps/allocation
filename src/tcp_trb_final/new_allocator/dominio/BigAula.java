package tcp_trb_final.new_allocator.dominio;

import java.util.ArrayList;
import java.util.List;

public class BigAula extends AbstractAula implements BigAula_Interface{

	// lista de aulas colidentes a serem consideradas como uma s�
	private List<Aula> aulas_colidentes; 
	
	// lista de indices das turmas das aulas colidentes na LISTA_TURMAS do BancoDados, a ser preenchida na aloca��o
	private List<Integer> lista_indices_turmas; 

	//*****************************************************************************
	// construtor
	public BigAula(Colisao colisao)
	{
		if (colisao != null)
		{
			this.dia = colisao.getDiaColisao();				// atributo da classe pai "AbstractAula"
			this.horaInicio = colisao.getHorarioColisao();	// atributo da classe pai "AbstractAula"

			this.aulas_colidentes = colisao.getAulasColidem();
			this.tipo = aulas_colidentes.get(0).getTipo(); 		// tipo da 1� aula da lista de aulas da colisao (o mesmo de todas)
			this.duracao = Extrair_Maior_Duracao(aulas_colidentes);

			int numAulas = aulas_colidentes.size();

			// obter o total de alunos da Big Aula
			int totalAlunos = 0;

			for (int indice = 0; indice < numAulas; indice++)
			{
				Aula aula = aulas_colidentes.get(indice); // i-�sima aula da lisat de colisoes
				int numAlunosAula = aula.getNumAlunos(); // numero de alunos da aula i
				
				totalAlunos += numAlunosAula; // atualizar acumulador de numero de alunos
			}

			this.numAlunos = totalAlunos;
			
			this.lista_indices_turmas = new ArrayList<Integer>();
		}
	}

	//*****************************************************************************
	// getters de atributos pr�prios
	
	public List<Aula> getListaAulas() {
		return aulas_colidentes;
	}
	
	public List<Integer> getListaIndicesTurmas(){
		return lista_indices_turmas;
	}

	// se todas as aulas da Big Aula estiverem alocadas, retornar true
	public boolean getEstahAlocada(){
		int numAulas = this.aulas_colidentes.size();
		
		for (int indice = 0; indice < numAulas; indice++)
		{
			Aula aula = this.aulas_colidentes.get(indice);
			boolean estah_alocada = aula.getEstahAlocada();
			
			if(estah_alocada == false)
				return false;
		}
		
		return true;
	}
	
	//**********************************************************************************
	// setters
	
	public void setSala(Sala sala) // setar o local de todas as "aulinhas" da Big Aula
	{
		int numAulas = this.aulas_colidentes.size();
		
		for (int indice = 0; indice < numAulas; indice++)
		{
			Aula aula = this.aulas_colidentes.get(indice);
			aula.setSala(sala);
		}
		
		this.sala = sala;
	}
	
	
	// setar a alocacao de todas as "aulinhas" da Big Aula
	public void setAlocada()
	{
		int numAulas = this.aulas_colidentes.size();
		
		for (int indice = 0; indice < numAulas; indice++)
		{
			Aula aula = this.aulas_colidentes.get(indice);
			aula.setAlocada();
		}
		
		this.estah_alocada = true;
	}
	
	
	//----------------------------------------------------------------------------------------------
	

	@Override
	public boolean equals(Object bigAula)
	{
		if (bigAula.getClass() == BigAula.class)
		{
			BigAula aula2 = (BigAula)bigAula;	// converter a AbstractAula para Aula
			
			Dia dia1 = this.getDia();
			Dia dia2 = aula2.getDia();
			
			Horario horario1 = this.getHorario();
			Horario horario2 = aula2.getHorario();
			
			if( dia1.equals(dia2) == true && horario1.equals(horario2) == true )
				return true;
			else
				return false;
		}
		else
			return false;
		
	}
	
	//************************************************************
	// Usado no construtor
	public int Extrair_Maior_Duracao(List<Aula> lista_aulas)
	{
		int maior_duracao = 0;
		int numAulas = lista_aulas.size();
		
		for (int indice=0; indice < numAulas; indice++)
		{
			Aula aula = lista_aulas.get(indice);
			int duracao = aula.getDuracao();
			
			if (duracao > maior_duracao)
				maior_duracao = duracao;
		}
		
		return maior_duracao;
	}
	

}
