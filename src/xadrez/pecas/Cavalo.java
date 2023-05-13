package xadrez.pecas;

import jogodetabueiro.Posicao;
import jogodetabueiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Cavalo extends PecaXadrez{

	public Cavalo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "C"; 
	}

	private boolean podeMover(Posicao posicao){//metodo vai dizer se o Rei pode mover para determinada posicao
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor(); // a peca tem que existir, e ela tem que ser diferente do adversario
	}						//preto diferente de branco
	
	@Override
	public boolean[][] possiveisMovimentos() {// nessa parte do código definimos todos os  valores que ela poderia andar com ofalso
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()]; // ele pega as matrizes do bauleiro
		
		Posicao p = new Posicao(0,0);
		//acima
		
		p.setvalues(posicao.getLinha()-1,posicao.getColuna() -2); // setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//abaixo
		p.setvalues(posicao.getLinha()-2,posicao.getColuna()-1);// setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//esquerda
		p.setvalues(posicao.getLinha()-2,posicao.getColuna()+1);// setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//direita
		p.setvalues(posicao.getLinha()-1,posicao.getColuna()+2); // setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
			
		//noroeste
		p.setvalues(posicao.getLinha()+1,posicao.getColuna()+2); // setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}
			
		//ne
		p.setvalues(posicao.getLinha()+2,posicao.getColuna()+1);// setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//su
		p.setvalues(posicao.getLinha()+2,posicao.getColuna()-1);  // setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//sudeste
		p.setvalues(posicao.getLinha()+1,posicao.getColuna()-2); // setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}
	}
		
		return mat;
	}
}


/*p
	
	
			
		
	
			
		
		p.setvalues(posicao.getLinha()+2,posicao.getColuna()+1); // setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setvalues(posicao.getLinha()+2,posicao.getColuna()-1); // setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setvalues(posicao.getLinha()+1,posicao.getColuna()-2); // setamos os valores de posicao acima
		if(getTabuleiro().posicaoExiste(p)&& podeMover(p)) { // se 
			mat[p.getLinha()][p.getColuna()] = true;
		}*/
