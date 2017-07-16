package tcp_trb_final.new_allocator.dominio;

import java.util.ArrayList;
import java.util.List;

public class Colisao implements Colisao_Interface {
	
	private Dia dia;
	private Horario horario;
	private List<Character> lista_ids_turmas;	// lista das ID's das turmas que colidem - tirar no final
	private List<Turma> lista_turmas;
	private List<Aula> lista_aulas;
	
	//**************************************************************
	// construtor
	// usado na classe Turma_Servico
	public Colisao(AbstractAula aula)
	{
		this.dia = aula.getDia();
		this.horario = aula.getHorario();
		this.lista_ids_turmas = new ArrayList<Character>();
		this.lista_turmas = new ArrayList<Turma>();
		this.lista_aulas = new ArrayList<Aula>();
	}
	
	//**************************************************************
	// getters
	public Dia getDiaColisao(){
		return dia;
	}
	
	public Horario getHorarioColisao(){
		return horario;
	}
	
	public List<Character> getIDsTurmasColidem(){ // tirar no final
		return lista_ids_turmas;
	}
	
	public List<Turma> getTurmasColidem(){
		return lista_turmas;
	}
	
	public List<Aula> getAulasColidem(){
		return lista_aulas;
	}
	//**********************************************************************
	// setters
	public void Incluir_Turma_Lista_Colisoes(Turma turma)
	{
		List<Turma> lista_turmas = this.getTurmasColidem();
		int indice = lista_turmas.lastIndexOf(turma);
		
		if (indice == -1)
			lista_turmas.add(turma);
	}
		
	// incluir uma colis�o numa lista de colis�es
	public int Incluir_Lista_Colisoes(List<Colisao> lista_colisoes)
	{		
		int indice = lista_colisoes.indexOf(this);
		
		if (indice == -1)
			lista_colisoes.add(this);
		
		return indice;
	}
	
	//****************************************************************************************
	// Comparador requerido pela sueprclass ObjetoGeral
	// Duas colis�es s�o iguais se seus dias e hor�rios forem iguais
	
	@Override
	public boolean equals(Object outra_colisao)
	{
		if (outra_colisao.getClass() == Colisao.class)
		{
			Colisao colisao = (Colisao)outra_colisao;
			
			Dia dia = this.getDiaColisao();
			Horario horario = this.getHorarioColisao();
			
			Dia dia2 = colisao.getDiaColisao();
			Horario horario2 = colisao.getHorarioColisao();
			
			boolean dias_iguais = dia.equals(dia2);
			boolean horarios_iguais = horario.equals(horario2);
			
			if (dias_iguais==true && horarios_iguais==true)
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	
}
