package tcp_trb_final.new_allocator.serviços;

import tcp_trb_final.new_allocator.dominio.BigAula;
import tcp_trb_final.new_allocator.dominio.Colisao;

import java.util.List;

public interface BigAula_Servico_Interface {

	public void Preencher_Lista_Big_Aulas(List<BigAula> lista_big_aulas, List<Colisao> lista_colisoes);
	
	public String getDados_ListaAulas(BigAula bigAula);
	
}
