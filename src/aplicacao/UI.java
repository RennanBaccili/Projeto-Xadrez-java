package aplicacao;

import xadrez.PecaXadrez;

public class UI {
	
	public static void printTabuleiro(PecaXadrez[][] pecas) { // Interface de usuario
		for(int i = 0;i<pecas.length;i++) {
			System.out.print((8 - i)+" ");
			for(int j = 0; j<pecas.length; j++) {
				printPeca(pecas[i][j]);           // para imprimir as pecas
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	private static void printPeca(PecaXadrez peca) { // imprimir pecas 
		if(peca == null) {
			System.out.print("-"); // valor null é aonde não tem peça
		}
		else {
			System.out.print(peca); // aqui é a peca
		}
		System.out.print(" ");
	}
}
