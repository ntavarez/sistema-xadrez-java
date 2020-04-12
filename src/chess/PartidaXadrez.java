package chess;

import java.util.ArrayList;
import java.util.List;

import board.Peca;
import board.Posicao;
import board.Tabuleiro;
import chess.pieces.Rei;
import chess.pieces.Torre;

public class PartidaXadrez {
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	//criando tabuleiro
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		setupInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	//
	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++ ) {
			for (int j=0; j<tabuleiro.getColunas(); j++ ) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	//m�todo para imprimir posi��es poss�veis a partir da posi��o de origem
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem){
		Posicao posicao = posicaoOrigem.paraPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	//
	public PecaXadrez movimentoPecaXadrez (PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);
		proximoTurno();
		return (PecaXadrez) pecaCapturada;
	}
	
	//m�todo para alterar posi��es das pe�as no momento da captura, primeiro remove uma pe�a da posi��o de origem para depois inseri-la na posi��o da pe�a capturada, que tamb�m ser� removida da sua atual posi��o
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(origem);
		tabuleiro.colocarPeca(p, destino);
		
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecasCapturadas);
			pecasCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;
	}
	
	//validando se existe alguma pe�a na posi��o dela de origem e tamb�m se existe movimentos poss�veis para serem feitos
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.pecaExistente(posicao)) {
			throw new XadrezException("N�o existe pe�a na posi��o de origem");
		}
		if (jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A pe�a escolhida n�o � sua");
		}
		if(!tabuleiro.peca(posicao).existeMovimentoPossivel()) {
			throw new XadrezException("N�o existe movimentos poss�veis para a pe�a escolhida");
		}
	}
	
	//validando se a posi��o de destino est� apta para receber uma pe�a
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezException("A pe�a escolhida n�o pode se mover para a posi��o de destino");
		}
	}
	
	//definindo com qual jogador est� o turno atual
	private void proximoTurno() {
		turno ++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO; //Caso o atual jogador do turno seja do Branco, alterar o pr�ximo para Preto. Caso n�o, alterar para Branco. 
	}
	
	//definindo posi��o e pe�a a ser movimentada no tabuleiro
	private void colocandoNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);
	}
	
	//colocando as pe�as no tabuleiro
	private void setupInicial() {
		
		colocandoNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocandoNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
		colocandoNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
		colocandoNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
		colocandoNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocandoNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

		colocandoNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
		colocandoNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
		colocandoNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
		colocandoNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
		colocandoNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
		colocandoNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));
	}

}
