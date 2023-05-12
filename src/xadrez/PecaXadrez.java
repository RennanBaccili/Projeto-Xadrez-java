package xadrez;

import jogodetabueiro.Peca;
import jogodetabueiro.Posicao;
import jogodetabueiro.Tabuleiro;

public abstract class PecaXadrez extends Peca{
	
	private Cor cor;
	
	public PecaXadrez(Tabuleiro tabuleiro,Cor cor) {
		super(tabuleiro);
		this.cor=cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return  PosicaoXadrez.dePosicao(posicao); // converto a posição da peça para dePosicao
	}
	
	protected boolean temPecaInimiga(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao); // variavel p vai receber a peca em determinada posicao
		return p != null && p.getCor() != cor; // aquii eui testo se aposicao é adversaria
	}

}
