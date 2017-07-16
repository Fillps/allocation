package tcp_trb_final.new_allocator.serviços;

import tcp_trb_final.new_allocator.dominio.Aula;
import tcp_trb_final.new_allocator.dominio.Colisao;
import tcp_trb_final.new_allocator.dominio.Turma;

import java.util.List;

public interface Colisao_Servico_Interface {

	public int Busca_Aula_numa_Colisao(Aula aula_procurada, Colisao colisao);
	
	public int Busca_Aula_numa_Lista_Colisoes(Aula aula_procurada, List<Colisao> lista_colisoes);
	
	public void Completar_Lista_Colisoes(List<Colisao> lista_colisoes, List<Turma> lista_turmas_com_colisao);
	
	public void Completar_Colisao(Colisao colisao, List<Turma> lista_turmas_com_colisao);
}
