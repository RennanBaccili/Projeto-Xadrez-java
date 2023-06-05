package xadrez.pecas;

import jogodetabueiro.Posicao;
import jogodetabueiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidadeXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{
	
	private PartidadeXadrez partidadeXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor,PartidadeXadrez partidadeXadrez) {
		super(tabuleiro, cor);
		this.partidadeXadrez = partidadeXadrez;
		// TODO Auto-generated constructor stub
	}

	private boolean podeMover(Posicao posicao){//metodo vai dizer se o Rei pode mover para determinada posicao
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor(); // a peca tem que existir, e ela tem que ser diferente do adversario
	}						//preto diferente de branco
	// metodo auxiliar para ajudar a testar o movimento de rook
	private boolean testeTorreCastling(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao); // seleciono a peça informada
		return p != null && p instanceof Torre &&  p.getCor() == getCor() && p.getMoveCont() == 0; // testo movimentos da torre
	}
	
	
	@Override
	public boolean[][] possiveisMovimentos() {// nessa parte do código definimos todos os  valores que ela poderia andar com o falso
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()]; // ele pega as matrizes do bauleiro
		
		Posicao p = new Posicao(0,0);
		//acima
		
		p.setvalues(posicao.getLinha()-1,posicao.getColuna()); // setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//abaixo
		p.setvalues(posicao.getLinha()+1,posicao.getColuna()); // setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//esquerda
		p.setvalues(posicao.getLinha(),posicao.getColuna()-1); // setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//direita
		p.setvalues(posicao.getLinha(),posicao.getColuna()+1); // setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		} //
		//noroeste
		p.setvalues(posicao.getLinha()-1,posicao.getColuna()-1); // setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}
			
		//ne
		p.setvalues(posicao.getLinha()-1,posicao.getColuna()+1); // setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//su
		p.setvalues(posicao.getLinha()+1,posicao.getColuna()-1); // setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//sudeste
		p.setvalues(posicao.getLinha()+1,posicao.getColuna()+1); // setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Movimento especial ROque
		if(getMoveCont() == 0 && !partidadeXadrez.getCheck()){
			//Roque pequeno
			Posicao post1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
			if(testeTorreCastling(post1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
				if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true; // permite ao rei
				}
			}
			//se no tabulei na posicao 1 não tem peça la e no tabuleiro na peca 2  for igual null
			
			Posicao post2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 4);
			if(testeTorreCastling(post2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
				if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) ==null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
		}
			
		return mat;
	}

	@Override
	public String toString() {
		return "R"; //T de torre ou R de rook
	}
}
