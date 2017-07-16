package tcp_trb_final.new_allocator.serviços;

import tcp_trb_final.new_allocator.dominio.Aula;
import tcp_trb_final.new_allocator.dominio.TipoRecurso;

import java.util.ArrayList;
import java.util.List;

public class Aula_Servico implements Aula_Servico_Interface {
	
	// construtor
	public Aula_Servico(){
	}
	
	// ****************************************************************************
	// IMPORTACAO
	
	// Incluir o numero de alunos (lido de uma TAG Group) em cada aula da turma (instanciada durante a leitura da TAG filha Session 
	public void Incluir_Numero_Alunos_Lista_Aulas(List<Aula> lista_aulas_turma, int numero_alunos)
	{
		int numAulas = lista_aulas_turma.size();
		
		for(int indice=0; indice < numAulas; indice++)
		{
			Aula aula = lista_aulas_turma.get(indice);
			aula.setNumAlunos(numero_alunos);
		}
	}
		
	//*********************************************************************************
	// ALOCACAO
	
	public List<TipoRecurso> Contar_Tipos_Aula(List<Aula> lista_aulas)
	{
		List<TipoRecurso> lista_tipos = new ArrayList<TipoRecurso>();
		
		int numAulas = lista_aulas.size();
		for(int indice=0; indice < numAulas; indice++)
		{
			Aula aula = lista_aulas.get(indice);
			TipoRecurso tipo_aula = aula.getTipo();
			lista_tipos.add(tipo_aula);	
		}
		
		return lista_tipos;
	}
	
	//------------------------------------------------------------------------------------
	
	public List<List<Aula>> Separar_Aulas_Turma_por_Tipo(List<Aula> lista_aulas, List<TipoRecurso> lista_tipos)
	{
		int numAulas = lista_aulas.size();
		
		for(int indice=0; indice < numAulas; indice++)
		{
			Aula aula = lista_aulas.get(indice);
			TipoRecurso tipo_aula = aula.getTipo();
			lista_tipos.add(tipo_aula);	
		}
		
		int numTipos = lista_tipos.size();
		
		List<List<Aula>> listas_de_listas_aulas = new ArrayList<List<Aula>>();
		
		// cria as 'numTipos' sublistas
		for(int indice=0; indice < numTipos; indice++)
		{
			List<Aula> sublista_aulas = new ArrayList<Aula>();
			listas_de_listas_aulas.add(sublista_aulas);
		}
			
		// preenche as sublistas
		for(int indice=0; indice < numAulas; indice++)
		{
			Aula aula = lista_aulas.get(indice);
			TipoRecurso tipo_aula = aula.getTipo();
			int indice_na_lista_tipos = lista_tipos.indexOf(tipo_aula);	// indice do tipo da aula na lista_tipos
				
			List<Aula> sublista_aulas = listas_de_listas_aulas.get(indice_na_lista_tipos);
			sublista_aulas.add(aula);
		}
			
		return listas_de_listas_aulas;
	}

	
}