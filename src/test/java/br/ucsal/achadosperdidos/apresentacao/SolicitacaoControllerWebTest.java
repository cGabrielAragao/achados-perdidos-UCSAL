package br.ucsal.achadosperdidos.apresentacao;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.ucsal.achadosperdidos.aplicacao.SolicitacaoService;
import br.ucsal.achadosperdidos.dominio.objeto.ProvaObrigatoriaException;

/**
 * Testa, na camada web, que {@link TratamentoGlobalDeExcecoes} evita que
 * uma exceção de domínio chegue ao usuário como erro 500 (RNF04).
 */
@WebMvcTest(SolicitacaoController.class)
class SolicitacaoControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SolicitacaoService solicitacaoService;

    @Test
    void solicitarComProvaVaziaNaoResultaEm500EMostraMensagemNaTelaDeOrigem() throws Exception {
        willThrow(new ProvaObrigatoriaException())
                .given(solicitacaoService).solicitar(anyLong(), anyString());

        mockMvc.perform(post("/objetos/1/solicitacoes").param("provaPosse", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/objetos/1"))
                .andExpect(flash().attributeExists("mensagemErro"));
    }
}
