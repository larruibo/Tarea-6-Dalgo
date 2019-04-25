
public class BellmanFord {
	
	
	public int[] bellmanFord(int[][] pMatriz, int pFuente)
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
		
		return costos;
	}

	public static void main(String[] args) {
		

	}

}
