package jogodetabueiro;

public abstract class Peca {
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
	public abstract boolean[][] possiveisMovimentos(); // 
	
	public boolean possivelMovimento(Posicao posicao) { //metodo concreto utilizando metodo abstrato
		return possiveisMovimentos() [posicao.getLinha()][posicao.getColuna()]; // huck metodo, ele faz um gancho com a sub classe
	} // metodo que depende de um metodo abstrato, só faz sentido quando um metodo concreto utiliza um abstrato
	
	public boolean temMovimentoPossivel() {
		boolean[][] mat = possiveisMovimentos();
		for (int i = 0; i < mat.length; i++) { // execulta varredura das matrizes 
			for(int j = 0; j < mat.length;j ++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	}

