package uniamerica.centralDeAjuda.Controller;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uniamerica.centralDeAjuda.DTO.MensagemDTO;
import uniamerica.centralDeAjuda.DTO.ModeloDTO;
import uniamerica.centralDeAjuda.Services.ModeloService;

import java.util.List;

@RestController
@RequestMapping("modelo")
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
    public ResponseEntity<MensagemDTO> cadastrarModelo(@RequestBody ModeloDTO modeloDTO) {
        try{
            return ResponseEntity.ok(modeloService.cadastrarModelo(modeloDTO));
        } catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemDTO> editarModelo(@PathVariable Long id, @RequestBody ModeloDTO modeloDTO) {
        try {
            return ResponseEntity.ok(modeloService.editarModelo(id, modeloDTO));
        } catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(modeloService.deletar(id));
        } catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }
}