package jogodetabueiro;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		if(linhas < 1|| colunas <1) {
			throw new ExcecaoTabuleiro("Erro ao criar o tabuleiro: é necessario existir pelos menos uma linhas e uma colunas ");
		}
		
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas]; 
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public Peca peca(int linha,int coluna) {// metodo piece para retornar a peça
		if(!posicaoExiste(linha,coluna)) {
			throw new ExcecaoTabuleiro("Posição não está do tabuleiro");
		}
		
		return pecas[linha][coluna];
	}
	public Peca peca(Posicao posicao) {// metodo piece para retornar a peça
		if(!posicaoExiste(posicao)) { 
			throw new ExcecaoTabuleiro("Posição não está do tabuleiro");// valor falso
		}
		
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}	
	
	public void placePeca(Peca peca, Posicao posicao) {
		if(temPeca(posicao)) {
			throw new ExcecaoTabuleiro("Ja existe uma peça nessa posicao " + posicao);
		}
		
		pecas[posicao.getLinha()][posicao.getColuna()] = peca; // to pegando a matriz criada na posicao dada
		// e atribuir a ela a posicao dada
		peca.posicao = posicao;
	}
	
	// remover peça, se não tiver peca na posicao reotrn null
	public Peca removePeca(Posicao posicao) {
		if(!posicaoExiste(posicao)) {
			throw new ExcecaoTabuleiro("Posição não está do tabuleiro");
		}
		if (peca(posicao) == null) {
			return null;
		}
		Peca aux = peca(posicao); // pega a Peça tem posição  <---
		aux.posicao = null; // essa peça vai ser retirada do tabuleiro <--- (null)
		pecas[posicao.getLinha()][posicao.getColuna()] = null; // messa matriz de pecas aonde indico que tirei uma peca
		//tera valor null, siginifica que não tem mais peça nessa matriz
		return aux;// vai retornar a peça que foi retirada
	}
	
	private boolean posicaoExiste(int linha, int coluna) { // metodo auxiliar
		return linha >=0 && linha < linhas && coluna >=0 && coluna < colunas;
		// para ela existir ela tem que ser maior ou igual a zero e tem que ser menor que o numero de colunas total
	}
	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste( posicao.getLinha(),posicao.getColuna());
	}
	
	public boolean temPeca(Posicao posicao) { // se o resultado for diferente de null é pq tem peca na posicao
		if(!posicaoExiste(posicao)) {
			throw new ExcecaoTabuleiro("Posição não está do tabuleiro");
		}
		return peca(posicao) != null;
	}
}
