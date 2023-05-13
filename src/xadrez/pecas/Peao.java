package xadrez.pecas;

import jogodetabueiro.Posicao;
import jogodetabueiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()]; // ele pega as matrizes do bauleiro
		
		Posicao p = new Posicao(0,0);
		
		//peão branco
		if (getCor() == Cor.WHITE) {// se a pega for branca
			p.setvalues(posicao.getLinha() -1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setvalues(posicao.getLinha() -2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() -1, posicao.getColuna()); // regra do peão ele pode se mover 2 casa somente se tiver livre
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p) && // testa se existe e testa se ta vazia
				getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temPeca(p2) && getMoveCont() == 0) {// testa se existe e testa se ta vazia
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setvalues(posicao.getLinha() -1, posicao.getColuna()-1);
			if(getTabuleiro().posicaoExiste(p) && temPecaInimiga(p)) { // testo se existe// testo se tem peca inimiga
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setvalues(posicao.getLinha() -1, posicao.getColuna()+1);
			if(getTabuleiro().posicaoExiste(p) && temPecaInimiga(p)) { // testo se existe// testo se tem peca inimiga
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		if(getCor()== Cor.BLACK) {
			p.setvalues(posicao.getLinha() +1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setvalues(posicao.getLinha() +2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() +1, posicao.getColuna()); // regra do peão ele pode se mover 2 casa somente se tiver livre
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p) && // testa se existe e testa se ta vazia
				getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temPeca(p2) && getMoveCont() == 0) {// testa se existe e testa se ta vazia
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setvalues(posicao.getLinha() +1, posicao.getColuna()-1);
			if(getTabuleiro().posicaoExiste(p) && temPecaInimiga(p)) { // testo se existe// testo se tem peca inimiga
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setvalues(posicao.getLinha() +1, posicao.getColuna()+1);
			if(getTabuleiro().posicaoExiste(p) && temPecaInimiga(p)) { // testo se existe// testo se tem peca inimiga
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
		}	
		return mat;
	}
	@Override
	public String toString() {
		return "P"; //P peao
	}

}
