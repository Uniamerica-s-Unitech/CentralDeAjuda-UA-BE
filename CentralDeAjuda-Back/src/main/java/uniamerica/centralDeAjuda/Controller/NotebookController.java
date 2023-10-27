package uniamerica.centralDeAjuda.Controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uniamerica.centralDeAjuda.DTO.NotebookDTO;
import uniamerica.centralDeAjuda.Services.NotebookService;

import java.util.List;
@RestController
@RequestMapping("/notebook")
public class NotebookController {

    @Autowired
    private NotebookService notebookService;

    @GetMapping("/{id}")
    public ResponseEntity<NotebookDTO> buscarPorId(@PathVariable Long id) {
        NotebookDTO notebookDTO = notebookService.findNotebookById(id);
        if (notebookDTO != null) {
            return ResponseEntity.ok(notebookDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lista")
    public List<NotebookDTO> listar(){
        return notebookService.listar();
    }

    @PostMapping
    public ResponseEntity<String> cadastrarNotebook(@RequestBody NotebookDTO notebookDTO) {
        try{
            return ResponseEntity.ok(notebookService.cadastrarNotebook(notebookDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarNotebook(@PathVariable Long id, @RequestBody NotebookDTO notebookDTO) {
        try {
            return ResponseEntity.ok(notebookService.editarNotebook(id, notebookDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        try {
            notebookService.deletar(id);
            return ResponseEntity.ok("Notebook deletado com sucesso!");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}