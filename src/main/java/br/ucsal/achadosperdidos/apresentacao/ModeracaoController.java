package br.ucsal.achadosperdidos.apresentacao;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ucsal.achadosperdidos.aplicacao.ModeracaoService;

/**
 * Área de moderação (RF06/RF07).
 *
 * <p><strong>Sem autenticação ainda:</strong> qualquer pessoa com o link
 * para {@code /moderacao} pode aprovar, recusar ou concluir devoluções.
 * Restringir isso a um moderador autenticado é o RF08 (desejável), que só
 * será implementado depois do fluxo central completo — a própria tela
 * avisa isso ao usuário.
 */
@Controller
@RequestMapping("/moderacao")
public class ModeracaoController {

    private final ModeracaoService moderacaoService;

    public ModeracaoController(ModeracaoService moderacaoService) {
        this.moderacaoService = moderacaoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pendentes", moderacaoService.listarPendentes());
        model.addAttribute("aprovadas", moderacaoService.listarAprovadas());
        return "moderacao/lista";
    }

    @PostMapping("/{solicitacaoId}/aprovar")
    public String aprovar(@PathVariable Long solicitacaoId) {
        moderacaoService.aprovar(solicitacaoId);
        return "redirect:/moderacao";
    }

    @PostMapping("/{solicitacaoId}/recusar")
    public String recusar(@PathVariable Long solicitacaoId) {
        moderacaoService.recusar(solicitacaoId);
        return "redirect:/moderacao";
    }

    @PostMapping("/{solicitacaoId}/concluir")
    public String concluir(@PathVariable Long solicitacaoId) {
        moderacaoService.concluirDevolucao(solicitacaoId);
        return "redirect:/moderacao";
    }
}
