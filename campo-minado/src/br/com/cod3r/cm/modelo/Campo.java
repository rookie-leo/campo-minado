package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.cod3r.cm.excecao.ExplosaoException;

/*Classe responsável por gerar os campos e suas regras*/

public class Campo {

	private final int linha;
	private final int coluna;
	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;
	private List<Campo> vizinhos = new ArrayList<Campo>();
	
	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		/*Adiciona um campo ao lado do outro*/
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna- vizinho.coluna);
		int deltaGeral = deltaLinha + deltaColuna;
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else if(deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else {
			return false;
		}
		
	}
	
	void alternarMarcacao() {
		/*Muda a marcação do campo*/
		if(!aberto) {
			marcado = !marcado;
		}
	}
	
	boolean abrir() {
		/*Abre o campo se o mesmo estiver fechado*/
		if(!aberto && !marcado) {
			aberto = true;
			
			if(minado) {
				/*Se o campo aberto conter uma mina, será levantado 
				 uma exceção*/
				throw new ExplosaoException();
			}
			
			if(vizinhancaSegura()) {
				/*Outros campos serão abertos enquanto não houverem
				 minas na região*/
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		}else {
			return false;			
		}
	}
	
	boolean vizinhancaSegura() {
		/*irá abrir os campos seguros até encontrar um campo minado*/
		return vizinhos.stream().noneMatch(v -> v.minado);		
	}
	
	void minar() {
		/*Mina o campo*/
		minado = true;
	}
	
	
	boolean objetivoAlcancado() {
		/*Verifica se todas as condições para a vitória do jogador
		  foram atendidas*/
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	long minasNaVizinhanca() {
		/*Retorna o numero de minas na vizinhança*/
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar() {
		/*Reinicia o jogo*/
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	public String toString() {
		if(marcado) {
			return "x";
		}else if(aberto && minado) {
			return "*";
		}else if(aberto && minasNaVizinhanca() > 0) {
			return Long.toString(minasNaVizinhanca());
		}else if(aberto) {
			return " ";
		}else {
			return "?";
		}
	}

//	Getters and Setters
	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	public boolean isMarcado() {
		return marcado;
	}
	
	public boolean isAberto() {
		return aberto;
	}
	
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	public boolean isMinado() {
		return minado;
	}
	
}
