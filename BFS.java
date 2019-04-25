package Puntos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS{

	public ArrayList<ArrayList<Integer>> BFSCC(int[][] pMatriz)
	{
		//Se inicializa la queue y el arreglo que contiene la información de los nodos marcados
		Queue<Integer> fila = new LinkedList<Integer>();
		int V = pMatriz.length;
		boolean marcados[] = new boolean[V];
		
		//Lista de componentes conectados
		ArrayList<ArrayList<Integer>> componentes = new ArrayList<ArrayList<Integer>>();
		
		//Se recorre cada fila de la matriz
		for(int i = 0; i < V; i++)
		{
			/*Si el nodo que se está revisando ya está marcado (ya pertenece a un componente conectado)
			 * se ignora y se empieza a revisar el siguiente
			*/
			if(marcados[i] == true)
			{
				continue;
			}
			
			//Se crea una lista que contiene los vertices conectados en un componente 
			ArrayList<Integer> componente = new ArrayList<Integer>();
			
			//Se agrega el vertice fuente al primer componente
			if(i == 0)
			{ 
				fila.add(0);
				componente.add(fila.remove());
				marcados[0] = true;
			}
			
			//Se marcan todos los vertices que estén conectados al componente actual
			while(!fila.isEmpty())
			{
				//Se marca el vertice actual
				int actual = fila.remove();
				marcados[actual] = true;
				componente.add(actual);
				
				//Se agregan a la fila los nodos que están conectados al vertice actual
				for(int j = 0; j < V; j++)
				{
					/*Si el valor en <i,j> es mayor a 0 quiere decir que hay un eje entre los vertices
					 *en la fila i y la columna j
					 */
					if(pMatriz[i][j] >= 0)
					{
						if(!marcados[j])
						{
							if(!fila.contains(j))
							{
								fila.add(j);
							}
						}
					}
				}
			}
			
			//Se agrega el componente a la lista de componentes
			componentes.add(componente);	
		}
		
		return componentes; 
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
