package org.example;

public class Main {
    public static void main(String[] args) {
        GerenciadorMemoria gm = new GerenciadorMemoria(15);

        gm.exibirMemoriaLivre();

        Integer a = gm.alocar(3); // deve alocar
        Integer b = gm.alocar(4); // deve alocar
        Integer c = gm.alocar(5); // deve alocar
        Integer d = gm.alocar(6); // não deve conseguir

        gm.exibirMemoriaLivre();

        if (b != null)
            gm.liberar(b, 4); // libera bloco de 4

        gm.exibirMemoriaLivre();

        d = gm.alocar(6); // agora deve conseguir (se houver espaço contíguo após compactação)

        gm.exibirMemoriaLivre();
    }
}
