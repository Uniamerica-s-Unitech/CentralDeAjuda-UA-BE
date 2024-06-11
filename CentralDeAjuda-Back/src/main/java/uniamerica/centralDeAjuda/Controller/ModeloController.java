package uniamerica.centralDeAjuda.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniamerica.centralDeAjuda.DTO.MensagemDTO;
import uniamerica.centralDeAjuda.DTO.ModeloDTO;
import uniamerica.centralDeAjuda.Services.ModeloService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/modelo")
@CrossOrigin(origins = "*")
public class ModeloController {

    @Autowired
    private ModeloService modeloService;

    @GetMapping("/{id}")
    public ResponseEntity<ModeloDTO> buscarPorId(@PathVariable Long id) {
        ModeloDTO modeloDTO = modeloService.findModeloById(id);
        if (modeloDTO != null) {
            return ResponseEntity.ok(modeloDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lista")
    public List<ModeloDTO> listar(){
        return modeloService.listar();
    }

    @PostMapping
    public ResponseEntity<MensagemDTO> cadastrarModelo(@RequestBody Map<String, Object> payload) {
        try{
            ModeloDTO modeloDTO = new ObjectMapper().convertValue(payload.get("modelo"), ModeloDTO.class);
            String userCreacao = (String) payload.get("userCreacao");
            return ResponseEntity.ok(modeloService.cadastrarModelo(modeloDTO, userCreacao));
        } catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemDTO> editarModelo(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            ModeloDTO modeloDTO = new ObjectMapper().convertValue(payload.get("modelo"), ModeloDTO.class);
            String userAlteracao = (String) payload.get("userAlteracao");
            return ResponseEntity.ok(modeloService.editarModelo(id, modeloDTO, userAlteracao));
        } catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id, @RequestParam String userExclusao) {
        try {
            return ResponseEntity.ok(modeloService.deletar(id, userExclusao));
        } catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }
}