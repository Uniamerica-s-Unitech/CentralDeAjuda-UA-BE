package uniamerica.centralDeAjuda.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniamerica.centralDeAjuda.DTO.AlunoDTO;
import uniamerica.centralDeAjuda.DTO.MensagemDTO;
import uniamerica.centralDeAjuda.Services.AlunoService;

import java.util.List;

@RestController
@RequestMapping("/aluno")
@CrossOrigin(origins = "http://localhost:4200")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> buscarPorId(@PathVariable Long id) {
        AlunoDTO alunoDTO = alunoService.findAlunoById(id);
        if (alunoDTO != null) {
            return ResponseEntity.ok(alunoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listaSemVinculo")
    public List<AlunoDTO> listarSemVinculo(){
        return alunoService.listarSemVinculo();
    }

    @GetMapping("/lista")
    public List<AlunoDTO> listar(){
        return alunoService.listar();
    }

    @PostMapping
    public ResponseEntity<MensagemDTO> cadastrarAluno(@RequestBody AlunoDTO alunoDTO) {
        try{
            return ResponseEntity.ok(alunoService.cadastrarAluno(alunoDTO));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemDTO> editarAluno(@PathVariable Long id, @RequestBody AlunoDTO alunoDTO) {
        try {
            return ResponseEntity.ok(alunoService.editarAluno(id, alunoDTO));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(alunoService.deletar(id));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }
}