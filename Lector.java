import java.io.*;

public class Lector {

	
	public static void main(String[] args) {
		
		try
		{
			File archivo = new File(args[0]);
			FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);
			
			String nom = args[0].replaceAll("distances", "");
			nom = nom.replaceAll(".txt", "");
			int tamanio = Integer.parseInt(nom);
			int[][] matriz = new int[tamanio][tamanio];
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
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		
	}

}
