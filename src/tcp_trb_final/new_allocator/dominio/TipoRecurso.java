package tcp_trb_final.new_allocator.dominio;

public class TipoRecurso implements TipoRecurso_Interface{
	
	String nome;
	int id;
	boolean hidden;
	
	//**********************************************************
	// construtores
	
	public TipoRecurso(String id_tipo){
		setID(id_tipo);
	}
	
	// a partir das strings lidas do arquivo de entrada (Tag Feature)
	public TipoRecurso(String name, String id_feature, String hidden){
		nome = name;
		setID(id_feature);
		setHidden(hidden);
	}
	
	//**********************************************************
	// getters
	public String getNome(){
		return nome;
	}
	
	public int getID(){
		return id;
	}
	
	// necess�rio para escrever no arquivo de saida
	public String ID_toString(){
		return id+"";
	}
		
	public boolean getHidden(){
		return hidden;
	}

	
	//*********************************************************************
	// setters a partir da leitura do arquivo de entrada
	
	public void setID(String feature_ids){
		int id_tipo = 0; // se a string de entrada � vazia

		if(feature_ids == null){
			feature_ids = new String();
		}
		if (feature_ids.length() > 0)
			id_tipo = Integer.parseInt(feature_ids);
			
		this.id = id_tipo;
	}
	

	public void setHidden(String hidden_feature)
	{
		boolean hidden = false; // se n�o existir a informa��o de hidden, assumir valor como false
		
		if(hidden_feature == null)
			hidden = false;
		else if (hidden_feature.length() > 0)
         	hidden = Boolean.parseBoolean(hidden_feature); // se existir a informa��o de hidden, converter para boolean
        
		this.hidden = hidden;
	}
	
	//**************************************************************************
	@Override
	public boolean equals(Object tipo2)
	{
		if(tipo2.getClass() == TipoRecurso.class)
		{
			TipoRecurso tipo = (TipoRecurso)tipo2; // cast do Object para TipoRecurso
			
			int id_tipo1 = this.getID();
			int id_tipo2 = tipo.getID();
			
			return (id_tipo1 == id_tipo2); // retorna o reultado da compara��o das ID's dos tipos
		}
		else
			return false;
	}
	
	
	//**************************************************************************
	// retorna se o tipoRecurso this � do tipo simples (sala sem recurso : id = 0)
	public boolean Eh_Tipo_Simples(){ 
		int id = this.getID();
		return (id == 0);
	}
}
