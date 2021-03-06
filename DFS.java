
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Stack;

public class DFS {

	private static ArrayList<Integer>[] hijos;
	private static int tamanio;
	private static int[][] matriz;
	
	public static int[] topologicalDFS(int[][] pMatriz)
	{
		
		Stack<Integer> orden = new Stack<Integer>();
		int V = pMatriz.length;
		int[] rta = new int[V];
		boolean[] visitado = new boolean[V];
		
		for(int i = 0; i < V; i++)
		{
			if(!visitado[i])
			{
				ordenTopologico(i, visitado, orden, pMatriz);
			}
		}
		
		int k = 0;
		while(!orden.isEmpty())
		{
			rta[k] = orden.pop();
		}
		
		return rta;
	}
	
	public static boolean DFSCC(int[][] pMatriz)
	{
		//Se inicializan todos los vertices como no visitados
		int V = pMatriz.length;
		boolean[] visitado = new boolean[V];
		boolean tieneCiclo = false;
		
		
		
		//Lista de componentes conectados
		ArrayList<ArrayList<Integer>> componentes = new ArrayList<ArrayList<Integer>>();
		
		//Se actualizan los hijos de cada vertice
		for(int i = 0; i < V; i++)
		{
			for(int j = 0; j < V; j++)
			{
				if(pMatriz[i][j] >= 0)
				{
					hijos[i].add(j);
				}
			}
			
		}
		
		//Se recorren todos los vertices
		for(int i = 0; i < V; i++)
		{
			//Si el vertice no se ha visitado se hace la recursi�n
			if(!visitado[i])
			{	
				//La recursi�n devuelve una lista con todos los vertices conectados al vertice actual
				ArrayList<Integer> componente = new ArrayList<Integer>();
				recursionDFS(i,visitado,componente);
				
				//Se agrega el componente a la lista de componentes conectados
				componentes.add(componente);
			}
			else
			{
				tieneCiclo = true;
			}
		}
		
		return tieneCiclo;
	}
	
	/*
	 * Retorna los vertices conectados al nodo actual
	 */
	public static ArrayList<Integer> recursionDFS(int pFuente, boolean[] pVisitados, ArrayList<Integer> pComponente)
	{
		//Se marca el vertice fuente como visitado
		pVisitados[pFuente] = true;
		
		//Se recorre la lista de hijos del vertice fuente
		for(int i = 0; i < hijos[pFuente].size(); i++)
		{
			//Si el vertice actual no se ha visitado se hace la recursi�n sobre este
			if(!pVisitados[hijos[pFuente].get(i)])
			{
				recursionDFS(i, pVisitados, pComponente);
				
				//Se agrega el vertice actual al componente
				pComponente.add(i);
			}
		}
		
		return pComponente;
		
	}
	
	public static void ordenTopologico(int pVertice, boolean pVisitado[], Stack<Integer> pOrden, int[][] pMatriz)
	{
		pVisitado[pVertice] = true;
		int V = pMatriz.length;
		
			for(int i = 0; i < V; i++)
			{	
				if(pMatriz[pVertice][i] >= 0)
				{
					if(!pVisitado[i])
					{
						ordenTopologico(i, pVisitado, pOrden,pMatriz);
					}
				}
				
			}
		
		pOrden.push(pVertice);
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
			hijos = new ArrayList[tamanio];
			for(int i = 0; i<hijos.length; i++)
			{
				hijos[i] = new ArrayList();
			}
			
			
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
			
			boolean respuesta = DFSCC(matriz);
			System.out.println(respuesta);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
