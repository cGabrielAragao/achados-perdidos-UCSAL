package br.ucsal.achadosperdidos.apresentacao;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.server.ResponseStatusException;

import br.ucsal.achadosperdidos.dominio.objeto.ObjetoInvalidoException;
import br.ucsal.achadosperdidos.dominio.objeto.ProvaObrigatoriaException;
import br.ucsal.achadosperdidos.dominio.objeto.TransicaoInvalidaException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Traduz as exceções de domínio (RNF04) em respostas amigáveis: a UI nunca
 * deve quebrar com stack trace ou um HTTP 500 cru. Fica na apresentação
 * porque só aqui se decide COMO mostrar o erro (redirecionar com flash,
 * ou renderizar uma página de erro) — o domínio continua só lançando a
 * exceção, sem saber nada sobre HTTP.
 */
@ControllerAdvice
public class TratamentoGlobalDeExcecoes {

    /** RN04: prova de posse ausente ao solicitar recuperação. */
    @ExceptionHandler(ProvaObrigatoriaException.class)
    public String tratarProvaObrigatoria(ProvaObrigatoriaException ex, HttpServletRequest request,
                                          RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
        return "redirect:" + telaDoObjetoDaRequisicao(request);
    }

    /** RN10: dados de registro inválidos (campos obrigatórios ou data futura). */
    @ExceptionHandler(ObjetoInvalidoException.class)
    public String tratarObjetoInvalido(ObjetoInvalidoException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
        return "redirect:/objetos/novo";
    }

    /** Transição de status não permitida no estado atual (padrão State). */
    @ExceptionHandler(TransicaoInvalidaException.class)
    public String tratarTransicaoInvalida(TransicaoInvalidaException ex, HttpServletRequest request,
                                           RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
        return "redirect:" + telaDoObjetoDaRequisicao(request);
    }

    /** Objeto/local referenciado não existe (ex.: id inválido na URL ou no formulário). */
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView tratarRecursoNaoEncontrado(IllegalArgumentException ex) {
        return paginaDeErro(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /** Ex.: detalhe de um objeto inexistente (RF04). */
    @ExceptionHandler(ResponseStatusException.class)
    public ModelAndView tratarResponseStatusException(ResponseStatusException ex) {
        HttpStatus status = HttpStatus.resolve(ex.getStatusCode().value());
        String mensagem = ex.getReason() != null ? ex.getReason() : "A página solicitada não foi encontrada.";
        return paginaDeErro(status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR, mensagem);
    }

    private ModelAndView paginaDeErro(HttpStatus status, String mensagem) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.setStatus(status);
        modelAndView.addObject("mensagem", mensagem);
        return modelAndView;
    }

    /**
     * As ações sujeitas a essas exceções (solicitar recuperação) sempre
     * partem da tela de detalhe de um objeto ({@code /objetos/{objetoId}/...}).
     * Em vez de cada handler adivinhar a origem, lemos o {@code objetoId}
     * já resolvido pelo Spring MVC para a requisição atual.
     */
    @SuppressWarnings("unchecked")
    private String telaDoObjetoDaRequisicao(HttpServletRequest request) {
        Map<String, String> variaveisDeRota =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String objetoId = variaveisDeRota != null ? variaveisDeRota.get("objetoId") : null;
        return objetoId != null ? "/objetos/" + objetoId : "/objetos";
    }
}
