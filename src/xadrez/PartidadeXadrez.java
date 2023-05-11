package xadrez;

import jogodetabueiro.Peca;
import jogodetabueiro.Posicao;
import jogodetabueiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidadeXadrez {

	private Tabuleiro tabuleiro;

	public PartidadeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);// classe que determina o tamanho de um tabuleiro
		inicialSetup();
	}
	
	public PecaXadrez[][] Getpecas() {
//pelo desevenvolvimente em camadas, o programa deve enxergar apenas pecaXadrez, e nao a Peça interna que  esta na parte acima
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for(int i=0; i < tabuleiro.getLinhas();i++) {
			for(int j=0;j < tabuleiro.getColunas();j++) {
				mat[i][j] = (PecaXadrez)tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	//movimento de peca
	
	public PecaXadrez exeMoverPeca(PosicaoXadrez posicaoInicial,PosicaoXadrez posicaoFinal) {
		Posicao pinicial = posicaoInicial.xadPosicao(); // aqui estanciamos posicao inicial
		Posicao pfinal = posicaoFinal.xadPosicao(); // e posicao final
		validacaoPeca(pinicial); /* essa operação determina se existe uma peça no local indicado, se não tiver peça a ser movida
		 o programa lança uma exception */
		Peca capturaPeca = moverPeca(pinicial, pfinal); // operacao responsavel por movimentar peca
		return (PecaXadrez)capturaPeca; //  ele retorna a peca capturada, conceito de downcast
	}
	
	private Peca moverPeca(Posicao pinicial, Posicao pfinal) { // logica de realizar um movimento
		Peca p = tabuleiro.removePeca(pinicial); // a peca vai ser removida da posicao inicial e movida para a final
		Peca capturaPeca = tabuleiro.removePeca(pfinal); 
		tabuleiro.placePeca(p, pfinal); // peça p para o local final
		return capturaPeca;
	}
	
	
	private void validacaoPeca(Posicao posicao) {
		if (!tabuleiro.temPeca(posicao)) { // se nã́o tiver peca para ser movida emprime o erro abaixo
			throw new ExcecaoXadrez("Não existe peça na posição de origem");
		}
		if(!tabuleiro.peca(posicao).temMovimentoPossivel()) {
			throw new ExcecaoXadrez("Não existe movimentos possiveis para a peça escolhida");
		}
	}
	
	//instanciacao de peca dentro do tabuleiro
	private void instanciePecaXadrez(char coluna, int linha, PecaXadrez peca){
		tabuleiro.placePeca(peca, new PosicaoXadrez(coluna, linha).xadPosicao());
	}
	
	private void inicialSetup() { // funcao que faz inicializacao da partida de xadrez
		instanciePecaXadrez('c',1, new Torre(tabuleiro,Cor.WHITE)); // colocamos as pecas
		instanciePecaXadrez('c',2, new Rei(tabuleiro,Cor.WHITE));
		instanciePecaXadrez('e',2, new Rei(tabuleiro,Cor.WHITE));
		instanciePecaXadrez('e',1, new Rei(tabuleiro,Cor.WHITE));
		instanciePecaXadrez('d',1, new Rei(tabuleiro,Cor.WHITE));
		
		instanciePecaXadrez('c',7, new Rei(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('c',8, new Rei(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('d',7, new Rei(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('e',7, new Rei(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('e',8, new Rei(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('d',8, new Rei(tabuleiro,Cor.BLACK));
	}
}
