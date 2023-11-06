package uniamerica.centralDeAjuda.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniamerica.centralDeAjuda.DTO.MensagemDTO;
import uniamerica.centralDeAjuda.DTO.NotebookDTO;
import uniamerica.centralDeAjuda.Services.NotebookService;

import java.util.List;
@RestController
@RequestMapping("/notebook")
@CrossOrigin(origins = "http://localhost:4200")
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
    @GetMapping("/listaSemVinculo")
    public List<NotebookDTO> listarSemVinculo(){
        return notebookService.listarSemVinculo();
    }


    @GetMapping("/lista")
    public List<NotebookDTO> listar(){
        return notebookService.listar();
    }

    @PostMapping
    public ResponseEntity<MensagemDTO> cadastrarNotebook(@RequestBody NotebookDTO notebookDTO) {
        try{
            return ResponseEntity.ok(notebookService.cadastrarNotebook(notebookDTO));
        } catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemDTO> editarNotebook(@PathVariable Long id, @RequestBody NotebookDTO notebookDTO) {
        try {
            return ResponseEntity.ok(notebookService.editarNotebook(id, notebookDTO));
        } catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(notebookService.deletar(id));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

}