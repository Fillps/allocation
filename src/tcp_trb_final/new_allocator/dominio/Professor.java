package tcp_trb_final.new_allocator.dominio;

import java.util.ArrayList;
import java.util.List;

public class Professor implements Professor_Interface {
	
	int id;		// seu �ndice na LISTA_PROFESSORES do BancoDados
	String nome;
	List<Turma> turmas;
	
	// *******************************************************************
	// construtor
	public Professor(String nomeProf)
	{
		id = -1;	// em principio, n�o tem ID
		nome = nomeProf;
		turmas = new ArrayList<Turma>(); // ponteiros para as turmas
	}
	
	//****************************************************************************
	// getters
	public List<Turma> getTurmas(){
		return turmas;
	}
	
	public String getNome(){
		return nome;
	}
	
	public int getID(){
		return id;
	}
	
	//****************************************************************************
	// setters
	public void setID(int id_prof){
		this.id = id_prof;
	}
	
	public void Incluir_Turma(Turma turma)
	{
		int indice_turma = turmas.indexOf(turma);
		
		if (indice_turma == -1)
			turmas.add(turma);
	}
	
	//****************************************************************************
	
	@Override
	public boolean equals(Object prof2)
	{
		if (prof2.getClass() == Professor.class)
		{
			Professor prof = (Professor)prof2; // cast para o Object de entrada
			
			String nome1 = this.getNome();
	    	String nome2 = prof.getNome();
	    	
	        return nome1.equals(nome2); // retorna a comparacao entre as strings

		}
		else
			return false;
			
	}
	
}
