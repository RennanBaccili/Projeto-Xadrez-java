package xadrez.pecas;

import jogodetabueiro.Posicao;
import jogodetabueiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidadeXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {
	
	private PartidadeXadrez partidadeXadrez;
	
	public Peao(Tabuleiro tabuleiro, Cor cor, PartidadeXadrez partidadeXadrez) {
		super(tabuleiro, cor);
		this.partidadeXadrez = partidadeXadrez;
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
			//Especial move enPassant
			if(posicao.getLinha() == 3) {
				Posicao left = new Posicao(posicao.getLinha(), posicao.getColuna() -1);
				if(getTabuleiro().posicaoExiste(left) && temPecaInimiga(left) && getTabuleiro().peca(left) == partidadeXadrez.getEnPassantVulnerable()) {
					mat[left.getLinha()-1][left.getColuna()] = true;
				}
				Posicao right = new Posicao(posicao.getLinha(), posicao.getColuna() +1);
				if(getTabuleiro().posicaoExiste(right) && temPecaInimiga(right) && getTabuleiro().peca(right) == partidadeXadrez.getEnPassantVulnerable()) {
					mat[right.getLinha()-1][right.getColuna()] = true;
				}
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
			//Especial move enPassant
			if(posicao.getLinha() == 4) {
				Posicao left = new Posicao(posicao.getLinha(), posicao.getColuna() -1);
				if(getTabuleiro().posicaoExiste(left) && temPecaInimiga(left) && getTabuleiro().peca(left) == partidadeXadrez.getEnPassantVulnerable()) {
					mat[left.getLinha()+1][left.getColuna()] = true;
				}
				Posicao right = new Posicao(posicao.getLinha(), posicao.getColuna() +1);
				if(getTabuleiro().posicaoExiste(right) && temPecaInimiga(right) && getTabuleiro().peca(right) == partidadeXadrez.getEnPassantVulnerable()) {
					mat[right.getLinha()+1][right.getColuna()] = true;
				}
			}
		}	
		return mat;
	}
	@Override
	public String toString() {
		return "P"; //P peao
	}

}
