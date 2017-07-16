package tcp_trb_final.new_allocator.dominio;

public interface AbstractAula_Interface {

	// getters
	public Dia getDia();
	public Horario getHorario();	
	public int getDuracao();	
	public TipoRecurso getTipo();	
	public int getNumAlunos();
	public Sala getSala();
	public boolean getEstahAlocada(); // implementa��o nas classes filhas
	
	// setters
	public void setDia(Dia diaAula);
	public void setNumAlunos(int numAlunos);
	public void setHoraInicio(Horario horaInicio);
	public void setDuracao(int duracao);
	public void setTipoRecurso(TipoRecurso tipo);
	public void setSala(Sala sala);			// implementa��o nas classes filhas
	public void setAlocada();				// implementa��o nas classes filhas
	
	
	// setar diretamente da leitura do arquivo de entrada
	public void setDia(String weekday);
	public void setHoraInicio(String start_time);
	public void setDuracao(String duration);
	public void setTipoRecurso(String feature_ids);
}
