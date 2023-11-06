package uniamerica.centralDeAjuda.Services;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uniamerica.centralDeAjuda.DTO.MarcaDTO;
import uniamerica.centralDeAjuda.DTO.MensagemDTO;
import uniamerica.centralDeAjuda.DTO.ModeloDTO;
import uniamerica.centralDeAjuda.Entity.Marca;
import uniamerica.centralDeAjuda.Entity.Modelo;
import uniamerica.centralDeAjuda.Entity.Notebook;
import uniamerica.centralDeAjuda.Repository.MarcaRepository;
import uniamerica.centralDeAjuda.Repository.ModeloRepository;
import uniamerica.centralDeAjuda.Repository.NotebookRepository;

import java.util.List;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private NotebookRepository notebookRepository;

    public ModeloDTO findModeloById(Long id) {
        Modelo modelo = modeloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Modelo não encontrado!"));
        return modeloToDTO(modelo);
    }

    public List<ModeloDTO> listar() {
        return modeloRepository.findModeloByAtivo().stream().map(this::modeloToDTO).toList();
    }

    public MensagemDTO cadastrarModelo(ModeloDTO modeloDTO) {
        Modelo modelo = toModelo(modeloDTO);

        Assert.notNull(modelo.getNome(),"Nome inválido!");

        Assert.notNull(modelo.getMarcaId(),"Marca invalida!");
        Assert.isTrue(!marcaRepository.findById
                (modelo.getMarcaId().getId()).isEmpty(),"Marca não existe!");

        modeloRepository.save(modelo);
        return new MensagemDTO("Modelo cadastrado com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO editarModelo(Long id, ModeloDTO modeloDTO) {
        Modelo modelo = toModelo(modeloDTO);

        Assert.notNull(modelo.getNome(), "Nome inválido!");

        Assert.notNull(modelo.getMarcaId(),"Marca invalida!");

        modeloRepository.save(modelo);
        return new MensagemDTO("Modelo atualizado com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO deletar(Long id) throws Exception{
        Modelo modeloBanco = modeloRepository.findById(id)
                .orElseThrow(()->
                        new EntityNotFoundException("Modelo com ID "+id+" nao existe!"));

        List<Notebook> modeloNotebookAtivo = notebookRepository.findNotebookByModeloAtivo(modeloBanco);

        if (!modeloNotebookAtivo.isEmpty()){
            throw new Exception("Não é possível excluir esse modelo, pois existem notebooks ativos associados a ele.");
        } else {
            desativarModelo(modeloBanco);
        }
        return new MensagemDTO("Modelo deletado com sucesso!", HttpStatus.CREATED);
    }

    private void desativarModelo(Modelo modelo) {
        modelo.setAtivo(false);
        modeloRepository.save(modelo);
    }

    public ModeloDTO modeloToDTO(Modelo modelo){
        ModeloDTO modeloDTO = new ModeloDTO();

        modeloDTO.setId(modelo.getId());
        modeloDTO.setNome(modelo.getNome());

        MarcaDTO marcaDTO = new MarcaDTO();

        marcaDTO.setId(modelo.getMarcaId().getId());
        marcaDTO.setNome(modelo.getMarcaId().getNome());

        modeloDTO.setMarcaId(marcaDTO);
        return modeloDTO;
    }

    public Modelo toModelo(ModeloDTO modeloDTO){
        Modelo novoModelo = new Modelo();

        novoModelo.setId(modeloDTO.getId());
        novoModelo.setNome(modeloDTO.getNome());

        Marca marca = new Marca();

        marca.setId(modeloDTO.getMarcaId().getId());

        novoModelo.setMarcaId(marca);

        return novoModelo;
    }
}