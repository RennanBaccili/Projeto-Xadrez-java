package aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.PartidadeXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m"; //codigos especiais das cores 
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m"; // cores texto

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m"; // cores fundo
	
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	
	// https://stackoverflow.com/questions/2979383/java-clear-the-console
		public static void limparTela() {
			System.out.print("\033[H\033[2J");
			System.out.flush();
		}
	
	public static PosicaoXadrez leiaPosicao(Scanner sc) { // vai ler a posição instanciada pelo usuario no program principal
		try {
		String s = sc.nextLine();
		char coluna = s.charAt(0); // coluna é de xadrez é o primeiro caracter da string
		int linha =Integer.parseInt(s.substring(1)); // reportar  a string a partir de 1 e convertelo para esquerdo para encontra a linha
		return new PosicaoXadrez(coluna,linha);
		}
		catch(RuntimeException e) {
			throw new InputMismatchException("Erro ao estanciar posição de xadrez: valores validos [a1 até h8]");
		}
	}// posicao de xadrez é uma letra e um número
	
	
	public static void printPartida(PartidadeXadrez partidadexadrez, List<PecaXadrez> capturada) {
		printTabuleiro(partidadexadrez.Getpecas());
		System.out.println();
		printPecasCapturadas(capturada);
		System.out.println();
		System.out.println("Turno : " + partidadexadrez.getTurno());
		if(!partidadexadrez.getCheckMate()) {
		System.out.println("Esperando o turno do jogardor " + partidadexadrez.getVezdoJogador());
			if(partidadexadrez.getCheck()) {
				System.out.println("CHECK!");
			}
		} else {
			System.out.println("CHECKMAT!");
			System.out.println("Vencedor: " + partidadexadrez.getVezdoJogador());
		}
	}
	
	
	public static void printTabuleiro(PecaXadrez[][] pecas) { // Interface de usuario
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j],false); // para imprimir as pecas
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	
	public static void printTabuleiro(PecaXadrez[][] pecas,boolean[][] movimentoPossivel) { // Interface de usuario
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j],movimentoPossivel[i][j]); // para imprimir as pecas
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void printPeca(PecaXadrez peca, boolean background) { // imprimir pecas
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
	
		if (peca == null) {
			System.out.print("-" + ANSI_RESET); // valor null é aonde não tem peça
		} 
		else {
			if(peca.getCor() == Cor.WHITE){
				System.out.print(ANSI_WHITE + peca + ANSI_RESET); // aqui é a peca
			}
			else if(peca.getCor() == Cor.BLACK){
				System.out.print(ANSI_YELLOW + peca + ANSI_RESET); // aqui é a peca
			}
				
		}
		System.out.print(" ");
	}
	

	private static void printPecasCapturadas(List<PecaXadrez>capturada) {
		List<PecaXadrez> white =  capturada.stream().filter(x -> x.getCor() == Cor.WHITE).collect(Collectors.toList());
		List<PecaXadrez> black =  capturada.stream().filter(x -> x.getCor() == Cor.BLACK).collect(Collectors.toList());
		System.out.println("Peças capturadas: ");
		System.out.print("White: ");
		System.out.println(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.println(ANSI_RESET);
		System.out.print("Black: ");
		System.out.println(ANSI_YELLOW);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.println(ANSI_RESET);
	}
		
	
}

