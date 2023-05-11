package xadrez.pecas;

import jogodetabueiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Torre extends PecaXadrez{ // tambem chamada de Rook

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor); // repassou a chamada para a sub class torre
	}

	@Override
	public String toString() {
		return "T"; //T de torre ou R de rook
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()]; // ele pega as matrizes do bauleiro
		return mat;
	}
}
