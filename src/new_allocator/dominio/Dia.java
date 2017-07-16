package new_allocator.dominio;

public class Dia implements Dia_Interface{

	private Integer dia;
	
	//************************************************
	//construtor a partir de string lida do arquivo de entrada
	public Dia(String weekday){
		
		int dia = 0;
		
		if(weekday.length() > 0){
			dia = Integer.parseInt(weekday);
		}
		
		this.dia = dia;
	}
	
	//************************************************
	// getters
	public int getID(){
		return dia;
	}
	
	// se dia n�o for String, mas algum m�todo requer que seja, basta alterar implementa��o deste m�todo
	public String ID_toString(){
		return dia+"";
	}

	//************************************************
	
	@Override
	public boolean equals(Object dia2)
	{
		if (dia2.getClass() == Dia.class)
		{
			int id_dia = this.getID();
			int id_dia2 = ((Dia)dia2).getID();
		
			return (id_dia == id_dia2);
		}
		else
			return false;
		
	}
	
	// **************************************************************************

	
}
