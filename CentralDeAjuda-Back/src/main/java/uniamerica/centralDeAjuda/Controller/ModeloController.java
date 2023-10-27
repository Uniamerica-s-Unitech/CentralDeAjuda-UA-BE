package uniamerica.centralDeAjuda.Controller;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
    public ResponseEntity<String> cadastrarModelo(@RequestBody ModeloDTO modeloDTO) {
        try{
            return ResponseEntity.ok(modeloService.cadastrarModelo(modeloDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarModelo(@PathVariable Long id, @RequestBody ModeloDTO modeloDTO) {
        try {
            return ResponseEntity.ok(modeloService.editarModelo(id, modeloDTO));
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
            modeloService.deletar(id);
            return ResponseEntity.ok("Modelo deletado com sucesso!");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}