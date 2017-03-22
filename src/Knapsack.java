import java.io.*;
import java.util.*;

class Knapsack {
	
	public static void main(String[] args) {

        BufferedReader inReader = 
        				new BufferedReader(new InputStreamReader(System.in));

        int w = 0, 
        	n = 0;

        try {

        	//primeira linha do arquivo
        	String arr [] = new String [2];
        	arr = inReader.readLine().split(" "); 
        	n = Integer.parseInt(arr[0]);
        	w = Integer.parseInt(arr[1]);

        	int val[] = new int[n], 
        		wt[] = new int[n];

            //segunda linha do arquivo (Pesos dos itens)
        	arr = new String[n];
            arr = inReader.readLine().split(" ");
            for (int i = 0; i < arr.length; i++) 
            	wt[i] = Integer.parseInt(arr[i]);

            //terceira linha do arquivo (Valores dos itens)
            arr = new String[w];
            arr = inReader.readLine().split(" ");
            for (int i = 0; i < arr.length; i++) 
            	val[i] = Integer.parseInt(arr[i]);

            inReader.close();

            System.out.println("Valor: "+knapsack(val, wt, w));


        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
	}


	/**
	*	Parametros: val[] - Valores de cada item.
	*				wItem[] - Pesos de cada item
	*				wKnapsack - Peso maximo da mochila
	*
	*	Return: Valor maximo possivel
	*/
	private static int knapsack(int val[], int wItem[], int wKnapsack){

		int n = wItem.length; //Recupera a quantidade de itens.
		//Cria matriz que representa a tabela de pesos (pesos itens x peso da mochila)
		int [][] m = new int[n + 1][wKnapsack + 1]; 

		//Instancia primeira coluna com zeros (peso da mochila zero nao cabe nenhum item)
		for (int i = 0; i <= n; i++)
			m [i][0] = 0;	

		//Instancia primeira linha com zeros
		for (int i = 0; i <= wKnapsack; i++)
			m [0][i] = 0;

		for (int item = 1; item <= n; item++) {
			//preenchendo os valores na tabela
			for (int knapsackWeight = 1; knapsackWeight <= wKnapsack; knapsackWeight++ ) {
				
				//Caso o item atual seja menor ou igual ao peso atual da mochila
				if (wItem[item - 1] <= knapsackWeight)
					// retorna o max entre a soma do valor do item atual e o do item (pesoMochila - pesoItem) 
							// e o valor do item da linha anterior e da mesma coluna
					m [item][knapsackWeight] = Math.max 
										(val[item - 1] + m[item - 1][ knapsackWeight-wItem[item - 1] ], 
											m[item - 1][knapsackWeight]);
				else
					//Caso nao caiba na mochila, repete o valor do item da linha anterior de mesma coluna
					m [item][knapsackWeight] = m[item - 1][knapsackWeight];
			}
		}

		//exibindo a tabela
        /*for (int[] rows : m) {
            for (int col : rows) {
                System.out.format("%5d", col);
            }
            System.out.println();
        }*/

        //Processo de decisao dos produtos
		ArrayList<Integer> selectedItems = new ArrayList<Integer>();
		int colWeightKnapsack = wKnapsack;
		for (int item = n; item >= 1; item--) {
			if (m [item][colWeightKnapsack] != m [item - 1][colWeightKnapsack]){
				selectedItems.add(item);
				colWeightKnapsack -= wItem[item - 1];
			}
		}

		System.out.println("Produtos escolhidos: "+selectedItems);

		//ultimo elemento da tabela sera o valor maximo possivel
		return m[n][wKnapsack];
	}

}