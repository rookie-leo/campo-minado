package br.com.cod3r.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.cod3r.cm.excecao.ExplosaoException;
import br.com.cod3r.cm.excecao.SairException;
import br.com.cod3r.cm.modelo.Tabuleiro;

/*Classe respons�vel pela jogabilidade e por gerar o jogo no console*/

public class TabuleiroConsole {

	private Tabuleiro tabuleiro;
	private Scanner tcl = new Scanner(System.in);
	
//	Construtor
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}
	
	private void executarJogo() {
		/*Executa o jogo*/
		try {
			
			boolean continuar = true;
			
			while(continuar) {
				cicloDoJogo();
				
				
				System.out.println("Outra partida? (S/n) ");
				String resposta = tcl.nextLine();
				
				if ("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				}else {
					tabuleiro.reiniciar();
				}
			}
		}catch (SairException e){
			System.out.println("Tchau!!!");
		}finally {
			tcl.close();
		}
	}
	
	private void cicloDoJogo() {
		/*Mant�m o jogo em execu��o at� o jogador vencer, ou perder, ou desistir*/
		try {
			
			while(!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro);
				
				/*L�gica respons�vel por ler o que foi digitado pelo jogador e reconhecer 
				 os campos no tabuleiro*/
				String digitado = capturarValorDigitado("Digite linha e coluna(l, c): ");
				
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
						.map(e -> Integer.parseInt(e.trim()))
						.iterator();
				
				
				/*L�gica respons�vel por ler a op��o escolhida pelo jogador*/
				System.out.println("Escolha uma op��o");
				digitado = capturarValorDigitado(" [ 1 ] abrir\n [ 2 ] (DES)marcar:");
				
				if("1".equals(digitado)) {
					/*Abre o campo*/
					tabuleiro.abrir(xy.next(), xy.next());
				}else if("2".equals(digitado)) {
					/*Marca o campo como minado*/
					tabuleiro.alternarMarcacao(xy.next(), xy.next());
				}
			}
			
			System.out.println(tabuleiro);//Mostra todos os campos do tabuleiro
			System.out.println("Voc� venceu!!!");
		}catch (ExplosaoException e) {
			System.out.println(tabuleiro);//Mostra todos os campos do tabuleiro
			System.err.println("Voc� perdeu!");
		}
	}
	
	private String capturarValorDigitado(String texto) {
		/*Verifica o que foi digitado pelo jogador*/
		System.out.print(texto);
		String digitado = tcl.nextLine();
		
		if("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}
		return digitado;
	}
}
