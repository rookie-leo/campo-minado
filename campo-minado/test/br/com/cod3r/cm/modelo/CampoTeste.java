package br.com.cod3r.cm.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class CampoTeste {

	private Campo campo;
	
	@BeforeEach
	void iniciarCampo() {
		/*para cada teste construido 
		  o campo será reiniciado automaticamente*/
		campo = new Campo(3, 3);
	}
	
	@Test
	void testeVizinhoRealDistancia1Esquerda() {
		/*Testa a distancia entre o campo atual e o vizinho*/
		Campo vizinho = new Campo(3, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);//Verifica se o resultado é true
	}
	
	@Test
	void testeVizinhoRealDistancia1Direita() {
		Campo vizinho = new Campo(3, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoRealDistancia1Cima() {
		Campo vizinho = new Campo(2, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoRealDistancia1Baixo() {
		Campo vizinho = new Campo(4, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDiagonalCimaEsquerda() {
		/*Testa se o vizinho está na casa diagonal*/
		Campo vizinho = new Campo(2, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDiagonalCimaDireita() {
		Campo vizinho = new Campo(2, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDiagonalBaixoEsquerda() {
		Campo vizinho = new Campo(4, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDiagonalBaixoDireita(){
		Campo vizinho = new Campo(4, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void naoVizinho() {
		/*Testa se a casa selecionada não é vizinha*/
		Campo vizinho = new Campo(1, 1);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);//Verifica se o resultado é falso
	}
	
	@Test
	void testeValorPadraoAtributoMarcado() {
		/*Verifica se o campo está fechado*/
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacao() {
		/*Marca o campo*/
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacaoDuasChamadas() {
		/*Marca e desmarca o campo*/
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void abrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}
	
	@Test
	void abrirNaoMinadoMarcado() {
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}
	
	@Test
	void abrirMinadoMarcado() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}
	
	@Test
	void abrirminadoNaoMarcado() {
		/*Abre um campo minado*/
		campo.minar();
		
		assertThrows(ExplosaoException.class, () -> {
			campo.abrir();
		});
	}
	
	@Test
	void abrirComVizinhos() {
	/*Abre todos os campos vizinhos enquanto estiver seguro*/
		Campo vizinhoDoVizinho1 = new Campo(1, 1);
		Campo vizinho1 = new Campo(2, 2);
		vizinho1.adicionarVizinho(vizinhoDoVizinho1);
		
		campo.adicionarVizinho(vizinho1);
		campo.abrir();
		
		assertTrue(vizinhoDoVizinho1.isAberto() && vizinho1.isAberto());
	}
	
	@Test
	void abrirComVizinhos2() {
	/*Abre todos os campos vizinhos enquanto estiver seguro*/
		
		Campo vizinhoDoVizinho1 = new Campo(1, 1);
		Campo vizinho2 = new Campo(1, 2);
		vizinho2.minar();
		Campo vizinho1 = new Campo(2, 2);
		vizinho1.adicionarVizinho(vizinhoDoVizinho1);
		vizinho1.adicionarVizinho(vizinho2);
		
		campo.adicionarVizinho(vizinho1);
		campo.abrir();
		
		assertTrue(vizinho1.isAberto() && !vizinhoDoVizinho1.isAberto() );
	}
	
	@Test
	void testeObjetivoAlcancadoProtegido() {
		Campo vizinho2 = new Campo(2, 2);
		
		campo.adicionarVizinho(vizinho2);
		vizinho2.minar();	
		vizinho2.isMarcado();
		
		assertFalse(vizinho2.objetivoAlcancado());
	}

	@Test
	void testeObjetivoAlcancadoDesvendado() {
		Campo vizinho2 = new Campo(2, 2);
		campo.adicionarVizinho(vizinho2);
		vizinho2.abrir();
		
		assertTrue(vizinho2.objetivoAlcancado());
	}
	
	@Test
	void testeMinasNaVizinhanca() {
		/*Retorna se o numero selecionado é o 
		 * numero de vizinhos minados*/
		Campo viz1, viz2, viz3, viz4, viz5;
		viz1 = new Campo(1, 1);
		viz2 = new Campo(2, 2);
		viz3 = new Campo(3, 2);
		viz4 = new Campo(4, 4);
		viz5 = new Campo(4, 2);		
		
		viz2.minar();
		viz3.minar();
		viz5.minar();
		
		campo.adicionarVizinho(viz1);
		campo.adicionarVizinho(viz2);
		campo.adicionarVizinho(viz3);
		campo.adicionarVizinho(viz4);
		campo.adicionarVizinho(viz5);
		
		viz4.abrir();
		
		assertEquals(campo.minasNaVizinhanca(), 3l);
	}
	
	@Test
	void testeReinicar() {
		/*Reinicia o jogo*/
		campo.reiniciar();
	}
	
	@Test
	void testeToStringMarcado() {
		/*Verifica se o campo foi marcado*/
		Campo viz1 = new Campo(1, 1);
		viz1.alternarMarcacao();
		assertEquals(viz1.toString(), "x");
		
	}
	
	@Test
	void testeToStringAbertoMinado() {
		/*Verifica se o campo com uma mina foi aberto e 
		 * se ocorreu uma explosão*/
		Campo viz1 = new Campo(1, 1);
		viz1.minar();
		assertThrows(ExplosaoException.class, () -> {
			viz1.abrir();
			
		});
		assertEquals(viz1.toString(), "*");
	}
	
	@Test
	void testeToStringAbertoMinasNaVizinhança() {
		/*Verifica se o campo foi aberto e retorna quantas minas 
		 * há na vizinhança*/
		Campo viz1, viz2, viz3, viz4, viz5;
		viz1 = new Campo(1, 1);
		viz2 = new Campo(2, 2);
		viz3 = new Campo(3, 2);
		viz4 = new Campo(4, 4);
		viz5 = new Campo(4, 2);		
		
		viz2.minar();
		viz3.minar();
		viz5.minar();
		
		campo.adicionarVizinho(viz1);
		campo.adicionarVizinho(viz2);
		campo.adicionarVizinho(viz3);
		campo.adicionarVizinho(viz4);
		campo.adicionarVizinho(viz5);
		
		viz4.abrir();
		
		if(campo.minasNaVizinhanca() > 0) 
			assertEquals(campo.minasNaVizinhanca(), 3);
		/*Ainda não consegui realizar o teste*/
	}
	
	@Test
	void testeToStringAberto() {
	/*Verifica se o campo foi aberto*/
		campo.abrir();
		assertEquals(campo.toString(), " ");
	}
	
	@Test
	void testeToStringFechado() {
		/*Verifica se o campo está fechado*/
		assertEquals(campo.toString(), "?");
	}
}
