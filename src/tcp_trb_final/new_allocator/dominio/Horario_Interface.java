package tcp_trb_final.new_allocator.dominio;

public interface Horario_Interface {

	// getters
	public int getHora();
	public int getMinuto();
	public String Horario_toString();
	
	// para compara��es e buscas em listas
	public boolean equals(Object horario2);
	
	// Necess�rio na classe Horario_Servico
	public int compareTo(Horario horario2);
}
