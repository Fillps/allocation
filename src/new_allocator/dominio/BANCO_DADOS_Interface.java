package new_allocator.dominio;

import java.util.List;

public interface BANCO_DADOS_Interface {
	
	// getters
	public BANCO_DADOS getBanco();
	public List<Predio> getListaPredios();
	public List<Professor> getListaProfs();
	public List<Disciplina> getListaDisciplinas();
	public List<Turma> getListaTurmas();
	public List<Dia> getListaDias();
	public List<Horario> getListaHorarios();
	public List<Sala> getListaSalas();
	public int getNumHorarios();	// � igual ao n�mero de LINHAS de cada matriz de disponibilidades de um Local
	public int getNumDias();		// � igual ao n�mero de COLUNAS de cada matriz de disponibilidades de um Local
	public TipoRecurso get_TIPO_SALA_REGULAR();
	public List<Turma> getListaEspera();
	public List<TipoRecurso> getListaTiposExistentes();
	public List<TipoRecurso> getListaTiposSolicitados();
		
	// ***********************************************************************************
	// setters
	public void setListaTiposExistentes(List<TipoRecurso> lista_tipos_sala);
	public void IncluirTipo_ListaTiposExistentes(TipoRecurso novo_tipo_sala);
	public void set_TIPO_SALA_REGULAR(String name_room, String id_feature);
		
	//-------------------------------------------------------------------------------
	// Incluir dados em LISTAS diretamente de Strings lidas do arquivo de entrada (Tag Session)
		
	// Incluir dia na LISTA_DIAS
	public void IncluirDia_ListaDias(String weekday);
		
	// Incluir horario na LISTA_HORARIOS
	public void IncluirHorario_ListaHorarios(String start_time);
		
	// Incluir tipo de aula na LISTA_TIPOS_SOLICITADOS
	public void IncluirTipo_ListaTiposSolicitados(String feature_ids);
	
	
}
