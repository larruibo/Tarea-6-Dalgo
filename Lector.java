import java.io.*;

public class Lector {

	private static int tamanio;
	private static int[][] matriz;
	private static int [][] costoMinDij;
	
	
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
		
		
		//Initialize all the distance to infinity
		for (int i = 0; i <tamanio ; i++) 
		{
		     distance[i] = INFINITY;
		}
		
		
		//start from the vertex 0
		distance[sourceVertex] = 0;
		
		
		//create SPT
		for (int i = 0; i <tamanio ; i++) 
		{
			//get the vertex with the minimum distance
			int vertex_U = getMinimumVertex(spt, distance);
		
			//include this vertex in SPT
		    spt[vertex_U] = true;
		    
		    //iterate through all the adjacent vertices of above vertex and update the keys
		    for (int vertex_V = 0; vertex_V <tamanio ; vertex_V++) 
		    {
		    	//check of the edge between vertex_U and vertex_V
		    	if(matriz[vertex_U][vertex_V]>0)
		    	{
		    		//check if this vertex 'vertex_V' already in spt and
		    		// if distance[vertex_V]!=Infinity
		    		if(spt[vertex_V]==false && matriz[vertex_U][vertex_V]!=INFINITY)
		    		{
		    			//check if distance needs an update or not
		    			//means check total weight from source to vertex_V is less than
		    			//the current distance value, if yes then update the distance
		    			int newKey =  matriz[vertex_U][vertex_V] + distance[vertex_U];
		    			if(newKey<distance[vertex_V])
		    			{
		    				distance[vertex_V] = newKey;
		    			}  
		            }
		         }
		     }
		 }
		//print shortest path tree
		printDijkstra(sourceVertex, distance);
	}
	
	public static void printDijkstra(int sourceVertex, int [] key)
	{
		System.out.println("Dijkstra Algorithm: (Adjacency Matrix)");
		for (int i = 0; i <tamanio ; i++) 
		{
			System.out.println("Source Vertex: " + sourceVertex + " to vertex " +   + i +
					" distance: " + key[i]);
		}
	}
	
	public static void main(String[] args) {
		
		try
		{
			File archivo = new File(args[0]);
			FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			
			String nom = args[0].replaceAll("distances", "");
			nom = nom.replaceAll(".txt", "");
			tamanio = Integer.parseInt(nom);
			matriz = new int[tamanio][tamanio];
			costoMinDij = new int[tamanio][tamanio];
			
			
			System.out.println("Se aceptó archivo con matriz de tamaño " + tamanio);
			
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
			
			String prueba = "";
			for(int i = 0; i<tamanio; i++)
			{
				for(int j =0; j<tamanio; j++)
				{
					prueba += matriz[i][j] + "\t";
				}
				prueba += "\n";
			}
			
			System.out.println(prueba);
			br.close();
			fr.close();
			
			for(int i = 0; i<tamanio; i++)
			{
				dijkstra_GetMinDistances(i);
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		
	}

}
