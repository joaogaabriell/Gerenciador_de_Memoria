package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GerenciadorMemoriaTest {

    private GerenciadorMemoria gm;

    @BeforeEach
    public void setUp() {
        gm = new GerenciadorMemoria(15);
    }

    @Test
    public void testAlocacaoSequencial() {
        assertEquals(0, gm.alocar(3));
        assertEquals(3, gm.alocar(4));
        assertEquals(7, gm.alocar(5));
    }

    @Test
    public void testAlocacaoSemEspacoRetornaNull() {
        gm.alocar(3);
        gm.alocar(4);
        gm.alocar(5);
        assertNull(gm.alocar(6), "Só restam 3 unidades — alocação de 6 deve falhar");
    }

    @Test
    public void testBestFitEscolheMenorBlocoSuficiente() {
        gm.alocar(3);              // [0,2]
        Integer b = gm.alocar(4);  // [3,6]
        gm.alocar(5);              // [7,11]  — sobra [12,14] (3 livres)
        gm.liberar(b, 4);          // buracos: [3,6] (4) e [12,14] (3)

        // Best-Fit para 4 unidades deve escolher o buraco exato de 4, não o de 3
        assertEquals(3, gm.alocar(4));
    }

    @Test
    public void testCompactacaoDeBlocosAdjacentes() {
        Integer a = gm.alocar(5);
        Integer b = gm.alocar(5);
        Integer c = gm.alocar(5);
        gm.liberar(a, 5);
        gm.liberar(c, 5);
        gm.liberar(b, 5); // os três blocos livres devem ser compactados em um só

        assertEquals(0, gm.alocar(15), "Após liberar tudo, deve caber uma alocação do tamanho total");
    }

    @Test
    public void testReusoAposLiberacao() {
        Integer a = gm.alocar(6);
        assertNotNull(a);
        gm.liberar(a, 6);
        assertEquals(0, gm.alocar(6));
    }
}
