package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogodetabueiro.Peca;
import jogodetabueiro.Posicao;
import jogodetabueiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidadeXadrez {

	private int turno;
	private Cor vezdoJogador;
	private Tabuleiro tabuleiro;
	private boolean check; //por padrão começa com false
	private boolean checkMate;
	private PecaXadrez enPassantVulnerable;
	private PecaXadrez promocao;
	
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
	public PecaXadrez getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	public PecaXadrez getPromocao() {
		return promocao;
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
		
		PecaXadrez movedPeca = (PecaXadrez)tabuleiro.peca(pfinal); //////
		
		//# Special Move promotion
		promocao = null;
		if(movedPeca instanceof Peao) {
			if((movedPeca.getCor() == Cor.WHITE && pfinal.getLinha() ==0 ) || (movedPeca.getCor() == Cor.BLACK && pfinal.getLinha() == 7 )) {
				promocao = (PecaXadrez)tabuleiro.peca(pfinal);
				promocao = replacePromocaoPeca("Q");
			}
		}
		
		check = (testeCheck(oponente(vezdoJogador))) ? true : false; // teste check do oponente
		if(TesteCheckMate(oponente(vezdoJogador))) {
			checkMate = true;
		}
		else {
			trocaTurno(); // após jogadas, troca o turno
		}
		
		// # special move enpassant
		if(movedPeca instanceof Peao && (pfinal.getLinha() == pinicial.getLinha() -2 || pfinal.getLinha() == pinicial.getLinha() +2)) {
			enPassantVulnerable = movedPeca;
		}
		else {
			enPassantVulnerable = null;
		}
		
		return (PecaXadrez)capturaPeca; //  ele retorna a peca capturada, conceito de downcast
	}
	public PecaXadrez replacePromocaoPeca(String type) {
		if(promocao==null) {
			throw new IllegalStateException("Não existe peça na posicao para a promoção");
		}
		if(!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")) {
			throw new InvalidParameterException("Tipo indisponivel para promoção");
		}
		
		Posicao pos = promocao.getPosicaoXadrez().xadPosicao();
		Peca p = tabuleiro.peca(pos);
		pecanoTabuleiro.remove(p);
		
		PecaXadrez newPeca = newPeca(type, promocao.getCor());
		tabuleiro.placePeca(newPeca, pos);
		pecanoTabuleiro.add(newPeca);
		
		return newPeca;
	}
	
	private PecaXadrez newPeca(String tipo,Cor cor) {
		if(tipo.equals("B")) return new Bispo(tabuleiro, cor);
		if(tipo.equals("C")) return new Cavalo(tabuleiro, cor);
		if(tipo.equals("Q")) return new Rainha(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
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
		// # movimento especial rook pequeno
		if(p instanceof Rei && pfinal.getColuna() == pinicial.getColuna()+2) {
			Posicao pinicialT = new Posicao(pinicial.getLinha(),pinicial.getColuna()+3);
			Posicao pfinalT = new Posicao(pinicial.getLinha(),pinicial.getColuna()+1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(pinicialT);
			tabuleiro.placePeca(torre, pfinalT);
			torre.somaMove();
		}
		// # movimento especial rook grande
		if(p instanceof Rei && pfinal.getColuna() == pinicial.getColuna()-2) {
			Posicao pinicialT = new Posicao(pinicial.getLinha(),pinicial.getColuna()-4);
			Posicao pfinalT = new Posicao(pinicial.getLinha(),pinicial.getColuna()-1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(pinicialT);
			tabuleiro.placePeca(torre, pfinalT);
			torre.somaMove();
		}
		
		if(p instanceof Peao) {
			if(pinicial.getColuna() != pfinal.getColuna() && capturaPeca == null) {
				Posicao peaoPosicao;
				if(p.getCor() == Cor.WHITE) {
					peaoPosicao = new Posicao(pfinal.getLinha()+1, pfinal.getLinha());
				}
				else {
					peaoPosicao = new Posicao(pfinal.getLinha()-1, pfinal.getLinha());
				}
				capturaPeca = tabuleiro.removePeca(peaoPosicao);
				pecasCapturadas.add(capturaPeca);
				pecanoTabuleiro.remove(capturaPeca);
			}
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
	
	 // # movimento especial rook pequeno
		if(p instanceof Rei && pfinal.getColuna() == pinicial.getColuna()+2) {
			Posicao pinicialT = new Posicao(pinicial.getLinha(),pinicial.getColuna()+3);
			Posicao pfinalT = new Posicao(pinicial.getLinha(),pinicial.getColuna()+1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(pfinalT);
			tabuleiro.placePeca(torre, pinicialT);
			torre.decliveMove();
		}
		// # movimento especial rook grande
		if(p instanceof Rei && pfinal.getColuna() == pinicial.getColuna()-2) {
			Posicao pinicialT = new Posicao(pinicial.getLinha(),pinicial.getColuna()-4);
			Posicao pfinalT = new Posicao(pinicial.getLinha(),pinicial.getColuna()-1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(pfinalT);
			tabuleiro.placePeca(torre, pinicialT);
			torre.decliveMove();
	 		}
		
		if(p instanceof Peao) {
			if(pinicial.getColuna() != pfinal.getColuna() && capturaPeca == enPassantVulnerable) {
				PecaXadrez pawn = (PecaXadrez)tabuleiro.removePeca(pfinal);
				Posicao peaoPosicao;
				if(p.getCor() == Cor.WHITE) {
					peaoPosicao = new Posicao(3, pfinal.getLinha());
				}
				else {
					peaoPosicao = new Posicao(4, pfinal.getLinha());
				}
				tabuleiro.placePeca(pawn, peaoPosicao);
				capturaPeca = tabuleiro.removePeca(peaoPosicao);
			}
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
		instanciePecaXadrez('a',2, new Peao(tabuleiro,Cor.WHITE, this)); // colocamos as pecas
		instanciePecaXadrez('b',2, new Peao(tabuleiro,Cor.WHITE,this));
		instanciePecaXadrez('b',1, new Cavalo(tabuleiro,Cor.WHITE));
		instanciePecaXadrez('c',2, new Peao(tabuleiro,Cor.WHITE,this));
		instanciePecaXadrez('d',1, new Rei(tabuleiro,Cor.WHITE, this));
		instanciePecaXadrez('d',2, new Peao(tabuleiro,Cor.WHITE,this));
		instanciePecaXadrez('e',2, new Peao(tabuleiro,Cor.WHITE,this));
		instanciePecaXadrez('f',2, new Peao(tabuleiro,Cor.WHITE,this));
		instanciePecaXadrez('g',2, new Peao(tabuleiro,Cor.WHITE,this));
		instanciePecaXadrez('g',1, new Cavalo(tabuleiro,Cor.WHITE));
		instanciePecaXadrez('h',2, new Peao(tabuleiro,Cor.WHITE,this));
		instanciePecaXadrez('a',1, new Torre(tabuleiro,Cor.WHITE));
		instanciePecaXadrez('h',1, new Torre(tabuleiro,Cor.WHITE));
		instanciePecaXadrez('c',1, new Bispo(tabuleiro,Cor.WHITE));
		instanciePecaXadrez('f',1, new Bispo(tabuleiro,Cor.WHITE));

		instanciePecaXadrez('e',1, new Rainha(tabuleiro,Cor.WHITE));


		
		instanciePecaXadrez('a',7, new Peao(tabuleiro,Cor.BLACK,this)); // colocamos as pecas
		instanciePecaXadrez('b',7, new Peao(tabuleiro,Cor.BLACK,this));
		instanciePecaXadrez('c',7, new Peao(tabuleiro,Cor.BLACK,this));
		instanciePecaXadrez('d',8, new Rei(tabuleiro,Cor.BLACK, this));
		instanciePecaXadrez('d',7, new Peao(tabuleiro,Cor.BLACK,this));
		instanciePecaXadrez('e',7, new Peao(tabuleiro,Cor.BLACK,this));
		instanciePecaXadrez('f',7, new Peao(tabuleiro,Cor.BLACK,this));
		instanciePecaXadrez('g',7, new Peao(tabuleiro,Cor.BLACK,this));
		instanciePecaXadrez('h',7, new Peao(tabuleiro,Cor.BLACK,this));
		instanciePecaXadrez('a',8, new Torre(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('h',8, new Torre(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('c',8, new Bispo(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('f',8, new Bispo(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('b',8, new Cavalo(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('g',8, new Cavalo(tabuleiro,Cor.BLACK));
		instanciePecaXadrez('e',8, new Rainha(tabuleiro,Cor.BLACK));
	}
}
