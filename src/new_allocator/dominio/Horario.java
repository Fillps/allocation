package new_allocator.dominio;

public class Horario implements Horario_Interface {
	
	private int hora;
	private int minuto;
	
	//********************************************************
	// construtores

	public Horario(Horario horario_origem) { // usado em AbstractAula
		this.hora = horario_origem.getHora();
		this.minuto = horario_origem.getMinuto();
	}
	
	// usado na leitura do arquivo de entrada (classes BANCO_DADOS e Aula)
	public Horario(String horario_string) { 
		int indice = horario_string.indexOf(":");
		int tamanho = horario_string.length();

		String hh = horario_string.substring(0, indice);
		String mm = horario_string.substring(indice + 1, tamanho);

		hora = Integer.parseInt(hh);
		minuto = Integer.parseInt(mm);
	}
	
	// usado na alocacao na classe Horario_Servico
	public Horario(int hora, int minuto) { // usado em AbstractAula
		this.hora = hora;
		this.minuto = minuto;
	}
	
	// ****************************************************************
	// getters
	public int getHora() {
		return hora;
	}
	
	public int getMinuto() {
		return minuto;
	}
	
	// necessario para escrever no arquivo de saida
	public String Horario_toString(){
		return hora+":"+minuto;
	}
	
	//***************************************************************************
	// dois Horarios s�o iguais se as horas e os minutos s�o iguais

	@Override
	public boolean equals(Object horario2)
	{
		if (horario2.getClass() == Horario.class)
		{
			int hora = this.getHora();
			int min = this.getMinuto();
			
			Horario horario = (Horario)horario2; // cast da entrada de Object para Horario
			int hora2 = horario.getHora();
			int min2 = horario.getMinuto();
			
			return (hora == hora2 && min == min2);
		}
		else
			return false;
		
	}
	
	// Necess�rio na classe Horario_Servico
	public int compareTo(Horario horario2)
	{
		int hora = this.getHora();
		int min = this.getMinuto();
		int total_minutos1 = 60*hora + min; // total de minutos do horario this
		
		Horario horario = (Horario)horario2; // cast da entrada de Object para Horario
		int hora2 = horario.getHora();
		int min2 = horario.getMinuto();
		int total_minutos2 = 60*hora2 + min2; // total de minutos do horario2
		
		return (total_minutos1 - total_minutos2);
	}
	

}
