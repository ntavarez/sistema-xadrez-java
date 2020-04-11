package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.PartidaXadrez;
import chess.PecaXadrez;
import chess.PosicaoXadrez;
import chess.XadrezException;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXadrez partida = new PartidaXadrez();

		while (true) {

			//limpeza da tela e em seguida solicitaçãopara usuário informar posição de origem e destino no tabuleiro
			try {
				UI.clearScreen();
				UI.printTabuleiro(partida.getPecas());
				System.out.println();
				System.out.println("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);

				System.out.println();
				System.out.println("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

				PecaXadrez pecaCapturada = partida.movimentoPecaXadrez(origem, destino);
				
			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();

			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();

			}

		}
	}
}
