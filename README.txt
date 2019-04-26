Desarrollado por: 
Luis Alfonso Ruiz
Sebastián Diaz


Para ejecutarlo (en mac, por lo menos), desde el terminal y sobre el directorio donde se encuentra el archivo, ejecutar los comandos: javac archivo.java (para compilar, donde archivo es el nombre del archivo) y java Clase distancesX.txt (para ejecutar, donde X representa al número de nodos en la matriz y Clase es el nombre de la clase a ejecutar, en este caso, el mismo que archivo).

Por ejemplo, para ejecutar Lector.java se debe seguir algo similar a:
javac Lector.java
java Lector distances5.txt

1) En esta solución se plantearon los 3 algoritmos para encontrar rutas más cortas. Dijkstra, Bellman-Ford y Floyd-Warshall. El programa recibe el archivo y lo lee, y después genera la matriz con los datos en el archivo. Con la matriz, realiza los 3 algoritmos (y sus respectivos tiempos), calcula las matrices de respuesta y retorna los resultados. Se colocó una restricción para que imprimiera en consola todas las matrices, siempre y cuando el número de nodos fuera igual o menor a 20. De ser mayor ese número, las matrices se calculan pero no se muestran en consola. Se observó que para matrices pequeñas (como la de 5 nodos) no hay diferencia en el tiempo de ejecución. Para matrices intermedias(como la de 100 nodos), las diferencias son de más o menos 100 milisegundos entre cada uno de los algoritmos, siendo Floyd-Warshall el más rápido y Bellman-Ford el más lento. Para la matriz de 1000 nodos no se calcularon tiempos debido a que el programa se demoró bastante.


2)El archivo BFS.java contiene una implementación de un BFS. Sin embargo, no pudo ser probado.

3)El archivo DFS.java contiene una implementación de un DFS. Sin embargo, no pudo ser probado.

