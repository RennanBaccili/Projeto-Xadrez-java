package xadrez;

import jogodetabueiro.Posicao;

public class PosicaoXadrez { // sistema de prosição de xadrez
	
	private char coluna;
	private int linha;
	
	
	public PosicaoXadrez(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 0 || linha > 8) { // caso o usuario digite um valor maior que o tabuleiro
			throw new ExcecaoXadrez ("Erro ao estanciar posição de xadrez: valores validos [a1 até h8]");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

	
	//logica para inverter a linha e coluna
	protected Posicao xadPosicao() {  // posicao convertida para a posicao usada no xadrez
		return new Posicao(8 - linha, coluna - 'a');
	}
	
	protected static PosicaoXadrez dePosicao(Posicao posicao) { // invertemos a matriz para ler "linha primeiro" e depois coluna.
		return new PosicaoXadrez((char)('a' + posicao.getColuna()),8 - posicao.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha; 
	}
	
	

}
