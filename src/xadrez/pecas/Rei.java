package xadrez.pecas;

import jogodetabueiro.Posicao;
import jogodetabueiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "R"; //T de torre ou R de rook
	}

	@Override
	public boolean[][] possiveisMovimentos() {// nessa parte do código definimos todos os  valores que ela poderia andar com ofalso
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()]; // ele pega as matrizes do bauleiro
		
				return mat;
	}
}
