package br.com.cod3r.cm.modelo;

import br.com.cod3r.cm.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    private int linhas;
    private int colunas;
    private int minas;
    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarOsVizinhos();
        sortearMinas();
    }

    public void abrir(int linha, int coluna) {
        try {
            this.campos.stream().filter(c -> c.getLinha() == linha).filter(c -> c.getColuna() == coluna).findFirst().ifPresent(c -> c.abrir());
        } catch (ExplosaoException e) {
            this.campos.forEach(c -> c.setAberto(true));
            throw e;
        }
    }

    public void marcar(int linha, int coluna) {
        this.campos.stream().filter(c -> c.getLinha() == linha).filter(c -> c.getColuna() == coluna).findFirst().ifPresent(c -> c.alternarMarcacao());
    }
    private void gerarCampos() {
        for(int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                campos.add(new Campo(i, j));
            }
        }
    }

    private void associarOsVizinhos() {
        for(Campo c1: this.campos) {
            for(Campo c2: this.campos) {
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void sortearMinas() {
        int minasArmadas = 0;
        do {
            int aleatorio = (int) (Math.random() * this.campos.size());
            this.campos.get(aleatorio).minar();
            minasArmadas = (int) this.campos.stream().filter(c -> c.isMinado()).count();
        } while (minasArmadas < this.minas);
    }

    public boolean objetivoAlcancado() {
        return this.campos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciarJogo() {
        this.campos.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int c = 0; c < this.colunas; c++) {
            sb.append(" ");
            sb.append(c);
            sb.append(" ");
        }
        sb.append("\n");
        int i = 0;
        for(int l = 0; l < linhas; l ++) {
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
