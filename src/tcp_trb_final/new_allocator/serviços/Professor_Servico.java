package tcp_trb_final.new_allocator.serviços;

import tcp_trb_final.new_allocator.dominio.*;

import java.util.ArrayList;
import java.util.List;

public class Professor_Servico implements Professor_Servico_Interface{
	
	// Extrair as Indices dos professores de uma lista (usado na classe TAG Group)
	public List<Integer> Extrair_Indices_Lista_Professores(List<Professor> lista_professores)
	{
		List<Integer> lista_ids_prof = new ArrayList<Integer>();
		
		int numProfessores = lista_professores.size();
		
		for(int indice=0; indice< numProfessores; indice++)
		{
			Professor professor = lista_professores.get(indice);
			int id_prof = professor.getID();
			lista_ids_prof.add(id_prof);
		}
		
		return lista_ids_prof;
	}
	
	//*****************************************************************
	// Usado na classe IMPORTACAO
	public void Incluir_Professores_nas_suas_Turmas(List<Professor> lista_professores)
	{
		int numProfessores = lista_professores.size();					// n�mero de professores da lista de entrada
		
		// percorre lista de professores
		for(int indiceProf = 0; indiceProf < numProfessores; indiceProf++)
		{
			Professor professor = lista_professores.get(indiceProf);	// i-�simo professor da lista_professores
			int id_professor = professor.getID();						// ID do professor i
			
			List<Turma> lista_turmas_prof = professor.getTurmas();		// lista de turmas do i-�simo professor
			int numTurmas = lista_turmas_prof.size();					// tamanho dessa lista
			
			// percorre lista de turmas do professor i 
			for(int indiceTurma = 0; indiceTurma < numTurmas; indiceTurma++)
			{
				Turma turma = lista_turmas_prof.get(indiceTurma);		// j-�sima turma do professor i
				turma.Incluir_Indice_Professor(id_professor);				// incluir a turma j na lista de turmas do professor i
			}
		}
	}
	
	
}
