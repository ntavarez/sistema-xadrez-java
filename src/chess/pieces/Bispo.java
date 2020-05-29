package chess.pieces;

import board.Posicao;
import board.Tabuleiro;
import chess.Cor;
import chess.PecaXadrez;

public class Bispo extends PecaXadrez {

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "B";
	}

	// definindo movimentos poss�veis da Torre (somente em linha reta, quantas casas
	// quiser, n�o ultrapassando uma pe�a aliada)
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// noroeste
		p.setValor(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaExistente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValor(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && existeUmaPecaInimiga(p)) { // verificando se existe uma pe�a
																				// advers�ria na posi��o informada
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// nordeste
		p.setValor(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaExistente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValor(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && existeUmaPecaInimiga(p)) { // verificando se existe uma pe�a
																				// advers�ria na posi��o informada
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sudeste
		p.setValor(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaExistente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValor(posicao.getLinha() + 1, posicao.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && existeUmaPecaInimiga(p)) { // verificando se existe uma pe�a
																				// advers�ria na posi��o informada
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sudoeste
		p.setValor(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaExistente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValor(posicao.getLinha() + 1, posicao.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && existeUmaPecaInimiga(p)) { // verificando se existe uma pe�a
																				// advers�ria na posi��o informada
			mat[p.getLinha()][p.getColuna()] = true;
		}
		return mat;
	}
}
