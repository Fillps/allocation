package new_allocator.dominio;

import java.util.ArrayList;
import java.util.List;

public class BANCO_DADOS implements BANCO_DADOS_Interface{
		
	private List<Professor> LISTA_PROFESSORES;
	private List<Disciplina> LISTA_DISCIPLINAS;
	private List<Turma> LISTA_TURMAS;
	
	// para a matriz de cada local
	private List<Dia> LISTA_DIAS;			// dias poss�veis para aulas
	private List<Horario> LISTA_HORARIOS;	// horarios poss�veis de in�nio de aulas
	
	private List<Predio> LISTA_PREDIOS;
	private List<Sala> LISTA_SALAS;
	
	private List<Turma> LISTA_ESPERA; 					// lista de espera para turmas para as quais n�o foi encontrado um local
	private List<TipoRecurso> LISTA_TIPOS_EXISTENTES;
	public List<TipoRecurso> LISTA_TIPOS_SOLICITADOS;
	
	private TipoRecurso TIPO_SALA_REGULAR; // ID do tipo de sala de aula regular (sem recursos espec�ficos)
											// extra�do da leitura da TAG Feature
		
	
	
	//************************************************************************
	// construtor
	public BANCO_DADOS()
	{
		this.LISTA_PREDIOS = new ArrayList<Predio>();
		this.LISTA_PROFESSORES = new ArrayList<Professor>();
		this.LISTA_DISCIPLINAS = new ArrayList<Disciplina>();
		this.LISTA_TURMAS = new ArrayList<Turma>();
		
		this.LISTA_DIAS = new ArrayList<Dia>();
		this.LISTA_HORARIOS = new ArrayList<Horario>();
		
		this.LISTA_SALAS = new ArrayList<Sala>();
		
		this.LISTA_TIPOS_EXISTENTES = new ArrayList<TipoRecurso>();
		
		this.LISTA_ESPERA = new ArrayList<Turma>();
		
		this.LISTA_TIPOS_SOLICITADOS = new ArrayList<TipoRecurso>();
		//this.LISTA_LOCAIS_ORGANIZADA = new ArrayList<List<Local>>();
	}
	
	//************************************************************************
	// getters
	public BANCO_DADOS getBanco(){
		return this;
	}
	
	public List<Predio> getListaPredios(){
		return LISTA_PREDIOS;
	}
	
	public List<Professor> getListaProfs(){
		return LISTA_PROFESSORES;
	}
	
	public List<Disciplina> getListaDisciplinas(){
		return LISTA_DISCIPLINAS;
	}
	
	public List<Turma> getListaTurmas(){
		return LISTA_TURMAS;
	}
	
	public List<Dia> getListaDias(){
		return LISTA_DIAS;
	}
	
	public List<Horario> getListaHorarios(){
		return LISTA_HORARIOS;
	}
	
	public List<Sala> getListaSalas(){
		return LISTA_SALAS;
	}
		
	public int getNumHorarios(){
		return LISTA_HORARIOS.size();
	}
	
	public int getNumDias(){
		return LISTA_DIAS.size();
	}
	
	public TipoRecurso get_TIPO_SALA_REGULAR(){
		return TIPO_SALA_REGULAR;
	}
	
	public List<Turma> getListaEspera(){
		return LISTA_ESPERA;
	}
	
	public List<TipoRecurso> getListaTiposExistentes(){
		return LISTA_TIPOS_EXISTENTES;
	}
	
	public List<TipoRecurso> getListaTiposSolicitados(){
		return LISTA_TIPOS_SOLICITADOS;
	}
	
	//************************************************************************
	// setters
	public void setListaTiposExistentes (List<TipoRecurso> lista_tipos_sala) {
		this.LISTA_TIPOS_EXISTENTES = lista_tipos_sala;
	}
	
	public void IncluirTipo_ListaTiposExistentes(TipoRecurso novo_tipo_sala) {
		LISTA_TIPOS_EXISTENTES.add(novo_tipo_sala);
	}
	
	// setar valor do TIPO_SALA_REGULAR a partir da leitura do arquivo de entrada (Tag Feature)
	public void set_TIPO_SALA_REGULAR(String name_room, String id_feature)
	{
		TipoRecurso tipo = new TipoRecurso(name_room, id_feature, null);
		
		// se o nome do tipo de sala (id_feature) for uma sala de aula regular, 
        // salvar 'tipo' na constante TIPO_SALA_REGULAR		
		if(name_room.compareTo("Sala de aula") == 0 || name_room.contains("sala") || name_room.contains("aula"))
			this.TIPO_SALA_REGULAR = tipo;
	}
		
	//-------------------------------------------------------------------------------
	// Incluir dados em LISTAS diretamente de Strings lidas do arquivo de entrada (Tag Session)
	
	// Incluir dia na LISTA_DIAS
	public void IncluirDia_ListaDias(String weekday){
		
		Dia dia = new Dia(weekday);

		boolean tem_dia = LISTA_DIAS.contains(dia);
	
		if (tem_dia == false)
			LISTA_DIAS.add(dia);
	}
	
	// Incluir horario na LISTA_HORARIOS
	public void IncluirHorario_ListaHorarios(String start_time)
	{
		Horario horario = new Horario(start_time);
		
		boolean tem_horario = LISTA_HORARIOS.contains(horario);
		
		if (tem_horario == false)
			LISTA_HORARIOS.add(horario);
	}
	
	// Incluir tipo de aula na LISTA_TIPOS_SOLICITADOS
	public void IncluirTipo_ListaTiposSolicitados(String feature_ids)
	{
		if (feature_ids != null)
		{
			TipoRecurso tipo = new TipoRecurso(feature_ids);
		
			if(LISTA_TIPOS_SOLICITADOS.contains(tipo) == false)
				LISTA_TIPOS_SOLICITADOS.add(tipo);
		}
	}
		
	
}

