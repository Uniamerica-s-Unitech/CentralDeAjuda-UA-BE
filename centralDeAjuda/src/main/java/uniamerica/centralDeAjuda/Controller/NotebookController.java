package uniamerica.centralDeAjuda.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniamerica.centralDeAjuda.DTO.LaptopDTO;
import uniamerica.centralDeAjuda.Services.NotebookService;

import java.util.List;
@RestController
@RequestMapping("/laptop")
public class NotebookController {

    @Autowired
    private NotebookService notebookService;

    @GetMapping("/lista")
    public List<LaptopDTO> listar(){
        return notebookService.listar();
    }
    @GetMapping("/{id}")
    public LaptopDTO findById(@PathVariable Long id) {
        return notebookService.findById(id);
    }

    @PostMapping
    public LaptopDTO cadastrar(@RequestBody LaptopDTO laptopDTO) {
        return notebookService.cadastrar(laptopDTO);
    }

    @PutMapping("/{id}")
    public LaptopDTO editar(@PathVariable Long id, @RequestBody LaptopDTO laptopDTO) {
        return notebookService.editar(id, laptopDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        notebookService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
