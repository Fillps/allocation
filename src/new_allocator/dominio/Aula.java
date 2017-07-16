package new_allocator.dominio;

import java.util.List;

public class Aula extends AbstractAula implements Aula_Interface {

	private String codDisc;
	private int indice_disc;		// indice da disciplina da turma na LISTA_DISCIPLINAS do BancoDados
	
	private char id_turma;
	private int indice_Turma;		// indice da turma na LISTA_TURMAS do BancoDados
	
	private List<Integer> lista_id_profs; // ponteiro para a lista de ids de professores da turma dessa aula

	//**********************************************************
	// construtor
	
	// usado na leitura do arquivo de entrada (Tag Session)
	public Aula(List<Integer> lista_id_profs)
	{
		this.lista_id_profs = lista_id_profs;
		this.estah_alocada = false;
		this.sala = null;
	}
	
	//**********************************************************
	// getters
	public String getCodDisc(){
		return codDisc;
	}
	
	public int getID_Disciplina(){
		return indice_disc;
	}
	
	public char getIDTurma(){	//para imprimir informa��es da aula
		return id_turma;
	}
	
	public int getIndiceTurma(){	
		return indice_Turma;
	}
	
	public List<Integer> getListaIDsProfs(){
		return lista_id_profs;
	}
	
	public boolean getEstahAlocada() { // atributo da superclasse AbstractAula
		return estah_alocada;
	}
	
	//**************************************************************
	// setters
	
	public void setSala(Sala sala){
		this.sala = sala;
	}
	
	public void setAlocada(){
		this.estah_alocada = true;
	}
	
	public void setCodDisc(String codigo){
		this.codDisc = codigo;
	}
	
	public void setID_Disc(int indice_disc){
		this.indice_disc = indice_disc;
	}
	
	public void setIDTurma(char id_turma){ 	//para imprimir informa��es da aula
		this.id_turma = id_turma;
	}
	

	// ****************************************************************************
	
	/* a aula this ser� igual a aula2 se possuirem os mesmos:
	 * 	indices de disciplina
	 * 	indices de turmas
	 * 	dias e horarios
	 */
	@Override
	public boolean equals(Object aula)
	{
		if (aula.getClass() == Aula.class)
		{
			Aula aula2 = (Aula)aula;	// converter a AbstractAula para Aula
						
			int indiceDisc1 = this.getID_Disciplina();
			int indiceDisc2 = aula2.getID_Disciplina();
			
			int indice_turma1 = this.getIDTurma();
			int indice_turma2 = aula2.getIDTurma();
			
			Dia dia1 = this.getDia();
			Dia dia2 = aula2.getDia();
			
			Horario horario1 = this.getHorario();
			Horario horario2 = aula2.getHorario();
			
			if( indiceDisc1 == indiceDisc2 &&
				indice_turma1 == indice_turma2 &&
				dia1.equals(dia2) == true &&
				horario1.equals(horario2) == true )
				return true;
			else
				return false;
		}
		else
			return false;
		
	}
	
	
}