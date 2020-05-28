package chess.pieces;

import board.Posicao;
import board.Tabuleiro;
import chess.Cor;
import chess.PecaXadrez;

public class Peao extends PecaXadrez {

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.BRANCO) {
			p.setValor(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaExistente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaExistente(p)
					&& getTabuleiro().posicaoExistente(p2) && !getTabuleiro().pecaExistente(p2)
					&& getContagemMovimento() == 0) {	
				;
			mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExistente(p) && existeUmaPecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExistente(p) && existeUmaPecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		else {
			p.setValor(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaExistente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaExistente(p)
					&& getTabuleiro().posicaoExistente(p2) && !getTabuleiro().pecaExistente(p2)
					&& getContagemMovimento() == 0) {	
				;
			mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExistente(p) && existeUmaPecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExistente(p) && existeUmaPecaInimiga(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		}

		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
