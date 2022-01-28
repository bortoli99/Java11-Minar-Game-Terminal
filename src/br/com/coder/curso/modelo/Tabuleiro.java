package br.com.coder.curso.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import br.com.coder.curso.excesao.ExplosaoException;

public class Tabuleiro {

	private Integer linhas;
	private Integer colunas;
	private Integer minas;

	private final List<Campo> campos = new ArrayList<Campo>();

	public Tabuleiro(Integer linhas, Integer colunas, Integer minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;

		gerarCampos();
		associarOsvizinhos();
		sortearMinas();
	}

	public void abrir(Integer linha, Integer coluna) {
		try {
			this.campos.parallelStream().filter(campo -> campo.getLinha() == linha && campo.getColuna() == coluna)
					.findFirst().ifPresent(campo -> campo.abrir());
		} catch (ExplosaoException e) {
			this.campos.forEach(campo -> campo.setAberto(true));
			
			throw e;
		}
	}

	public void marcar(Integer linha, Integer coluna) {
		this.campos.parallelStream().filter(campo -> campo.getLinha() == linha && campo.getColuna() == coluna)
				.findFirst().ifPresent(campo -> campo.alternarMarcacao());
	}

	private void gerarCampos() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				this.campos.add(new Campo(linha, coluna));
			}
		}
	}

	private void associarOsvizinhos() {
		for (Campo campo : this.campos) {
			for (Campo vizinho : this.campos) {
				campo.adicionarVizinho(vizinho);
			}
		}
	}

	private void sortearMinas() {
		Long minasArmandas = 0L;
		Predicate<Campo> minado = campo -> campo.getMinado();

		do {
			Integer aleatorio = (int) (Math.random() * campos.size());
			this.campos.get(aleatorio).minar();
			minasArmandas = this.campos.stream().filter(minado).count();
		} while (minasArmandas < this.minas);

	}

	public Boolean objetivoAlcancado() {
		return this.campos.stream().allMatch(campo -> campo.objetivoAlcancado());
	}

	public void reiniciar() {
		this.campos.stream().forEach(campo -> campo.reiniciar());
	}

	public String toString() {

		StringBuilder sb = new StringBuilder();
		Integer index = 0;

		sb.append("  ");
		for (int coluna = 0; coluna < colunas; coluna++) {
			sb.append(" ");
			sb.append(coluna);
			sb.append(" ");
		}
		
		sb.append("\n");
		
		for (int linha = 0; linha < this.linhas; linha++) {
			sb.append(linha);
			sb.append(" ");
			for (int coluna = 0; coluna < this.colunas; coluna++) {
				sb.append(" ");
				sb.append(campos.get(index));
				sb.append(" ");
				index++;
			}
			sb.append("\n");
		}

		return sb.toString();
	}

}
