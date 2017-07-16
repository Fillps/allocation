package new_allocator.dominio;



import java.util.List;

public class Predio implements Predio_Interface {
	
	String id;
	List<Sala> lista_salas;
	
	public Predio(String id_building, List<Sala> listaSalas)
	{
		// obter numero do predio a partir string 'id_building' lida do arquivo de entrada
		//this.id = Extrair_numeracao_predio(id_building);
		this.id = id_building;
		this.lista_salas = listaSalas;
	}
	
	// **************************************************************************
	// getters
	public String getID(){
		return id;
	}
	
	// se id n�o for String, mas algum m�todo da classe Exporta��o requer que seja
	// basta alterar implementa��o deste m�todo
	public String ID_toString(){
		return id;
	}
	
	public List<Sala> getListaSalas(){
		return lista_salas;
	}
	
	// **************************************************************************
	
	// retornar a ID do predio de indice 'indice' na LISTA_PREDIOS do Banco de Dados
	public String getNumPredio_ListaPredios(int indice, List<Predio> LISTA_PREDIOS){
		Predio predio = LISTA_PREDIOS.get(indice);
		return predio.ID_toString();
	}

	// **************************************************************************
	public void Imprimir()
	{
		System.out.printf("Numero %d\n", this.getID());
	}
	
}
