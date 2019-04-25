import java.io.*;

public class Lector {

	private static int tamanio;
	private static int[][] matriz;
	private static int [][] costoMinDij;
	private static int [][] warshalC;
	
	
	public static int getMinimumVertex(boolean [] mst, int [] key)
	{
		int minKey = Integer.MAX_VALUE;
		int vertex = -1;
		for (int i = 0; i <tamanio ; i++) 
		{
			if(mst[i]==false && minKey>key[i])
			{
		          minKey = key[i];
		          vertex = i;
		    }
		}
		return vertex;
	}
	
	
	public static void dijkstra_GetMinDistances(int sourceVertex)
	{
		boolean[] spt = new boolean[tamanio];
		int [] distance = new int[tamanio];
		int INFINITY = Integer.MAX_VALUE;
		
		
		//Se inicializan todas las distancias en infinito
		for (int i = 0; i <tamanio ; i++) 
		{
		     distance[i] = INFINITY;
		}
		
		
		//Se inicializa el vertice actual en 0
		distance[sourceVertex] = 0;
		
		
		//crear SPT
		for (int i = 0; i <tamanio ; i++) 
		{
			//Obtener el vertice con la menor distancia
			int vertex_U = getMinimumVertex(spt, distance);
		
			//Incluir el vertice en  SPT
		    spt[vertex_U] = true;
		    
		    //Iterar todos los vertives adyacentes y actualizar los valores
		    for (int vertex_V = 0; vertex_V <tamanio ; vertex_V++) 
		    {
		    	if(matriz[vertex_U][vertex_V]>0)
		    	{
		    		if(spt[vertex_V]==false && matriz[vertex_U][vertex_V]!=INFINITY)
		    		{
		    			//Verifica si se necesita actualizar o no
		    			int newKey =  matriz[vertex_U][vertex_V] + distance[vertex_U];
		    			if(newKey<distance[vertex_V])
		    			{
		    				distance[vertex_V] = newKey;
		    			}  
		            }
		         }
		     }
		 }
		//Como este es un método para encontrar los caminos de 1 vertice en especifico, 
		//añado los resultados a una matriz completa.
		addDijkstra(sourceVertex, distance);
	}
	
	//Método para añadir todos los llamados a una matriz.
	public static void addDijkstra(int sourceVertex, int [] key)
	{
		for (int i = 0; i <tamanio ; i++) 
		{
			costoMinDij[sourceVertex][i] = key[i];
		}
	}
	
	
	
	public static int[] bellmanFord(int[][] pMatriz, int pFuente)
	{
		int V = pMatriz.length;
		int E = V*V;
		int[] costos = new int[V];
		int[] predecesor = new int[V];
		
		for(int i = 0; i < V; i++)
		{
			costos[i] = Integer.MAX_VALUE;
		}
		
		costos[pFuente] = 0;
		
		for(int fuente = 0; fuente < V; fuente++)
		{
			for(int destino = 0; destino < V; destino++)
			{
				if((pMatriz[fuente][destino] != Integer.MAX_VALUE) && (pMatriz[fuente][destino] != -1))
				{
					if(Integer.max(costos[fuente], costos[destino] + pMatriz[fuente][destino]) == costos[fuente])
					{
						costos[fuente] = costos[destino] + pMatriz[fuente][destino];
						predecesor[destino] = fuente;
					}
				}
			}
			
		}
		
		return predecesor;
	}
	
	public static void floydWarshall()
	{
		//Se inicializa la matriz con los valores por defecto de los arcos, o un número grande
		//Si no hay camino entre dos vertices.
		for (int i = 0; i<tamanio; i++)
		{
			for(int j = 0;j<tamanio; j++)
			{
				if(matriz[i][j] == -1)
				{
					//No se puede usar Integer.Max porque al sumarse más abajo con cualquier otro 
					//produce error.
					warshalC[i][j] = 9999;
				}
				else
				{
					warshalC[i][j] = matriz[i][j];
				}
			}
		}
		
		//Se hacen pasos intermedios k para llegar de un vertice i a un vertice j.
		for(int k = 0; k<tamanio; k++)
		{
			for(int i = 0; i<tamanio; i++)
			{
				for(int j = 0; j<tamanio; j++)
				{
					//Si es más barato con un camino intermedio, cambio el valor.
					if(warshalC[i][k] + warshalC[k][j] < warshalC[i][j])
					{
						warshalC[i][j] = warshalC[i][k] + warshalC[k][j];
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
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
			costoMinDij = new int[tamanio][tamanio];
			warshalC = new int[tamanio][tamanio];
			
			
			//Se acepta la matriz y se inicializan los valores.
			System.out.println("Se aceptó archivo con matriz de tamaño " + tamanio + "\n");
			
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
			
			//Se imprime la matriz que se aceptó.
			String prueba = "Matriz aceptada: ";
			for(int i = 0; i<tamanio; i++)
			{
				for(int j =0; j<tamanio; j++)
				{
					prueba += matriz[i][j] + "\t";
				}
				prueba += "\n";
			}
			
			System.out.println(prueba + "\n");
			
			//Cierro los flujos cuando ya no son necesarios.
			br.close();
			fr.close();
			
			//Se ejecuta Dijkstra
			String resultado = "Resultado de caminos minimos de Dijkstra:";
			for(int i = 0; i<tamanio; i++)
			{
				dijkstra_GetMinDistances(i);
			}
			for(int i = 0; i<tamanio; i++)
			{
				for(int j =0; j<tamanio; j++)
				{
					resultado += costoMinDij[i][j] + "\t";
				}
				resultado += "\n";
			}
			System.out.println(resultado + "\n");
			
			
			//Se ejecuta Bellman-Ford
			String resultado2 = "Resultado de caminos mínimos de Bellman-Ford:";
			for(int i = 0; i<tamanio; i++)
			{
				int[] bellman = bellmanFord(matriz, i);
				for(int j = 0; j< bellman.length; j++)
				{
					resultado2 += bellman[j] + "\t";
				}
				resultado2 += "\n";
			}
			
			System.out.println(resultado2 + "\n");
			
			
			//Se ejecuta Floyd-Warshall
			floydWarshall();
			String resultado3 = "Resultado de caminos mínimos de Floyd-Warshall:";
			for (int i = 0; i<tamanio; i++)
			{
				for(int j = 0;j<tamanio; j++)
				{
					resultado3 += warshalC[i][j] + "\t";
				}
				resultado3 += "\n";
			}
			System.out.println(resultado3);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		
	}

}
