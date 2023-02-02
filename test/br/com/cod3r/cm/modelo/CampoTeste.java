package br.com.cod3r.cm.modelo;


import br.com.cod3r.cm.excecao.ExplosaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class CampoTeste {
    private Campo campo;
    @BeforeEach //para cada novo teste ele vai inicializar um novo campo
    void iniciarCampo () {
        campo = new Campo(3, 3);
    }

    @Test
    public void testeVizinhoRealParalelo() {
        Campo vizinhoEsquerda = new Campo(3,2); //vai ser vizinho ne tem que dar true
        boolean resultadoEsquerda = campo.adicionarVizinho(vizinhoEsquerda);

        Campo vizinhoDireita = new Campo(3,4); //vai ser vizinho ne tem que dar true
        boolean resultadoDireita = campo.adicionarVizinho(vizinhoDireita);

        Campo vizinhoCima = new Campo(2,3); //vai ser vizinho ne tem que dar true
        boolean resultadoCima = campo.adicionarVizinho(vizinhoCima);

        Campo vizinhoBaixo = new Campo(4, 3);
        boolean resultadoBaixo = campo.adicionarVizinho(vizinhoBaixo);

        assertTrue(resultadoEsquerda);
        assertTrue(resultadoDireita);
        assertTrue(resultadoCima);
        assertTrue(resultadoBaixo);
    }

    @Test
    public void testeVizinhoRealDiagonal() {
        Campo vizinhoSuperiorEsquerda = new Campo(2,2); //vai ser vizinho ne tem que dar true
        boolean resultadoSuperiorEsquerda = campo.adicionarVizinho(vizinhoSuperiorEsquerda);

        Campo vizinhoSuperiorDireita = new Campo(2,4); //vai ser vizinho ne tem que dar true
        boolean resultadoSuperiorDireita = campo.adicionarVizinho(vizinhoSuperiorDireita);

        Campo vizinhoInferiorEsquerda = new Campo(4,2); //vai ser vizinho ne tem que dar true
        boolean resultadoInferiorEsquerda = campo.adicionarVizinho(vizinhoInferiorEsquerda);

        Campo vizinhoInferiorDireita = new Campo(4, 4);
        boolean resultadoInferiorDireita = campo.adicionarVizinho(vizinhoInferiorDireita);

        assertTrue(resultadoSuperiorEsquerda);
        assertTrue(resultadoSuperiorDireita);
        assertTrue(resultadoInferiorEsquerda);
        assertTrue(resultadoInferiorDireita);
    }

    @Test
    public void testeVizinhoFalso() {
        Campo vizinhoFalso = new Campo(1,1); //vai ser vizinho ne tem que dar true
        boolean resultadoFalso = campo.adicionarVizinho(vizinhoFalso);
        Campo vizinhoFalsoDois = new Campo(5,5); //vai ser vizinho ne tem que dar true
        boolean resultadoFalsoDois = campo.adicionarVizinho(vizinhoFalsoDois);
        assertFalse(resultadoFalso);
        assertFalse(resultadoFalsoDois);
    }


    @Test
    public void alternarMarcacao() {
        assertFalse(campo.isMarcado());
        campo.alternarMarcacao();
        assertTrue(campo.isMarcado());
    }

    @Test
    public void alternarMarcacaoDuasChamadas() {
        campo.alternarMarcacao();
        campo.alternarMarcacao();
        assertFalse(campo.isMarcado());
    }

    @Test
    public void testeAbrirNaoMinadoNaoMarcado() {
        assertTrue(campo.abrir());
    }

    @Test
    public void testeAbrirNaoMinadoMarcado() {
        campo.alternarMarcacao();
        assertFalse(campo.abrir());
    }

    @Test
    public void testeAbrirMinadoMarcado() {
        campo.alternarMarcacao();
        campo.minar();
        assertFalse(campo.abrir());
    }

    @Test
    public void testeAbrirMinadoNaoMarcado() {
        campo.minar();
        assertThrows(ExplosaoException.class, () -> { //ele espera receber uma excessao do tipo ExplosaoException
           campo.abrir();
        });
    }

    @Test
    public void testeAbrirComVizinhos1() {
        Campo campo11 = new Campo(1, 1);
        Campo campo22 = new Campo(2, 2);
        campo22.adicionarVizinho(campo11);
        campo.adicionarVizinho(campo22);

        campo.abrir();
        assertTrue(campo.isAberto());
        assertTrue(campo22.isAberto());
        assertTrue(campo11.isAberto());
    }

    @Test
    public void testeAbrirComVizinhos2() {
        Campo campo11 = new Campo(1, 1);
        Campo campo12 = new Campo(1, 2);
        campo12.minar();
        Campo campo22 = new Campo(2, 2);
        campo22.adicionarVizinho(campo11);
        campo22.adicionarVizinho(campo12);

        campo.adicionarVizinho(campo22);
        campo.abrir();
        assertTrue(campo22.isAberto() && campo11.isFechado());
        assertTrue(campo12.isFechado());
    }

    @Test
    public void testeDesvendadoMinadoMarcado() {
        campo.minar();
        campo.alternarMarcacao();
        assertTrue(campo.objetivoAlcancado());
    }

    @Test
    public void testeDesvendadoNaoMinadoAberto() {
        campo.abrir();
        assertTrue(campo.objetivoAlcancado());
    }

    @Test
    public void testeQuantasMinasNaVizinhanca() {
        Campo campo22 = new Campo(2, 2);
        Campo campo34 = new Campo(3,4);
        Campo campo44 = new Campo(4, 4);
        campo22.minar();
        campo34.minar();

        campo.adicionarVizinho(campo44);
        campo.adicionarVizinho(campo34);
        campo.adicionarVizinho(campo22);

        long minasNaVizinhanca = campo.minasNaVizinhanca();
        assertEquals(2, minasNaVizinhanca);
    }

    @Test
    public void testeReiniciarCampoAberto() {
        campo.abrir();
        campo.reiniciar();

        assertFalse(campo.isAberto());
    }
}
