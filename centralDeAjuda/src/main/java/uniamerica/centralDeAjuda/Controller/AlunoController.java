package uniamerica.centralDeAjuda.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniamerica.centralDeAjuda.DTO.AlunoDTO;
import uniamerica.centralDeAjuda.Services.AlunoService;

import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService service;

    @GetMapping("/lista")
    public List<AlunoDTO> listar(){
        return service.listar();
    }
    @GetMapping("/{id}")
    public AlunoDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public AlunoDTO cadastrar(@RequestBody AlunoDTO alunoDTO) {
        return service.cadastrar(alunoDTO);
    }

    @PutMapping("/{id}")
    public AlunoDTO editar(@PathVariable Long id, @RequestBody AlunoDTO alunoDTO) {
        return service.editar(id, alunoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
