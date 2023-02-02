package br.com.cod3r.cm.modelo;

import br.com.cod3r.cm.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Campo {
    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;
    private List<Campo> vizinhos = new ArrayList<>();
    private final int LINHA;
    private final int COLUNA;

     //nivel de visibilidade: pacote
    Campo(int linha, int coluna) {
        this.LINHA = linha;
        this.COLUNA = coluna;
    }

    boolean adicionarVizinho(Campo vizinho) {
        boolean linhaDiferente = this.LINHA != vizinho.LINHA; //se ambos forem TRUE, significa que o vizinho está na diagonal.
        boolean colunaDiferente = this.COLUNA != vizinho.COLUNA;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(this.LINHA - vizinho.LINHA);
        int deltaColuna = Math.abs(this.COLUNA - vizinho.COLUNA);

        int deltaGeral = deltaLinha + deltaColuna;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }

    void alternarMarcacao() {
        if(!aberto) {
            marcado = !marcado;
        }
    }

    boolean abrir() {
        if(!aberto && !marcado) { //Se o campo estiver fechado e não marcado então vc pode executar a logica..
            aberto = true;
            if (minado) {
                throw new ExplosaoException(); //Quebra o fluxo do jogo
            }
            if (vizinhancaSegura()) {
                vizinhos.forEach(v -> v.abrir());
            }
            return true;
        } else {
            return false;
        }
    }
    boolean vizinhancaSegura(){
        return vizinhos.stream().noneMatch(v -> v.minado); //nenhum vizinho pode dar match nesse predicado
    }
    void minar() {
        this.minado = true;
    }

    public boolean isMarcado() {
        return this.marcado;
    }

    public boolean isAberto() {
        return this.aberto;
    }

    public boolean isFechado() {
        return !this.isAberto();
    }

    void setAberto(boolean aberto){
        this.aberto = aberto;
    }
    public boolean isMinado() {
        return this.minado;
    }

    public int getLinha() {
        return this.LINHA;
    }

    public int getColuna() {
        return this.COLUNA;
    }

    public boolean objetivoAlcancado() {
        boolean desvendado = !this.minado && this.aberto;
        boolean protegido = this.minado && this.marcado;
        return desvendado || protegido;
    }

    public long minasNaVizinhanca() {
       return vizinhos.stream().filter(v -> v.minado).count();
    }

    public void reiniciar() {
        this.aberto = false;
        this.minado = false;
        this.marcado = false;
    }

    @Override
    public String toString() {
        if(this.marcado) {
            return "X";
        } else if (this.minado && this.aberto) {
            return "*";
        } else if (this.aberto && this.minasNaVizinhanca() > 0) {
            return Long.toString(this.minasNaVizinhanca());
        } else if (this.aberto) {
            return " ";
        } else {
            return "?";
        }
    }
}
