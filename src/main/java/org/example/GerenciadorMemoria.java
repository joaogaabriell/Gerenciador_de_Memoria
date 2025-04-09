package org.example;

public class GerenciadorMemoria {
    private final int tamanhoMemoria;
    private BlocoMemoria blocosLivres;

    public GerenciadorMemoria(int tamanhoMemoria) {
        this.tamanhoMemoria = tamanhoMemoria;
        this.blocosLivres = new BlocoMemoria(0, tamanhoMemoria); // inicia com tudo livre
    }


    public Integer alocar(int tamanho) {
        BlocoMemoria[] melhor = encontrarMelhorBloco(tamanho);

        BlocoMemoria melhorBloco = melhor[0];
        BlocoMemoria anterior = melhor[1];

        if (melhorBloco == null) {
            System.out.println("Erro: Não há espaço suficiente para alocar " + tamanho + " unidades.");
            return null;
        }

        int enderecoAlocado = melhorBloco.inicio;

        atualizarBlocoAposAlocacao(melhorBloco, anterior, tamanho);

        System.out.println("Alocado " + tamanho + " unidades no endereço " + enderecoAlocado);
        return enderecoAlocado;
    }

    /**
     * Libera um bloco de memória e realiza compactação.
     */
    public void liberar(int endereco, int tamanho) {
        BlocoMemoria novo = new BlocoMemoria(endereco, tamanho);

        inserirBlocoOrdenado(novo);
        compactarBlocosAdjacentes();

        System.out.println("Liberado " + tamanho + " unidades a partir do endereço " + endereco);
    }

    /**
     * Exibe os blocos livres atuais.
     */
    public void exibirMemoriaLivre() {
        System.out.print("Memória livre: ");
        BlocoMemoria atual = blocosLivres;
        while (atual != null) {
            System.out.print("[" + atual.inicio + "," + (atual.inicio + atual.tamanho - 1) + "] ");
            atual = atual.proximo;
        }
        System.out.println();
    }


    private BlocoMemoria[] encontrarMelhorBloco(int tamanho) {
        BlocoMemoria melhorBloco = null;
        BlocoMemoria anteriorMelhor = null;

        BlocoMemoria atual = blocosLivres;
        BlocoMemoria anterior = null;

        while (atual != null) {
            if (atual.tamanho >= tamanho) {
                if (melhorBloco == null || atual.tamanho < melhorBloco.tamanho) {
                    melhorBloco = atual;
                    anteriorMelhor = anterior;
                }
            }
            anterior = atual;
            atual = atual.proximo;
        }

        return new BlocoMemoria[]{melhorBloco, anteriorMelhor};
    }

    private void atualizarBlocoAposAlocacao(BlocoMemoria bloco, BlocoMemoria anterior, int tamanho) {
        bloco.inicio += tamanho;
        bloco.tamanho -= tamanho;

        if (bloco.tamanho == 0) {
            if (anterior == null) {
                blocosLivres = bloco.proximo;
            } else {
                anterior.proximo = bloco.proximo;
            }
        }
    }

    private void inserirBlocoOrdenado(BlocoMemoria novo) {
        if (blocosLivres == null || novo.inicio < blocosLivres.inicio) {
            novo.proximo = blocosLivres;
            blocosLivres = novo;
        } else {
            BlocoMemoria atual = blocosLivres;
            while (atual.proximo != null && atual.proximo.inicio < novo.inicio) {
                atual = atual.proximo;
            }
            novo.proximo = atual.proximo;
            atual.proximo = novo;
        }
    }

    private void compactarBlocosAdjacentes() {
        BlocoMemoria atual = blocosLivres;
        while (atual != null && atual.proximo != null) {
            if (atual.inicio + atual.tamanho == atual.proximo.inicio) {
                atual.tamanho += atual.proximo.tamanho;
                atual.proximo = atual.proximo.proximo;
            } else {
                atual = atual.proximo;
            }
        }
    }
}
