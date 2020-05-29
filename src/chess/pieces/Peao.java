package chess.pieces;

import board.Posicao;
import board.Tabuleiro;
import chess.Cor;
import chess.PartidaXadrez;
import chess.PecaXadrez;

public class Peao extends PecaXadrez {
	
	private PartidaXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
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
			//# movimento especial en passant #
			if (posicao.getLinha() == 3) {
				Posicao esq = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExistente(esq) && existeUmaPecaInimiga(esq) && getTabuleiro().peca(esq) == partidaXadrez.getEnPassantVulneravel()) {
					mat[esq.getLinha() - 1][esq.getColuna()] = true;
				}
				Posicao dir = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExistente(dir) && existeUmaPecaInimiga(dir) && getTabuleiro().peca(dir) == partidaXadrez.getEnPassantVulneravel()) {
					mat[dir.getLinha() - 1][dir.getColuna()] = true;
				}
			}
		}
		else {
			p.setValor(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaExistente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValor(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
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
			//# movimento especial en passant #
			if (posicao.getLinha() == 4) {
				Posicao esq = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExistente(esq) && existeUmaPecaInimiga(esq) && getTabuleiro().peca(esq) == partidaXadrez.getEnPassantVulneravel()) {
					mat[esq.getLinha() + 1][esq.getColuna()] = true;
				}
				Posicao dir = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExistente(dir) && existeUmaPecaInimiga(dir) && getTabuleiro().peca(dir) == partidaXadrez.getEnPassantVulneravel()) {
					mat[dir.getLinha() + 1][dir.getColuna()] = true;
				}
			}
		}

		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
