package uniamerica.centralDeAjuda.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniamerica.centralDeAjuda.DTO.LaptopDTO;
import uniamerica.centralDeAjuda.Services.LaptopService;

import java.util.List;
@RestController
@RequestMapping("/laptop")
public class LaptopController {

    @Autowired
    private LaptopService laptopService;

    @GetMapping("/lista")
    public List<LaptopDTO> listar(){
        return laptopService.listar();
    }
    @GetMapping("/{id}")
    public LaptopDTO findById(@PathVariable Long id) {
        return laptopService.findById(id);
    }

    @PostMapping
    public LaptopDTO cadastrar(@RequestBody LaptopDTO laptopDTO) {
        return laptopService.cadastrar(laptopDTO);
    }

    @PutMapping("/{id}")
    public LaptopDTO editar(@PathVariable Long id, @RequestBody LaptopDTO laptopDTO) {
        return laptopService.editar(id, laptopDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        laptopService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
