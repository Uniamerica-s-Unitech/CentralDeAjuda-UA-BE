package uniamerica.centralDeAjuda.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniamerica.centralDeAjuda.DTO.MarcaDTO;
import uniamerica.centralDeAjuda.DTO.MensagemDTO;
import uniamerica.centralDeAjuda.Services.MarcaService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/marca")
@CrossOrigin(origins = "*")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;

    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> buscarPorId(@PathVariable Long id) {
        MarcaDTO marcaDTO = marcaService.findMarcaById(id);
        if (marcaDTO != null) {
            return ResponseEntity.ok(marcaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lista")
    public List<MarcaDTO> listar(){
        return marcaService.listar();
    }

    @PostMapping
    public ResponseEntity<MensagemDTO> cadastrarMarca(@RequestBody Map<String, Object> payload) {
        try{
            MarcaDTO marcaDTO = new ObjectMapper().convertValue(payload.get("marca"), MarcaDTO.class);
            String userCreacao = (String) payload.get("userCreacao");
            return ResponseEntity.ok(marcaService.cadastrarMarca(marcaDTO, userCreacao));
        } catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemDTO> editarMarca(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            MarcaDTO marcaDTO = new ObjectMapper().convertValue(payload.get("marca"), MarcaDTO.class);
            String userAlteracao = (String) payload.get("userAlteracao");
            return ResponseEntity.ok(marcaService.editarMarca(id, marcaDTO, userAlteracao));
        } catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id, @RequestParam String userExclusao) {
        try {
            return ResponseEntity.ok(marcaService.deletar(id, userExclusao));
        } catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }
}