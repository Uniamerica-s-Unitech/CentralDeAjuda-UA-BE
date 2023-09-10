package uniamerica.centralDeAjuda.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniamerica.centralDeAjuda.DTO.NotebookDTO;
import uniamerica.centralDeAjuda.Services.NotebookService;

import java.util.List;
@RestController
@RequestMapping("/notebook")
public class NotebookController {

    @Autowired
    private NotebookService notebookService;

    @GetMapping("/lista")
    public List<NotebookDTO> listar(){
        return notebookService.listar();
    }

    @PostMapping
    public NotebookDTO cadastrar(@RequestBody NotebookDTO notebookDTO) {
        return notebookService.cadastrar(notebookDTO);
    }

    @PutMapping("/{id}")
    public NotebookDTO editar(@PathVariable Long id, @RequestBody NotebookDTO notebookDTO) {
        return notebookService.editar(id, notebookDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        notebookService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
