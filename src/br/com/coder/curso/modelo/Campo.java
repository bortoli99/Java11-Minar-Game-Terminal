package br.com.coder.curso.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.coder.curso.excesao.ExplosaoException;

public class Campo {
	
	private final Integer linha;
	private final Integer coluna;
	private Boolean minado = false;
	private Boolean aberto = false;
	private Boolean marcado = false;
	private List<Campo> vizinhos = new ArrayList<>();
	
	public Campo(Integer linha, Integer coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	
	public Boolean adicionarVizinho(Campo vizinho) {
		Boolean linhaDiferente = this.linha != vizinho.linha;
		Boolean colunaDiferente = this.coluna != vizinho.coluna;
		Boolean diagonal = linhaDiferente && colunaDiferente;
		
		Integer deltaLinha = Math.abs(this.linha - vizinho.linha);
		Integer deltaColuna = Math.abs(this.coluna - vizinho.coluna);
		Integer deltaGeral = deltaColuna + deltaLinha;
		
		if (deltaGeral == 1 && !diagonal) {
			this.vizinhos.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			this.vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
		
	}
	
	public void  alternarMarcacao() {
		if(!this.aberto) {
			this.marcado = !this.marcado;
		}
	}
	
	public Boolean abrir() {
		
		if(!this.aberto && !this.marcado) {
			this.aberto = true;
			
			if(this.minado) {
				throw new ExplosaoException();
			}
			
			if(vizinhacaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			
			return true;
		}else {
			return false;
		}
	}
	
	public Boolean vizinhacaSegura() {
		return this.vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	public void minar() {
		setMinado(true);
	}
	
	public Boolean objetivoAlcancado() {
		Boolean desvendado = !minado && aberto;
		Boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	public Long minasNaVizinhanca() { 
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	public void reiniciar () {
		this.aberto = false;
		this.minado = false;
		this.marcado = false;
	}

	public String toString() {
		if(this.marcado) {
			return "x";
		} else if (aberto && minado) {
			return "*";
		} else if (aberto && minasNaVizinhanca() > 0) {
			return Long.toString(minasNaVizinhanca());
		} else if (aberto) {
			return " ";
		} else {
			return "?";
		}
	}
	
	public Boolean getMinado() {
		return minado;
	}

	public void setMinado(Boolean minado) {
		this.minado = minado;
	}

	public Boolean getAberto() {
		return aberto;
	}

	public void setAberto(Boolean aberto) {
		this.aberto = aberto;
	}

	public Boolean getMarcado() {
		return marcado;
	}

	public void setMarcado(Boolean marcado) {
		this.marcado = marcado;
	}

	public List<Campo> getVizinhos() {
		return vizinhos;
	}

	public void setVizinhos(List<Campo> vizinhos) {
		this.vizinhos = vizinhos;
	}

	public Integer getLinha() {
		return linha;
	}

	public Integer getColuna() {
		return coluna;
	}

	@Override
	public int hashCode() {
		return Objects.hash(aberto, coluna, linha, marcado, minado, vizinhos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campo other = (Campo) obj;
		return Objects.equals(aberto, other.aberto) && Objects.equals(coluna, other.coluna)
				&& Objects.equals(linha, other.linha) && Objects.equals(marcado, other.marcado)
				&& Objects.equals(minado, other.minado) && Objects.equals(vizinhos, other.vizinhos);
	}
	
	
}
