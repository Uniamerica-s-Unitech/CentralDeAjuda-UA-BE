package uniamerica.centralDeAjuda.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uniamerica.centralDeAjuda.DTO.MarcaDTO;
import uniamerica.centralDeAjuda.Services.MarcaService;

import java.util.List;

@RestController
@RequestMapping("/marca")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;

    @GetMapping("/lista")
    public List<MarcaDTO> listar(){
        return marcaService.listar();
    }
    @GetMapping("/{id}")
    public MarcaDTO findById(@PathVariable Long id){
        return marcaService.findById(id);
    }
    @PostMapping
    public MarcaDTO cadastar(@RequestBody MarcaDTO marcaDTO){
        return marcaService.cadastar(marcaDTO);
    }


}
