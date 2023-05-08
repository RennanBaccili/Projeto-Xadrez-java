package jogodetabueiro;

public class Peca {
	protected Posicao posicao;
	private Tabuleiro tabuleiro;

	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null; 
	}

	public Posicao getPosicao() { 
		return posicao;
	}
	
	protected Tabuleiro getTabuleiro() {// somente classes dentro desse pacote poderão acessar o tabuleiro de uma peça
		return tabuleiro; // e tambem as subclasses
	}
}
