package uniamerica.centralDeAjuda.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uniamerica.centralDeAjuda.DTO.LaptopDTO;
import uniamerica.centralDeAjuda.Entity.Notebook;
import uniamerica.centralDeAjuda.Repository.NotebookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotebookService {
    @Autowired
    private NotebookRepository notebookRepository;

    public List<LaptopDTO> listar() {
        List<Notebook> notebooks = notebookRepository.findAll();
        return notebooks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LaptopDTO findById(Long id) {
        Notebook notebook = notebookRepository.findById(id).orElse(null);
        return convertToDTO(notebook);
    }

    public LaptopDTO cadastrar(LaptopDTO laptopDTO) {
        Notebook notebook = new Notebook();
        BeanUtils.copyProperties(laptopDTO, notebook);
        Assert.isTrue(notebook.getPatrimonio() != null, "Id patrimonio invalido");
        Assert.isTrue(this.notebookRepository.findByIdPatrimonio(notebook.getPatrimonio()).isEmpty(),"Já existe esse id");
        notebook = notebookRepository.save(notebook);
        return convertToDTO(notebook);
    }

    public LaptopDTO editar(Long id, LaptopDTO laptopDTO) {
        if (notebookRepository.existsById(id)) {
            Notebook notebook = new Notebook();
            BeanUtils.copyProperties(laptopDTO, notebook);
            notebook.setId(id);
            notebook = notebookRepository.save(notebook);
            return convertToDTO(notebook);
        }
        return null;
    }

    public void deletar(Long id) {
        notebookRepository.deleteById(id);
    }

    private LaptopDTO convertToDTO(Notebook notebook) {
        LaptopDTO laptopDTO = new LaptopDTO();
        BeanUtils.copyProperties(notebook, laptopDTO);
        return laptopDTO;
    }
}
