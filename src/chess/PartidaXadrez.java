package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import board.Peca;
import board.Posicao;
import board.Tabuleiro;
import chess.pieces.Rei;
import chess.pieces.Torre;

public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	private boolean xequeMate;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	// criando tabuleiro
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
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

	public boolean getXeque() {
		return xeque;
	}

	public boolean getXequeMate() {
		return xequeMate;
	}

	//
	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}

	// m�todo para imprimir posi��es poss�veis a partir da posi��o de origem
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.paraPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}

	//
	public PecaXadrez movimentoPecaXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);

		if (testeCheque(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezException("Voc� n�o pode se colocar em cheque");
		}

		xeque = (testeCheque(oponente(jogadorAtual))) ? true : false; // testando a condi��o de cheque do jogador
																		// advers�rio
		if (testeChequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		} else {
			proximoTurno();
		}
		
		return (PecaXadrez) pecaCapturada;
	}

	// m�todo para alterar posi��es das pe�as no momento da captura, primeiro remove
	// uma pe�a da posi��o de origem para depois inseri-la na posi��o da pe�a
	// capturada, que tamb�m ser� removida da sua atual posi��o
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(origem);
		p.incrementarContagemMovimentos();
		Peca pecaCapturada = tabuleiro.removerPeca(origem);
		tabuleiro.colocarPeca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(destino);
		p.decrementarContagemMovimentos();
		tabuleiro.colocarPeca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.remove(pecaCapturada);
		}
	}

	// validando se existe alguma pe�a na posi��o dela de origem e tamb�m se existe
	// movimentos poss�veis para serem feitos
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.pecaExistente(posicao)) {
			throw new XadrezException("N�o existe pe�a na posi��o de origem");
		}
		if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A pe�a escolhida n�o � sua");
		}
		if (!tabuleiro.peca(posicao).existeMovimentoPossivel()) {
			throw new XadrezException("N�o existe movimentos poss�veis para a pe�a escolhida");
		}
	}

	// validando se a posi��o de destino est� apta para receber uma pe�a
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezException("A pe�a escolhida n�o pode se mover para a posi��o de destino");
		}
	}

	// definindo com qual jogador est� o turno atual
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO; // Caso o atual jogador do turno seja do
																				// Branco, alterar o pr�ximo para Preto.
																				// Caso n�o, alterar para Branco.
	}

	// definindo posi��o e pe�a a ser movimentada no tabuleiro
	private void colocandoNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private PecaXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("N�o existe o Rei da cor" + cor + "no tabuleiro");
	}

	private boolean testeCheque(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().paraPosicao();
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testeChequeMate(Cor cor) {
		if (!testeCheque(cor)) {
			return false;
		}
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++)
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrez) p).getPosicaoXadrez().paraPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testeCheque = testeCheque(cor); // testando se o Rei da cor ainda est� em cheque
						if (!testeCheque) {
							return false;
						}
					}
			}
		}
		return true;
	}

	// colocando as pe�as no tabuleiro
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
