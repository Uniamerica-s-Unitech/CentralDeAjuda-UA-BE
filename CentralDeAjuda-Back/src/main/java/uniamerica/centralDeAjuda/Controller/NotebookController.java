package uniamerica.centralDeAjuda.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniamerica.centralDeAjuda.DTO.MensagemDTO;
import uniamerica.centralDeAjuda.DTO.NotebookDTO;
import uniamerica.centralDeAjuda.Services.NotebookService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notebook")
@CrossOrigin(origins = "*")
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
    public ResponseEntity<MensagemDTO> cadastrarNotebook(@RequestBody Map<String, Object> payload) {
        try{
            NotebookDTO notebookDTO = new ObjectMapper().convertValue(payload.get("notebook"), NotebookDTO.class);
            String userCreacao = (String) payload.get("userCreacao");
            return ResponseEntity.ok(notebookService.cadastrarNotebook(notebookDTO, userCreacao));
        } catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemDTO> editarNotebook(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            NotebookDTO notebookDTO = new ObjectMapper().convertValue(payload.get("notebook"), NotebookDTO.class);
            String userAlteracao = (String) payload.get("userAlteracao");
            return ResponseEntity.ok(notebookService.cadastrarNotebook(notebookDTO, userAlteracao));
        } catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id, @RequestParam String userExclusao) {
        try {
            return ResponseEntity.ok(notebookService.deletar(id, userExclusao));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

}