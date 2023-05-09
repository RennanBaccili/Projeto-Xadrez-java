package xadrez;

import jogodetabueiro.Peca;
import jogodetabueiro.Tabuleiro;

public class PecaXadrez extends Peca{
	
	private Cor cor;
	
	public PecaXadrez(Tabuleiro tabuleiro,Cor cor) {
		super(tabuleiro);
		this.cor=cor;
	}

	public Cor getCor() {
		return cor;
	}


}
