package new_allocator.dominio;

import java.util.ArrayList;
import java.util.List;

public class Disciplina implements Disciplina_Interface{
	String codigo;
	String nome;
	List<Turma> turmas;
	
	public Disciplina(String codigo, String nome){
		this.codigo = codigo;
		this.turmas = new ArrayList<Turma>();
		this.nome = nome;
	}
	
	// *********************************************************************
	// getters
	public String getCodigo(){
		return codigo;
	}
	
	// se codigo n�o for String, mas algum m�todo da classe Exporta��o requer que seja
	// basta alterar implementa��o deste m�todo
	public String Codigo_toString(){
		return codigo;
	}
	
	public String getNome(){
		return nome;
	}
	
	
	public List<Turma> get_Lista_Turmas(){
		return turmas;
	}
	
	// *********************************************************************
	// setters

	public void Incluir_Turma(Turma turma){
		turmas.add(turma);
	}
	
	public void Incluir_Lista_Turmas(List<Turma> lista_turmas){
		int tamanho = lista_turmas.size();
		
		for (int indice=0; indice < tamanho; indice++)
		{
			Turma turma = lista_turmas.get(indice); // i-�sima turma da lista_turmas
			turmas.add(turma);
		}
	}
	
	// *****************************************************************
	
	@Override
	public boolean equals(Object disciplina2)
	{
		if (disciplina2.getClass() == Disciplina.class)
		{
			Disciplina disciplina = (Disciplina)disciplina2;
						
			String codigo1 = this.getCodigo();
		    String codigo2 = disciplina.getCodigo();
				
			return (codigo1.compareTo(codigo2) == 0); // retorna resultado da comparacao entre as strings dos c�digos
		}
		else
			return false;
	}
	
	// *****************************************************************


	
}
