package new_allocator.serviços;

import new_allocator.dominio.*;

import java.util.List;

public interface Sala_Servico_Interface {

	//public static Comparator<Sala> SalaCapacidadeComparator = new Comparator<Sala>();
	//public static Comparator<Sala> SalaComparatorTipo = new Comparator<Sala>();
	
	// Criar Salas com base na lista de Pr�dios do Banco de Dados: uma sala para cada sala dispon�vel para uso
	public void Preencher_LISTA_SALAS(List<Predio> LISTA_PREDIOS);
	
	
	/* Encontrar um sala na LISTA_LOCAIS que satisfa�a as entradas: 
	 * contenha o tipo de recurso procurado,
	 * livre nos dias e hor�rios desejados
	 * comporte a capacidade m�nima (n�mero de alunos)
	 */
	public List<Sala> Preencher_Lista_Salas_Possiveis(TipoRecurso tipo_recurso, int capacidadeMinima,
                                                      List<Dia> lista_dias, List<Horario> lista_horarios);

	// ver se uma sala est� dispon�vel nos dias e hor�rios desejados
	public boolean Ver_Disponibilidade_Sala(Sala sala_corrente, List<Dia> lista_dias, List<Horario> lista_horarios);

	// Se uma aula n�o requer sala com algum recurso, filtrar a lista de salas poss�veis para ela,
	// ou seja, tomar apenas os salas que contem recurso do TIPO_SALA_REGULAR
	public List<Sala> Filtrar_Lista_Salas_Aula_Regular(List<Sala> lista_salas_possiveis);

	// marcar o sala corrente como ocupado: colocar false na disponibilidade do sala em
	// todos os hor�rios e dias das aulas alocadas
	public void Marcar_Sala_Ocupada(Sala sala_corrente, List<Dia> lista_dias, List<Horario> lista_horarios);


	// para teste: preenche a matriz de sstring com as turmas (codido+id) das turmas que ocupara o sala corrente
	public void Marcar_Sala_Ocupada_Codigos_Turmas(String turmas_reserva, Sala sala_corrente,
                                                   List<Dia> lista_dias, List<Horario> lista_horarios);

	// M�todo principal
	// Encontrar sala para aulas de acordo com o tipo_recurso que requerem, sendo que
	// tipo_recurso = 0 significa que a aula n�o requer recurso
	public Sala Encontrar_Sala(boolean alocar_aula_regular, TipoRecurso tipo_recurso, int capacidadeMinima,
                               int duracao, List<Dia> lista_dias, List<Horario> lista_horarios);
	
	
}
