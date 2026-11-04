package br.ucsal.achadosperdidos.apresentacao;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.ucsal.achadosperdidos.aplicacao.SolicitacaoService;

/**
 * Controller fino para abertura de solicitações de recuperação (RF05).
 */
@Controller
@RequestMapping("/objetos/{objetoId}/solicitacoes")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping
    public String solicitar(@PathVariable Long objetoId, @RequestParam String provaPosse) {
        solicitacaoService.solicitar(objetoId, provaPosse);
        return "redirect:/objetos/" + objetoId;
    }
}
