package br.ucsal.achadosperdidos.apresentacao;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller provisório para validar que a aplicação sobe corretamente
 * (esqueleto do Incremento 1). Será substituído pelas telas reais do fluxo
 * de busca de objetos no próximo incremento.
 */
@Controller
public class PaginaInicialController {

    @GetMapping("/")
    public String paginaInicial() {
        return "inicio";
    }
}
