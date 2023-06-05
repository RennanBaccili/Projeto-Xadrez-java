package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExcecaoXadrez;
import xadrez.PartidadeXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Programa {
	public static void main(String[]args) {
	Scanner sc = new Scanner(System.in);
	PartidadeXadrez partidadeXadrez = new PartidadeXadrez();
	List<PecaXadrez> capturada = new ArrayList<>();
	
	while(!partidadeXadrez.getCheckMate()) {
		try {
			UI.limparTela();
			UI.printPartida(partidadeXadrez, capturada); // Interface de usuario UI
			System.out.println();
			System.out.print("Informe posicão inicial: ");
			PosicaoXadrez pinicial = UI.leiaPosicao(sc);
			
			boolean[][] movimentoPossivel = partidadeXadrez.movimentoPossivel(pinicial);
			UI.limparTela();
			UI.printTabuleiro(partidadeXadrez.Getpecas(),movimentoPossivel);
			
			System.out.println();
			System.out.print("Informe posicão Final: ");
			PosicaoXadrez pfinal = UI.leiaPosicao(sc);
			
			PecaXadrez capturaPeca = partidadeXadrez.exeMoverPeca(pinicial, pfinal);
			
			if (capturaPeca != null) {
				capturada.add(capturaPeca);
			}
			if (partidadeXadrez.getPromocao() != null) {
				System.out.println("Enter com a peça para a promoção (B/C/R/T): ");
				String type = sc.nextLine();
				partidadeXadrez.replacePromocaoPeca(type);
			}
			
			}
		catch(ExcecaoXadrez e) {
			System.out.println(e.getMessage());
			sc.nextLine();
		}
		catch(InputMismatchException e) {
			System.out.println(e.getMessage());
			sc.nextLine();
		}
		
	}
	UI.limparTela();
	UI.printPartida(partidadeXadrez, capturada);
	
	}
}