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
	
	//método para imprimir posições possíveis a partir da posição de origem
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
	
	//método para alterar posições das peças no momento da captura, primeiro remove uma peça da posição de origem para depois inseri-la na posição da peça capturada, que também será removida da sua atual posição
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
	
	//validando se existe alguma peça na posição dela de origem e também se existe movimentos possíveis para serem feitos
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.pecaExistente(posicao)) {
			throw new XadrezException("Não existe peça na posição de origem");
		}
		if (jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peça escolhida não é sua");
		}
		if(!tabuleiro.peca(posicao).existeMovimentoPossivel()) {
			throw new XadrezException("Não existe movimentos possíveis para a peça escolhida");
		}
	}
	
	//validando se a posição de destino está apta para receber uma peça
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezException("A peça escolhida não pode se mover para a posição de destino");
		}
	}
	
	//definindo com qual jogador está o turno atual
	private void proximoTurno() {
		turno ++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO; //Caso o atual jogador do turno seja do Branco, alterar o próximo para Preto. Caso não, alterar para Branco. 
	}
	
	//definindo posição e peça a ser movimentada no tabuleiro
	private void colocandoNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);
	}
	
	//colocando as peças no tabuleiro
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
