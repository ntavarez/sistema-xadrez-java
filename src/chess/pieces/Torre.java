package chess.pieces;

import board.Posicao;
import board.Tabuleiro;
import chess.Cor;
import chess.PecaXadrez;

public class Torre extends PecaXadrez {

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "T";
	}

	// definindo movimentos possíveis da Torre (somente em linha reta, quantas casas
	// quiser, não ultrapassando uma peça aliada)
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// verificar caminho acima da peça
		p.setValor(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaExistente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && existeUmaPecaInimiga(p)) { // verificando se existe uma peça
																				// adversária na posição informada
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// verificar caminho à esquerda da peça
		p.setValor(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaExistente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && existeUmaPecaInimiga(p)) { // verificando se existe uma peça
																				// adversária na posição informada
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// verificar caminho à direita da peça
		p.setValor(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaExistente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && existeUmaPecaInimiga(p)) { // verificando se existe uma peça
																				// adversária na posição informada
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// verificar caminho abaixo da peça
		p.setValor(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaExistente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && existeUmaPecaInimiga(p)) { // verificando se existe uma peça
																				// adversária na posição informada
			mat[p.getLinha()][p.getColuna()] = true;
		}
		return mat;
	}
}
