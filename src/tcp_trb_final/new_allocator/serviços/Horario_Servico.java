package tcp_trb_final.new_allocator.serviços;

import tcp_trb_final.new_allocator.dominio.Horario;

import java.util.Comparator;
import java.util.List;

public class Horario_Servico implements Horario_Servico_Interface {
	
	
	//********************************************************
	// IMPORTACAO
	
	// Ordenar LISTA_HORARIOS possiveis em ordem crescente
	public static Comparator<Horario> HorarioNameComparator = new Comparator<Horario>(){
		@Override
		public int compare(Horario horario1, Horario horario2){
			return horario1.compareTo(horario2);
		}
	};
	

	//********************************************************
	// ALOCACAO
	
	// Usado na classe Sala_Servico
	public int Quantidade_Horarios_Aula(int duracao, List<Horario> LISTA_HORARIOS, int linha)
	{
		int numHorarios = LISTA_HORARIOS.size(); // numero de hor�rios poss�veis
		
		while(duracao > 0 && linha < numHorarios)
		{
			Horario horario_atual = LISTA_HORARIOS.get(linha); // na 1� itera��o, cont�m o hor�rio de inicio da lista de aulas que chamou o m�todo
			
			if(linha+1 < numHorarios)
			{
				Horario proximo_horario = LISTA_HORARIOS.get(linha+1);
				int intervalo = proximo_horario.compareTo(horario_atual); // numero de minutos entre os horarios
				duracao -= intervalo; // diminui o intervalo da dura��o da aula
			}
			
			linha++; // aumentar a linha significa a necessidade de mais um hor�rio
		}
		
		return linha-1; // retorna 1 linha a menos pois o la�o while ainda incrementa 1 vez at� parar
		
	}
	

	
}
