package br.com.coder.curso.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.coder.curso.excesao.ExplosaoException;
import br.com.coder.curso.excesao.SairException;
import br.com.coder.curso.modelo.Tabuleiro;

public class TabuleiroConsole {

	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);

	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;

		execturtarJogo();
	}

	private void execturtarJogo() {
		try {
			Boolean continuar = true;

			while (continuar) {

				cicloDoJogo();

				System.out.println("Outra partidade? (S/N)");
				String resposta = entrada.nextLine();

				if ("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				} else {
					tabuleiro.reiniciar();
				}

			}

		} catch (SairException e) {
			System.out.println("Tchau!!!");
		} finally {
			entrada.close();
		}
	}

	private void cicloDoJogo() {
		try {

			while (!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro.toString());

				String digitado = capturaValorDigitado("Digite (x,y):");

				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
						.map(elemento -> Integer.parseInt(elemento.trim())).iterator();

				digitado = capturaValorDigitado("1 - Abrir ou 2 - (Des)Marcar: ");

				if ("1".equals(digitado)) {
					tabuleiro.abrir(xy.next(), xy.next());
				} else if("2".equals(digitado)) {
					tabuleiro.marcar(xy.next(), xy.next());
				}

			}

			System.out.println("Você Ganhou !!!");
		} catch (ExplosaoException e) {
			System.out.println(tabuleiro.toString());
			System.out.println("Game Over!!!!!!!");
		}
	}

	private String capturaValorDigitado(String texto) {
		System.out.println(texto);
		String digitado = entrada.nextLine();

		if ("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}

		return digitado;

	}

}
