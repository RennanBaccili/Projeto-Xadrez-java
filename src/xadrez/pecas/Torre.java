package xadrez.pecas;

import jogodetabueiro.Posicao;
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
	public boolean[][] possiveisMovimentos() {// nessa parte do código definimos todos os  valores que ela poderia andar com ofalso
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()]; // ele pega as matrizes do bauleiro
		
		Posicao p = new Posicao(0,0);
		
		//acima // no caso da torre definimos essas linhas como verdadeiro, ela se move pelas linhas verdadeiras
		
		p.setvalues(posicao.getLinha() - 1, posicao.getColuna()); //to ascessando a posicao da propria peça ela pode mover linha -1
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()]= true;
			p.setLinha(p.getLinha()-1);
		}
		if (getTabuleiro().posicaoExiste(p) && temPecaInimiga(p)) { // aqui ela verifica se a peça é inimiga
			mat[p.getLinha()][p.getColuna()] = true; // se for ela podera se movimentar para area inimiga
		}
		
		// esquerda
		p.setvalues(posicao.getLinha(), posicao.getColuna() -1); //to ascessando a posicao da propria peça ela pode mover linha -1
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna()-1); // movimento para a esquerda da coluna
		}
		if (getTabuleiro().posicaoExiste(p) && temPecaInimiga(p)) { // aqui ela verifica se a peça é inimiga
			mat[p.getLinha()][p.getColuna()] = true; // se for ela podera se movimentar para area inimiga
		}
		
		
		//direita
		
		
		p.setvalues(posicao.getLinha(), posicao.getColuna()+1); //to ascessando a posicao da propria peça ela pode mover linha -1
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()]= true;
			p.setColuna(p.getColuna()+1); 
		}
		if (getTabuleiro().posicaoExiste(p) && temPecaInimiga(p)) { // aqui ela verifica se a peça é inimiga
			mat[p.getLinha()][p.getColuna()] = true; // se for ela podera se movimentar para area inimiga
		}
		
		// abaixo
		p.setvalues(posicao.getLinha() + 1, posicao.getColuna()); //to ascessando a posicao da propria peça ela pode mover linha -1
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()]= true;
			p.setLinha(p.getLinha()+ 1);
		}
		if (getTabuleiro().posicaoExiste(p) && temPecaInimiga(p)) { // aqui ela verifica se a peça é inimiga
			mat[p.getLinha()][p.getColuna()] = true; // se for ela podera se movimentar para area inimiga
		}
		return mat;
	}
}
