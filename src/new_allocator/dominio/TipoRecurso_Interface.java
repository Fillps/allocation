package new_allocator.dominio;

public interface TipoRecurso_Interface {
	
	// getters
	public String getNome();
	public int getID();
	public String ID_toString();
	public boolean getHidden();
	
	// setters a partir da leitura do arquivo de entrada
	public void setID(String feature_ids);
	public void setHidden(String hidden_feature);
	
	// para compara��es e buscas em listas
	public boolean equals(Object tipo2);
	
	// retorna se o tipoRecurso this � do tipo simples (sala sem recurso)
	public boolean Eh_Tipo_Simples();
	
}
