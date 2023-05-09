package xadrez;

import jogodetabueiro.Posicao;
import jogodetabueiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidadeXadrez {

	private Tabuleiro tabuleiro;

	public PartidadeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);// classe que determina o tamanho de um tabuleiro
		inicialSetup();
	}
	
	public PecaXadrez[][] Getpecas() {
//pelo desevenvolvimente em camadas, o programa deve enxergar apenas pecaXadrez, e nao a Peça interna que  esta na parte acima
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for(int i=0; i < tabuleiro.getLinhas();i++) {
			for(int j=0;j < tabuleiro.getColunas();j++) {
				mat[i][j] = (PecaXadrez)tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	private void inicialSetup() { // funcao que faz inicializacao da partida de xadrez
		tabuleiro.placePeca(new Torre(tabuleiro,Cor.WHITE), new Posicao(2,4)); // colocamos as pecas
		tabuleiro.placePeca(new Rei(tabuleiro,Cor.BLACK), new Posicao(1,4));
		tabuleiro.placePeca(new Rei(tabuleiro,Cor.WHITE), new Posicao(7,4));
	}
}
