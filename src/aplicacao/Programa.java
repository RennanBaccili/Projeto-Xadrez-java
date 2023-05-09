package aplicacao;

import xadrez.PartidadeXadrez;

public class Programa {
	public static void main(String[]args) {
		
	PartidadeXadrez partidadeXadrez = new PartidadeXadrez();
	UI.printTabuleiro(partidadeXadrez.Getpecas()); // Interface de usuario UI
	}
}
