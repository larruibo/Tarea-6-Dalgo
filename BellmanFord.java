package Puntos;

public class BellmanFord {
	
	
	public int[] bellmanFord(int[][] pMatriz, int pFuente)
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

	public static void main(String[] args) {
		

	}

}
