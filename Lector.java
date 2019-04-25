import java.io.*;
import java.sql.Timestamp;

public class Lector {

	private static int tamanio;
	private static int[][] matriz;
	private static int [][] costoMinDij;
	private static int[][] costoBell;
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
		addToMatrix(sourceVertex, distance, costoMinDij);
	}
	
	//Método para añadir todos los llamados a una matriz.
	public static void addToMatrix(int sourceVertex, int [] key, int matrizP[][])
	{
		for (int i = 0; i <tamanio ; i++) 
		{
			matrizP[sourceVertex][i] = key[i];
		}
	}
	
	
	
	public static void bellmanFord(int[][] pMatriz, int pFuente)
	{
		int V = pMatriz.length;
		int[] costos = new int[V];
		
		for(int i = 0; i < V; i++)
		{
			costos[i] = 999999;
		}
		
		costos[pFuente] = 0;
		
		for(int fuente = 0; fuente < V ; fuente++)
		{
			for(int destino = 0; destino < V; destino++)
			{
				if(pMatriz[fuente][destino] != -1)
				{
					if(costos[destino] > costos[fuente] + pMatriz[fuente][destino])
					{
						costos[destino] = costos[fuente] + pMatriz[fuente][destino];
					}
				}
			}
			
		}
		//Como este es un método para encontrar los caminos de 1 vertice en especifico, 
		//añado los resultados a una matriz completa.
		addToMatrix(pFuente, costos, costoBell);
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
			Timestamp tiempoInicio = new Timestamp(System.currentTimeMillis());
			
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
			costoBell = new int[tamanio][tamanio];
			
			
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
			
			//Se ejecuta Dijkstra
			Timestamp tiempoInicioDij = new Timestamp(System.currentTimeMillis());
			String resultado = "Resultado de caminos minimos de Dijkstra: \n";
			
			for(int i = 0; i<tamanio; i++)
			{
				dijkstra_GetMinDistances(i);
				for(int j =0; j<tamanio; j++)
				{
					resultado += costoMinDij[i][j] + "\t";
				}
				resultado += "\n";
			}
			Timestamp tiempoFinDij = new Timestamp(System.currentTimeMillis());
			if(tamanio <= 20)
			{
				System.out.println(resultado + "\n");
			}
			else
			{
				System.out.println("Matriz calculada, pero no mostrada. \n");
			}
			
			
			//Se ejecuta Bellman-Ford
			Timestamp tiempoInicioBell = new Timestamp(System.currentTimeMillis());
			String resultado2 = "Resultado de caminos mínimos de Bellman-Ford: \n";
			for(int i = 0; i<tamanio; i++)
			{
				bellmanFord(matriz, i);
				for(int j = 0; j< tamanio; j++)
				{
					resultado2 += costoBell[i][j] + "\t";
				}
				resultado2 += "\n";
			}
			Timestamp tiempoFinBell = new Timestamp(System.currentTimeMillis());
			if(tamanio <= 20)
			{
				System.out.println(resultado2 + "\n");
			}
			else
			{
				System.out.println("Matriz calculada, pero no mostrada. \n");
			}
			
			
			//Se ejecuta Floyd-Warshall
			Timestamp tiempoInicioWar = new Timestamp(System.currentTimeMillis());
			floydWarshall();
			String resultado3 = "Resultado de caminos mínimos de Floyd-Warshall: \n";
			for (int i = 0; i<tamanio; i++)
			{
				for(int j = 0;j<tamanio; j++)
				{
					resultado3 += warshalC[i][j] + "\t";
				}
				resultado3 += "\n";
			}
			Timestamp tiempoFinWar = new Timestamp(System.currentTimeMillis());
			if(tamanio <= 20)
			{
				System.out.println(resultado3 + "\n");
			}
			else
			{
				System.out.println("Matriz calculada, pero no mostrada. \n");
			}
			
			System.out.println("Tiempo que tardó Dijkstra: " + (tiempoFinDij.getTime()-tiempoInicioDij.getTime()) + " milisegundos.");
			System.out.println("Tiempo que tardó Bellman-Ford: " + (tiempoFinBell.getTime()-tiempoInicioBell.getTime())+ " milisegundos.");
			System.out.println("Tiempo que tardó Floyd-Warshall: " + (tiempoFinWar.getTime()-tiempoInicioWar.getTime())+ " milisegundos.");
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		
	}

}
