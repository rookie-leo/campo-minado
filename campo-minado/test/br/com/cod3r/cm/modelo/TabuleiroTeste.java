package br.com.cod3r.cm.modelo;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TabuleiroTeste {

	private Tabuleiro tabuleiro;
	
	@BeforeEach
	void iniciarTabuleiro() {
		/*Gera o tabuleiro*/
		tabuleiro = new Tabuleiro(6, 6, 12);
	}
	
	@Test
	void testeObejtivoAlcancado() {
		/*Testa se o objetivo foi alcan�ado
		 * nesse teste, o objetivo n�o foi alcan�ado*/
		assertFalse(tabuleiro.objetivoAlcancado());
	}
	
	@Test
	void testeReiniciar() {
		/*Reinicia o jogo*/
		tabuleiro.reiniciar();
	}

	@Test
	void testeAbrir() {
		/*Abre o campo selecionado pelo jogador*/
		tabuleiro.abrir(3, 5);
	}
	
	@Test
	void testeAlternarMarcacao() {
		/*Alterna a marca��o do campo selecionado*/
		tabuleiro.alternarMarcacao(5, 3);
	}
}
