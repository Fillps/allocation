package tcp_trb_final.new_allocator.serviços;

import tcp_trb_final.new_allocator.dominio.Aula;
import tcp_trb_final.new_allocator.dominio.TipoRecurso;

import java.util.List;

public interface Aula_Servico_Interface {

	public void Incluir_Numero_Alunos_Lista_Aulas(List<Aula> lista_aulas_turma, int numero_alunos);
	
	public List<TipoRecurso> Contar_Tipos_Aula(List<Aula> lista_aulas);
	
	public List<List<Aula>> Separar_Aulas_Turma_por_Tipo(List<Aula> lista_aulas, List<TipoRecurso> lista_tipos);
}
