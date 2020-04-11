package board;

public abstract class Peca {
	
	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	//m�todo para movimentos poss�veis das pe�as de forma gen�rica
	public abstract boolean[][] movimentosPossiveis();
	
	// pega a posi��o solicitada para ser analisado as linhas e colunas ao redor no tabuleiro
	public boolean movimentoPossivel(Posicao posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}
	
	// m�todo para verificar se existe algum movimento poss�vel dispon�vel
	public boolean existeMovimentoPossivel() {
		boolean[][] mat = movimentosPossiveis();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
}
