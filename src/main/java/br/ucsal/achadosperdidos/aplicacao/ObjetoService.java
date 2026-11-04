package br.ucsal.achadosperdidos.aplicacao;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ucsal.achadosperdidos.dominio.objeto.Categoria;
import br.ucsal.achadosperdidos.dominio.objeto.Local;
import br.ucsal.achadosperdidos.dominio.objeto.Objeto;
import br.ucsal.achadosperdidos.dominio.objeto.StatusObjeto;
import br.ucsal.achadosperdidos.infraestrutura.LocalRepository;
import br.ucsal.achadosperdidos.infraestrutura.ObjetoRepository;

/**
 * Orquestra o registro e as consultas de {@link Objeto} (RF01/RF02/RF04).
 * O controller permanece fino: a regra de negócio mora no domínio, aqui só
 * há coordenação de repositórios e montagem dos DTOs de saída.
 */
@Service
public class ObjetoService {

    private final ObjetoRepository objetoRepository;
    private final LocalRepository localRepository;

    public ObjetoService(ObjetoRepository objetoRepository, LocalRepository localRepository) {
        this.objetoRepository = objetoRepository;
        this.localRepository = localRepository;
    }

    /** RF01: registra um novo objeto encontrado, sempre DISPONIVEL (RN01). */
    @Transactional
    public Long registrar(Categoria categoria, String descricaoPublica, String caracteristicaSensivel,
                           Long localId, LocalDate dataEncontro) {
        Local local = localRepository.findById(localId)
                .orElseThrow(() -> new IllegalArgumentException("Local não encontrado."));

        Objeto objeto = new Objeto(categoria, descricaoPublica, caracteristicaSensivel, local, dataEncontro);

        return objetoRepository.save(objeto).getId();
    }

    /** RF02/RN02: lista apenas os objetos DISPONIVEL, mais recentes primeiro. */
    @Transactional(readOnly = true)
    public List<ObjetoResumoDTO> listarDisponiveis() {
        return objetoRepository.findByStatus(StatusObjeto.DISPONIVEL).stream()
                .sorted(Comparator.comparing(Objeto::getDataEncontro).reversed())
                .map(ObjetoResumoDTO::deObjeto)
                .toList();
    }

    /** RF04: detalhe de um objeto, sem a característica sensível (RN05). */
    @Transactional(readOnly = true)
    public Optional<ObjetoDetalheDTO> buscarDetalhe(Long id) {
        return objetoRepository.findById(id).map(ObjetoDetalheDTO::deObjeto);
    }

    /** Locais cadastrados, para popular o formulário de registro. */
    @Transactional(readOnly = true)
    public List<Local> listarLocais() {
        return localRepository.findAll();
    }
}
