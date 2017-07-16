package new_allocator.serviços;

import new_allocator.dominio.Colisao;
import new_allocator.dominio.Turma;

import java.util.List;


public interface ALOCACAO_Interface {

	public void Alocar_lista_turmas(boolean alocar_aula_regular);
	
	public void Alocar_Turma_Sem_Colisao(boolean alocar_aula_regular, Turma turma_corrente);
	
	public void Alocar_Turma_Com_Colisao(boolean alocar_aula_regular, Turma_servico turma_servico, List<Colisao> lista_colisoes);
	
}
