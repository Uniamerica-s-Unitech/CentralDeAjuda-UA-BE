package uniamerica.centralDeAjuda.Services;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uniamerica.centralDeAjuda.DTO.MarcaDTO;
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

    public ModeloDTO findModeloByid(Long id) {
        Modelo modelo = modeloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Modelo não encontrado!"));
        return modeloToDTO(modelo);
    }

    public List<ModeloDTO> listar() {
        return modeloRepository.findModeloByAtivo().stream().map(this::modeloToDTO).toList();
    }

    public String cadastrarModelo(ModeloDTO modeloDTO) {
        Modelo modelo = toModelo(modeloDTO);

        Assert.notNull(modelo.getNome(),"Nome inválido!");

        Assert.notNull(modelo.getMarcaId(),"Marca invalida!");
        Assert.isTrue(!marcaRepository.findById
                (modelo.getMarcaId().getId()).isEmpty(),"Marca não existe!");

        modeloRepository.save(modelo);
        return "Modelo cadastrado com sucesso!";
    }

    public String editarModelo(Long id, ModeloDTO modeloDTO) {
        if (modeloRepository.existsById(id)) {
            Modelo modelo = toModelo(modeloDTO);

            Assert.notNull(modelo.getNome(), "Nome inválido!");

            Assert.notNull(modelo.getMarcaId(),"Marca invalida!");
            Assert.isTrue(!marcaRepository.findById
                            (modelo.getMarcaId().getId()).isEmpty(),"Marca não existe!");

            modeloRepository.save(modelo);
            return "Modelo atualizado com sucesso!";

        }else {
            throw new IllegalArgumentException("Modelo não encontrado com o ID fornecido: " + id);
        }
    }

    public void deletar(Long id) {
        Modelo modeloBanco = modeloRepository.findById(id)
                .orElseThrow(()->
                        new EntityNotFoundException("Modelo com ID "+id+" nao existe!"));

        List<Notebook> modeloNotebookAtivo = notebookRepository.findNotebookByModeloAtivo(modeloBanco);

        if (!modeloNotebookAtivo.isEmpty()){
            throw new IllegalArgumentException("Não é possível excluir esse modelo tem notebook ativo!");
        } else {
            desativarModelo(modeloBanco);
        }
    }

    private void desativarModelo(Modelo modelo) {
        modelo.setAtivo(false);
        modeloRepository.save(modelo);
    }

    public ModeloDTO modeloToDTO(Modelo modelo){
        ModeloDTO modeloDTO = new ModeloDTO();

        modeloDTO.setId(modelo.getId());
        modeloDTO.setAtivo(modelo.getAtivo());
        modeloDTO.setNome(modelo.getNome());

        MarcaDTO marcaDTO = new MarcaDTO();

        marcaDTO.setId(modelo.getMarcaId().getId());
        marcaDTO.setAtivo(modelo.getMarcaId().getAtivo());
        marcaDTO.setNome(modelo.getMarcaId().getNome());

        modeloDTO.setMarcaId(marcaDTO);
        return modeloDTO;
    }

    public Modelo toModelo(ModeloDTO modeloDTO){
        Modelo novoModelo = new Modelo();

        novoModelo.setId(modeloDTO.getId());
        novoModelo.setAtivo(modeloDTO.getAtivo());
        novoModelo.setNome(modeloDTO.getNome());

        Marca marca = new Marca();

        marca.setId(modeloDTO.getMarcaId().getId());

        novoModelo.setMarcaId(marca);

        return novoModelo;
    }
}