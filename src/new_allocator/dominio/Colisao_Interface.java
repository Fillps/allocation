package new_allocator.dominio;

import java.util.List;

public interface Colisao_Interface {

	// getters
	public Dia getDiaColisao();
	public Horario getHorarioColisao();
	public List<Character> getIDsTurmasColidem();
	public List<Turma> getTurmasColidem();
	public List<Aula> getAulasColidem();
	
	// setters
	public void Incluir_Turma_Lista_Colisoes(Turma turma);
	public int Incluir_Lista_Colisoes(List<Colisao> lista_colisoes); // incluir uma colis�o numa lista de colis�es
	
	// para compara��es e buscas em listas
	public boolean equals(Object outra_colisao);
	
}
