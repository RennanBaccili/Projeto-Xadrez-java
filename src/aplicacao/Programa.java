package aplicacao;

import xadrez.PartidadeXadrez;
import xadrez.UI;

public class Programa {
	public static void main(String[]args) {
		
	PartidadeXadrez partidadeXadrez = new PartidadeXadrez();
	UI.printTabuleiro(partidadeXadrez.Getpecas()); // Interface de usuario UI
	}
}
