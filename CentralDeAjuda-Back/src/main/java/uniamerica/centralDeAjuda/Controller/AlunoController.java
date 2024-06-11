package uniamerica.centralDeAjuda.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniamerica.centralDeAjuda.DTO.AlunoDTO;
import uniamerica.centralDeAjuda.DTO.MensagemDTO;
import uniamerica.centralDeAjuda.Services.AlunoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/aluno")
@CrossOrigin(origins = "*")
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
    public ResponseEntity<MensagemDTO> cadastrarAluno(@RequestBody Map<String, Object> payload) {
        try{
            AlunoDTO alunoDTO = new ObjectMapper().convertValue(payload.get("aluno"), AlunoDTO.class);
            String userCreacao = (String) payload.get("userCreacao");
            return ResponseEntity.ok(alunoService.cadastrarAluno(alunoDTO, userCreacao));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemDTO> editarAluno(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            AlunoDTO alunoDTO = new ObjectMapper().convertValue(payload.get("aluno"), AlunoDTO.class);
            String userAlteracao = (String) payload.get("userAlteracao");
            return ResponseEntity.ok(alunoService.editarAluno(id, alunoDTO, userAlteracao));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id, @RequestParam String userExclusao) {
        try {
            return ResponseEntity.ok(alunoService.deletar(id, userExclusao));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }
}