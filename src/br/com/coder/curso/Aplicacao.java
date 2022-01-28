package br.com.coder.curso;

import br.com.coder.curso.modelo.Tabuleiro;
import br.com.coder.curso.visao.TabuleiroConsole;

public class Aplicacao {
	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(8, 8, 1);
		
		new TabuleiroConsole(tabuleiro);
		
	}
}
