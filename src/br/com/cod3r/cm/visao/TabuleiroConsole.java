package br.com.cod3r.cm.visao;

import br.com.cod3r.cm.excecao.ExplosaoException;
import br.com.cod3r.cm.excecao.SairException;
import br.com.cod3r.cm.modelo.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class TabuleiroConsole {
    private Tabuleiro tabuleiro;
    private Scanner sc = new Scanner(System.in);
    public TabuleiroConsole(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        this.executarJogo();
    }

    private void executarJogo() {
        try {
            boolean continuar = true;
            while(continuar) {
                this.cicloDoJogo();
                System.out.println("Outra partida? (S/n)");
                String resposta = sc.nextLine();
                if ("n".equalsIgnoreCase(resposta)) {
                    continuar = false;
                } else {
                    tabuleiro.reiniciarJogo();
                }
            }
        } catch (SairException sair) {
            System.out.println("Tchau!!");
        } finally {
            sc.close();
        }
    }

    private void cicloDoJogo() {
        try {
            while (!this.tabuleiro.objetivoAlcancado()) {
                System.out.println(this.tabuleiro);
                String digitado = capturarValorDigitado("Digite o valor de XY: ");


                Integer x = parseInt(digitado.trim().substring(0, 1));
                Integer y = parseInt(digitado.trim().substring(1, 2));
                digitado = capturarValorDigitado("1 - Abrir, 2 - Marcar/Desmarcar: ");
                if ("1".equals(digitado)) {
                    this.tabuleiro.abrir(x, y);
                } else if ("2".equals(digitado)) {
                    this.tabuleiro.marcar(x, y);
                }
            }
            System.out.println(this.tabuleiro);
            System.out.println("Você ganhou!");
        } catch (ExplosaoException explosao) {
            System.out.println(this.tabuleiro);
            System.out.println("Você perdeu!");

        }
    }

    private String capturarValorDigitado(String texto) {
        System.out.println(texto);
        String digitado = sc.nextLine();

        if ("sair".equalsIgnoreCase(digitado)) {
            throw new SairException();
        }

        return digitado;
    }
}
