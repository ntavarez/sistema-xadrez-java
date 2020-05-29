package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.PartidaXadrez;
import chess.PecaXadrez;
import chess.PosicaoXadrez;
import chess.XadrezException;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXadrez partida = new PartidaXadrez();
		List<PecaXadrez> capturadas = new ArrayList<>();

		while (!partida.getXequeMate()) {

			//limpeza da tela e em seguida solicita��opara usu�rio informar posi��o de origem e destino no tabuleiro
			try {
				UI.clearScreen();
				//UI.printTabuleiro(partida.getPecas());
				UI.printPartida(partida, capturadas);
				System.out.println();
				System.out.println("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);

				boolean[][] movimentosPossiveis = partida.movimentosPossiveis(origem);
				UI.clearScreen();
				UI.printTabuleiro(partida.getPecas(), movimentosPossiveis); //sobrecarga para imprimir as cores das posi��es poss�veis
				
				System.out.println();
				System.out.println("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

				PecaXadrez pecaCapturada = partida.movimentoPecaXadrez(origem, destino);
				
				if (pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}
				
				if(partida.getPromocao() != null) {
					System.out.println("Digita uma peça para promoção (B/C/T/Rn: ");
					String tipo = sc.nextLine();
					partida.substituirPecaPromovida(tipo);
				}
				
			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();

			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();

			}

		}
		UI.clearScreen();
		UI.printPartida(partida, capturadas);
	}
}
