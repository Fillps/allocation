package new_allocator.dominio;

public abstract class AbstractAula implements AbstractAula_Interface{
	
	protected Dia dia;
	protected Horario horaInicio;
	protected int duracao;
	protected TipoRecurso tipo;
	protected int numAlunos;
	protected Sala sala;	
	protected boolean estah_alocada;
	
	//**************************************************************************
	// getters
	public Dia getDia(){
		return dia;
	}
	
	public Horario getHorario() {
		return horaInicio;
	}
	
	public int getDuracao(){
		return duracao;
	}
	
	public TipoRecurso getTipo() {
		return tipo;
	}
	
	public int getNumAlunos() {
		return numAlunos;
	}
	
	public Sala getSala(){
		return sala;
	}
	
	//**************************************************************************
	// setters
	public void setDia(Dia diaAula){
		this.dia = diaAula;
	}
	
	public void setNumAlunos(int numAlunos){
		this.numAlunos = numAlunos;
	}
	
	public void setHoraInicio(Horario horaInicio){
		this.horaInicio = new Horario(horaInicio);
	}
	
	public void setDuracao(int duracao){
		this.duracao = duracao;
	}
	
	public void setTipoRecurso(TipoRecurso tipo){
		this.tipo = tipo;
	}
	
	//-----------------------------------------------------------------------
	// setar diretamente da leitura do arquivo de entrada
	public void setDia(String weekday){ 
		this.dia = new Dia(weekday);
	}
		
	public void setHoraInicio(String start_time){
		this.horaInicio = new Horario(start_time);
	}
		
	public void setDuracao(String duration){
		int duracao = 0;
			
		if(duration.length() > 0){
			duracao = Integer.parseInt(duration);
		}
			
		this.duracao = duracao;
	}
		
	public void setTipoRecurso(String feature_ids){
		this.tipo = new TipoRecurso(null, feature_ids, null);
	}


}
