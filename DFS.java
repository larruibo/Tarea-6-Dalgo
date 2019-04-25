package Puntos;

import java.util.ArrayList;
import java.util.Stack;

public class DFS {

	private ArrayList<Integer>[] hijos;
	
	public int[] topologicalDFS(int[][] pMatriz)
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
	
	public boolean DFSCC(int[][] pMatriz)
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
			//Si el vertice no se ha visitado se hace la recursión
			if(!visitado[i])
			{	
				//La recursión devuelve una lista con todos los vertices conectados al vertice actual
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
	public ArrayList<Integer> recursionDFS(int pFuente, boolean[] pVisitados, ArrayList<Integer> pComponente)
	{
		//Se marca el vertice fuente como visitado
		pVisitados[pFuente] = true;
		
		//Se recorre la lista de hijos del vertice fuente
		for(int i = 0; i < hijos[pFuente].size(); i++)
		{
			//Si el vertice actual no se ha visitado se hace la recursión sobre este
			if(!pVisitados[hijos[pFuente].get(i)])
			{
				recursionDFS(i, pVisitados, pComponente);
				
				//Se agrega el vertice actual al componente
				pComponente.add(i);
			}
		}
		
		return pComponente;
		
	}
	
	public void ordenTopologico(int pVertice, boolean pVisitado[], Stack<Integer> pOrden, int[][] pMatriz)
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
		
	}

}
