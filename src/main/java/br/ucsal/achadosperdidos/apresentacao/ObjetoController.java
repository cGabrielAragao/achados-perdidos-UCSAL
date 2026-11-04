package br.ucsal.achadosperdidos.apresentacao;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import br.ucsal.achadosperdidos.aplicacao.ObjetoDetalheDTO;
import br.ucsal.achadosperdidos.aplicacao.ObjetoService;
import br.ucsal.achadosperdidos.dominio.objeto.Categoria;

/**
 * Controller fino para registro, listagem (com filtros), e detalhe de
 * objetos (RF01/RF02/RF03/RF04). A regra de negócio, a composição dos
 * filtros de busca e a montagem dos DTOs ficam no {@link ObjetoService}.
 */
@Controller
@RequestMapping("/objetos")
public class ObjetoController {

    private final ObjetoService objetoService;

    public ObjetoController(ObjetoService objetoService) {
        this.objetoService = objetoService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) Categoria categoria,
                          @RequestParam(required = false) Long localId,
                          Model model) {
        model.addAttribute("objetos", objetoService.listarDisponiveis(categoria, localId));
        model.addAttribute("categorias", Categoria.values());
        model.addAttribute("locais", objetoService.listarLocais());
        model.addAttribute("categoriaSelecionada", categoria);
        model.addAttribute("localSelecionado", localId);
        return "objetos/lista";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        ObjetoDetalheDTO detalhe = objetoService.buscarDetalhe(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Objeto não encontrado."));
        model.addAttribute("objeto", detalhe);
        return "objetos/detalhe";
    }

    @GetMapping("/novo")
    public String novoFormulario(Model model) {
        model.addAttribute("formulario", new RegistroObjetoFormulario());
        adicionarListasDeApoio(model);
        return "objetos/formulario";
    }

    @PostMapping
    public String registrar(@Valid @ModelAttribute("formulario") RegistroObjetoFormulario formulario,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            adicionarListasDeApoio(model);
            return "objetos/formulario";
        }

        Long id = objetoService.registrar(
                formulario.getCategoria(),
                formulario.getDescricaoPublica(),
                formulario.getCaracteristicaSensivel(),
                formulario.getLocalId(),
                formulario.getDataEncontro());

        return "redirect:/objetos/" + id;
    }

    private void adicionarListasDeApoio(Model model) {
        model.addAttribute("categorias", Categoria.values());
        model.addAttribute("locais", objetoService.listarLocais());
    }
}
