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

    public NotebookDTO cadastrar(NotebookDTO notebookDTO) {
        Notebook notebook = new Notebook();
        BeanUtils.copyProperties(notebookDTO, notebook);

        Assert.notNull(notebook.getPatrimonio(),"Notebook inválido");
        if (!notebookRepository.findByIdPatrimonio(notebook.getPatrimonio()).isEmpty()){
            throw new IllegalArgumentException("Esse patrimonio ja existe");
        }

        if (modeloRepository.findById(notebook.getModeloId().getId()).isEmpty()) {
            throw new IllegalArgumentException("Modelo não existe");
        }

        notebook = notebookRepository.save(notebook);
        return convertToDTO(notebook);
    }

    public NotebookDTO editar(Long id, NotebookDTO notebookDTO) {
        if (notebookRepository.existsById(id)) {
            Notebook notebook = notebookRepository.findById(id).orElse(null);
            if (notebook != null){
                BeanUtils.copyProperties(notebookDTO, notebook,"id");

                Assert.notNull(notebook.getPatrimonio(),"Notebook inválido");
                if (!notebookRepository.findByIdPatrimonioPut(notebook.getPatrimonio(),id).isEmpty()){
                    throw new IllegalArgumentException("Esse patrimonio ja existe");
                }

                if (modeloRepository.findById(notebook.getModeloId().getId()).isEmpty()) {
                    throw new IllegalArgumentException("Modelo não existe");
                }

                notebook = notebookRepository.save(notebook);
                return convertToDTO(notebook);
            }
        }else {
            throw new IllegalArgumentException("Notebook não encontrado com o ID fornecido: " + id);
        }
        return null;
    }

    public void deletar(Long id) {
        if (notebookRepository.existsById(id)) {
                notebookRepository.deleteById(id);
        }
    }

    private NotebookDTO convertToDTO(Notebook notebook) {
        NotebookDTO notebookDTO = new NotebookDTO();
        BeanUtils.copyProperties(notebook, notebookDTO);
        return notebookDTO;
    }
}
