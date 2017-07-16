package tcp_trb_final.new_allocator.serviços;

import tcp_trb_final.new_allocator.dominio.Dia;

import java.util.Comparator;

public class Dia_Servico implements Dia_Servico_Interface{

	//********************************************************
	// IMPORTACAO
		
	// Ordenar LISTA_DIAS possiveis em ordem crescente
	public static Comparator<Dia> DiaComparator = new Comparator<Dia>(){
		@Override
		public int compare(Dia dia1, Dia dia2){
			int id_dia1 = dia1.getID();
			int id_dia2 = dia2.getID();
			
			return (id_dia1 - id_dia2);
		}
	};
}
