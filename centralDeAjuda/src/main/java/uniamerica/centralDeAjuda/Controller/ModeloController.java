package uniamerica.centralDeAjuda.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniamerica.centralDeAjuda.DTO.MarcaDTO;
import uniamerica.centralDeAjuda.DTO.ModeloDTO;
import uniamerica.centralDeAjuda.Services.ModeloService;

import java.util.List;

@RestController
@RequestMapping("modelo")
public class ModeloController {

    @Autowired
    private ModeloService modeloService;

    @GetMapping("/lista")
    public List<ModeloDTO> listar(){
        return modeloService.listar();
    }
    @GetMapping("/{id}")
    public ModeloDTO findById(@PathVariable Long id){
        return modeloService.findById(id);
    }
    @PostMapping
    public ModeloDTO cadastar(@RequestBody ModeloDTO modeloDTO){

        return modeloService.cadastrar(modeloDTO);
    }

    @PutMapping("/{id}")
    public ModeloDTO editar(@PathVariable Long id, @RequestBody ModeloDTO modeloDTO){
        return modeloService.editar(id, modeloDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        modeloService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
