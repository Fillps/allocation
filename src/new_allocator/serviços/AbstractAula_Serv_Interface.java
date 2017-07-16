package new_allocator.serviços;

import new_allocator.dominio.AbstractAula;
import new_allocator.dominio.TipoRecurso;
import new_allocator.dominio.Turma;

import java.util.List;

public interface AbstractAula_Serv_Interface {
	
	public List<AbstractAula> Converter_Lista_para_ListAbstractAula(List<?> lista_aulas);
	
	public int Alocar_Aulas(List<?> lista_aulas);
	
	public void Alocar_aulas_mesmo_tipo(List<AbstractAula> lista_aulas_mesmo_tipo, TipoRecurso tipo_recurso);
	
	public Turma Busca_Turma_de_uma_Aula(List<AbstractAula> lista_aulas);	// Usar apenas para uma lista de Aula
	

}
