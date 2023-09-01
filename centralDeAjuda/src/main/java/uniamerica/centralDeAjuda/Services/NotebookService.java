package uniamerica.centralDeAjuda.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uniamerica.centralDeAjuda.DTO.NotebookDTO;
import uniamerica.centralDeAjuda.Entity.Notebook;
import uniamerica.centralDeAjuda.Repository.ModeloRepository;
import uniamerica.centralDeAjuda.Repository.NotebookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotebookService {
    @Autowired
    private NotebookRepository notebookRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    public List<NotebookDTO> listar() {
        List<Notebook> notebooks = notebookRepository.findAll();
        return notebooks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public NotebookDTO findById(Long id) {
        Notebook notebook = notebookRepository.findById(id).orElse(null);
        return convertToDTO(notebook);
    }

    public NotebookDTO cadastrar(NotebookDTO notebookDTO) {
        Notebook notebook = new Notebook();
        BeanUtils.copyProperties(notebookDTO, notebook);
        Assert.isTrue(notebook.getPatrimonio() != null, "Id patrimonio invalido");
        Assert.isTrue(this.notebookRepository.findByIdPatrimonio(notebook.getPatrimonio()).isEmpty(),"Já existe esse id");
        Assert.isTrue(this.modeloRepository.findById(notebook.getModelo_id().getId()).isEmpty(),"Modelo não existe");
        notebook = notebookRepository.save(notebook);
        return convertToDTO(notebook);
    }

    public NotebookDTO editar(Long id, NotebookDTO notebookDTO) {
        if (notebookRepository.existsById(id)) {
            Notebook notebook = new Notebook();
            BeanUtils.copyProperties(notebookDTO, notebook);
            notebook.setId(id);
            notebook = notebookRepository.save(notebook);
            return convertToDTO(notebook);
        }
        return null;
    }

    public void deletar(Long id) {
        notebookRepository.deleteById(id);
    }

    private NotebookDTO convertToDTO(Notebook notebook) {
        NotebookDTO notebookDTO = new NotebookDTO();
        BeanUtils.copyProperties(notebook, notebookDTO);
        return notebookDTO;
    }
}
