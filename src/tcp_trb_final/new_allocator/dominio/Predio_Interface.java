package tcp_trb_final.new_allocator.dominio;

import java.util.List;

public interface Predio_Interface {

	// getters
	public String getID();
	public String ID_toString(); 		// se id n�o for String
	public List<Sala> getListaSalas();
	
	// retornar a ID do predio de indice 'indice' na LISTA_PREDIOS do Banco de Dados
	public String getNumPredio_ListaPredios(int indice, List<Predio> LISTA_PREDIOS);
	
}
