package aplicacao;

import java.util.Scanner;

import xadrez.PartidadeXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Programa {
	public static void main(String[]args) {
	Scanner sc = new Scanner(System.in);
	PartidadeXadrez partidadeXadrez = new PartidadeXadrez();
	while(true) {
		UI.printTabuleiro(partidadeXadrez.Getpecas()); // Interface de usuario UI
		System.out.println();
		System.out.print("Informe posicão inicial: ");
		PosicaoXadrez pinicial = UI.leiaPosicao(sc);
		
		System.out.println();
		System.out.print("Informe posicão Final: ");
		PosicaoXadrez pfinal = UI.leiaPosicao(sc);
		
		PecaXadrez capturaPeca = partidadeXadrez.exeMoverPeca(pinicial, pfinal);
		
	}
	}
}