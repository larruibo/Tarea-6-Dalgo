
import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS{
	
	private static int tamanio;
	private static int[][] matriz;

	public static ArrayList<ArrayList<Integer>> BFSCC(int[][] pMatriz)
	{
		//Se inicializa la queue y el arreglo que contiene la informaci�n de los nodos marcados
		Queue<Integer> fila = new LinkedList<Integer>();
		int V = pMatriz.length;
		boolean marcados[] = new boolean[V];
		
		//Lista de componentes conectados
		ArrayList<ArrayList<Integer>> componentes = new ArrayList<ArrayList<Integer>>();
		
		//Se recorre cada fila de la matriz
		for(int i = 0; i < V; i++)
		{
			/*Si el nodo que se est� revisando ya est� marcado (ya pertenece a un componente conectado)
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
			
			//Se marcan todos los vertices que est�n conectados al componente actual
			while(!fila.isEmpty())
			{
				//Se marca el vertice actual
				int actual = fila.remove();
				marcados[actual] = true;
				componente.add(actual);
				
				//Se agregan a la fila los nodos que est�n conectados al vertice actual
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
		
		try
		{
			//Se lee el archivo.
			File archivo = new File(args[0]);
			FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			
			//Se obtiene el tamaño de la matriz del nombre del archivo.
			String nom = args[0].replaceAll("distances", "");
			nom = nom.replaceAll(".txt", "");
			
			//Se inicializan todas las variables necesarias.
			tamanio = Integer.parseInt(nom);
			matriz = new int[tamanio][tamanio];
			
			
			String linea;
			int fila = 0;
			while((linea = br.readLine())!= null)
			{
				String[] numeros = linea.split("\t");
				for(int columna = 0; columna<numeros.length; columna++)
				{
					matriz[fila][columna] = Integer.parseInt(numeros[columna]);
				}
				fila++;
			}
			
			//Se acepta la matriz y se inicializan los valores.
			System.out.println("Se aceptó archivo con matriz de tamaño " + tamanio + "\n");
			
			//Se imprime la matriz que se aceptó.
			String prueba = "Matriz aceptada: \n";
			for(int i = 0; i<tamanio; i++)
			{
				for(int j =0; j<tamanio; j++)
				{
					prueba += matriz[i][j] + "\t";
				}
				prueba += "\n";
			}
			if(tamanio <= 20)
			{
				System.out.println(prueba + "\n");
			}
			else
			{
				System.out.println("Matriz cargada, pero no mostrada. \n");
			}
			
			
			//Cierro los flujos cuando ya no son necesarios.
			br.close();
			fr.close();
			
			ArrayList respuesta = BFSCC(matriz);
			for(int i = 0; i<respuesta.size(); i++)
			{
				ArrayList respuesta2 = (ArrayList) respuesta.get(i);
				System.out.println(respuesta2.size());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		

	}

}
