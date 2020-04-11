package chess;

import board.Peca;
import board.Posicao;
import board.Tabuleiro;

public abstract class PecaXadrez extends Peca {

	private Cor cor;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	//método para identificar a peça adversária no tabuleiro
	protected boolean existeUmaPecaInimiga(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}
	
}
