package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.function.Predicate;

import br.com.cod3r.cm.excecao.ExplosaoException;

/*Classe responsável pelo tabuleiro e suas regras*/

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private int minas;
	
	private ArrayList<Campo> campos = new ArrayList<>();

//	Construtor
	public Tabuleiro(int linhas, int colunas, int minas) {
		super();
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
//	Métodos
	
	public void abrir(int linha, int coluna) {
		/*Abre o campo selecionado pelo jogador*/
		try {
			campos.parallelStream()
				.filter(c -> c.getLinha() == linha && 
				c.getColuna() == coluna)
				.findFirst()
				.ifPresent(c -> c.abrir());
		}catch(ExplosaoException e) {
			/*Caso haja uma mina no campo selecionado, o jogo para
			 e todos os campos do tabuleiro são revelados*/
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}
	}
	
	public void alternarMarcacao(int linha, int coluna) {
		/*Altera a marcação do campo selecionado pelo jogador*/
		campos.parallelStream()
		.filter(c -> c.getLinha() == linha &&
		c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.alternarMarcacao());
	}
	
	 private void gerarCampos() {
		/*Gera os campos a cada novo jogo*/
		for(int linha = 0; linha < linhas; linha++) {
			for(int coluna = 0; coluna < colunas; coluna++) {
				campos.add(new Campo(linha, coluna));
			}
		}
	}
	
	private void associarVizinhos() {
		/*Organiza os vizinhos nas posições corretas*/
		for(Campo c1: campos) {
			for(Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	
	private void sortearMinas() {
		/*Pega casas aleatorias dentro do tabuleiro e coloca minas*/
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		
		do {
			int aleatorio = (int)(Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		}while(minasArmadas < minas);
	}
	
	public boolean objetivoAlcancado() {
		/*Verifica se o jogador venceu*/
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciar() {
		/*Reinicia o jogo*/
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("  ");
		for(int c = 0; c < colunas; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		sb.append("\n");
		
		int i = 0;
		for (int l = 0; l < linhas; l++) {
			sb.append(l);
			sb.append(" ");
			for (int c = 0; c < colunas; c++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
