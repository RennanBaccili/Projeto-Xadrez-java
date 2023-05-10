package xadrez;

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
	
	//instanciacao de peca dentro do tabuleiro
	private void instanciePecaXadrez(char coluna, int linha, PecaXadrez peca){
		tabuleiro.placePeca(peca, new PosicaoXadrez(coluna, linha).xadPosicao());
	}
	
	private void inicialSetup() { // funcao que faz inicializacao da partida de xadrez
		instanciePecaXadrez('c',1, new Torre(tabuleiro,Cor.WHITE)); // colocamos as pecas
		instanciePecaXadrez('c',2, new Rei(tabuleiro,Cor.WHITE));
		instanciePecaXadrez('e',2, new Rei(tabuleiro,Cor.WHITE));
		instanciePecaXadrez('e',1, new Rei(tabuleiro,Cor.WHITE));
		instanciePecaXadrez('d',1, new Rei(tabuleiro,Cor.WHITE));
		
		instanciePecaXadrez('c',7, new Rei(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('c',8, new Rei(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('d',7, new Rei(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('e',7, new Rei(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('e',8, new Rei(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('d',8, new Rei(tabuleiro,Cor.BLACK));
	}
}
