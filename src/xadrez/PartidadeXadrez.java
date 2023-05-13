package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogodetabueiro.Peca;
import jogodetabueiro.Posicao;
import jogodetabueiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidadeXadrez {

	private int turno;
	private Cor vezdoJogador;
	private Tabuleiro tabuleiro;
	private boolean check; //por padrão começa com false
	private boolean checkMate;
	
	private List<Peca> pecanoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidadeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);// classe que determina o tamanho de um tabuleiro
		turno = 1; // JOGO COMEÇA NO PRIMEIRO TURNO
		vezdoJogador =Cor.WHITE; // Quem começa é as brancas por padrão
		inicialSetup();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getVezdoJogador() {	
		return vezdoJogador;
	}
	
	public boolean getCheck() {
		return check;
	}
	public boolean getCheckMate() {
		return checkMate;
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
	
	public boolean[][] movimentoPossivel(PosicaoXadrez pinicialPosicao)	{
		Posicao posicao =  pinicialPosicao.xadPosicao();
		validacaoPeca(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
		
		
	}
	
	//movimento de peca
	
	public PecaXadrez exeMoverPeca(PosicaoXadrez posicaoInicial,PosicaoXadrez posicaoFinal) {
		Posicao pinicial = posicaoInicial.xadPosicao(); // aqui estanciamos posicao inicial
		Posicao pfinal = posicaoFinal.xadPosicao(); // e posicao final
		validacaoPeca(pinicial);/* essa operação determina se existe uma peça no local indicado, se não tiver peça a ser movida
		 o programa lança uma exception */
		validacaopFinal(pinicial,pfinal);
		Peca capturaPeca  = moverPeca(pinicial, pfinal); // operacao responsavel por movimentar peca
		
		if(testeCheck(vezdoJogador)) {
			desfazerMovimento(pinicial, pfinal, capturaPeca);
			throw new ExcecaoXadrez("Você não pode se colocar em check");
		}
		
		check = (testeCheck(oponente(vezdoJogador))) ? true : false; // teste check do oponente
		if(TesteCheckMate(oponente(vezdoJogador))) {
			checkMate = true;
		}
		else {
			trocaTurno(); // após jogadas, troca o turno
		}
		return (PecaXadrez)capturaPeca; //  ele retorna a peca capturada, conceito de downcast
		
	}
	
	private Peca moverPeca(Posicao pinicial, Posicao pfinal) { // logica de realizar um movimento
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(pinicial); // a peca vai ser removida da posicao inicial e movida para a final
		p.somaMove(); // contador
		Peca capturaPeca = tabuleiro.removePeca(pfinal); 
		tabuleiro.placePeca(p, pfinal); // peça p para o local final
		
		if (capturaPeca != null) {
			pecanoTabuleiro.remove(capturaPeca);
			pecasCapturadas.add((Peca) capturaPeca);
			
		}
		return capturaPeca;
	}
	
	private void desfazerMovimento(Posicao pinicial, Posicao pfinal, Peca capturaPeca) { 
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(pfinal);
	    p.decliveMove();// contador
	    tabuleiro.placePeca(p, pinicial);
	    if(capturaPeca != null) {
	        tabuleiro.placePeca(capturaPeca, pfinal);
	        pecasCapturadas.remove(capturaPeca);
	        pecanoTabuleiro.add(capturaPeca);
	    }
	}	
	
	private void validacaoPeca(Posicao posicao) {
		if (!tabuleiro.temPeca(posicao)) { // se nã́o tiver peca para ser movida emprime o erro abaixo
			throw new ExcecaoXadrez("Não existe peça na posição de origem");
		}
		if(vezdoJogador != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {//transformei o tabuleiro em PecaXadrez
			throw new ExcecaoXadrez("A peça escolhida não é sua ");
		}
		if(!tabuleiro.peca(posicao).temMovimentoPossivel()) {
			throw new ExcecaoXadrez("Não existe movimentos possiveis para a peça escolhida");
		}
	}
	
	private void validacaopFinal(Posicao pinicial, Posicao pfinal) {
		if(!tabuleiro.peca(pinicial).possivelMovimento(pfinal)){
			throw new ExcecaoXadrez("A peça não pode se mover para posição de destino");
		}
	}
	
	private void trocaTurno() {
		turno++;
		vezdoJogador = (vezdoJogador == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}
	
	// sistema de check
	private Cor oponente(Cor cor) {
		return (cor == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}
	
	private PecaXadrez Rei(Cor cor) {
		List<Peca> list = pecanoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for(Peca p:list) {
			if (p instanceof Rei) {
				return (PecaXadrez)p;
			}
		}
		throw new IllegalStateException("Não tem o Rei " + cor + " no tabuleiro");
	}
	
	private  boolean testeCheck(Cor cor) {
		Posicao ReiPosicao = Rei(cor).getPosicaoXadrez().xadPosicao(); 
		List<Peca> pecaOponente = pecanoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for(Peca p: pecaOponente) { 
			boolean[][] mat = p.possiveisMovimentos(); // uma varredura das peças do oponente
			if(mat[ReiPosicao.getLinha()][ReiPosicao.getColuna()]) { // se for verdadeiro o Rei está em check
				return true;
			}
		}
		return false;
	}
	
	private boolean TesteCheckMate(Cor cor) {
		if(!testeCheck(cor)) {
			return false;
		}
		List<Peca> list = pecanoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		// vou pegar todas as peças do jogador e ver se ela tem movimentos possiveis para defender o rei
		for (Peca p:list) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i = 0; i<tabuleiro.getLinhas();i++) {
				for(int j = 0; j<tabuleiro.getColunas();j++) {
					if(mat[i][j]) { // testar se o movimento possivel tira do check
						Posicao pinicial = ((PecaXadrez)p).getPosicaoXadrez().xadPosicao(); // pego posicao da peça
						Posicao pfinal = new Posicao(i,j);
						Peca capturaPeca = moverPeca(pinicial, pfinal); // eu movimento a peça
						boolean testeCheck = testeCheck(cor); // ele testa se o Rei ainda está em check
						desfazerMovimento(pinicial, pfinal, capturaPeca);
						if (!testeCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	//instanciacao de peca dentro do tabuleiro
	private void instanciePecaXadrez(char coluna, int linha, PecaXadrez peca){
		tabuleiro.placePeca(peca, new PosicaoXadrez(coluna, linha).xadPosicao());
		pecanoTabuleiro.add(peca);
	}
	
	private void inicialSetup() { // funcao que faz inicializacao da partida de xadrez
		instanciePecaXadrez('c',1, new Torre(tabuleiro,Cor.WHITE)); // colocamos as pecas
		instanciePecaXadrez('c',2, new Torre(tabuleiro,Cor.WHITE));
		instanciePecaXadrez('e',2, new Torre(tabuleiro,Cor.WHITE));
		instanciePecaXadrez('e',1, new Torre(tabuleiro,Cor.WHITE));
		instanciePecaXadrez('d',2, new Torre(tabuleiro,Cor.WHITE));
		instanciePecaXadrez('d',1, new Rei(tabuleiro,Cor.WHITE));
		
		instanciePecaXadrez('c',7, new Torre(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('c',8, new Torre(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('d',7, new Torre(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('e',7, new Torre(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('e',8, new Torre(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('d',8, new Rei(tabuleiro,Cor.BLACK));
	}
}
