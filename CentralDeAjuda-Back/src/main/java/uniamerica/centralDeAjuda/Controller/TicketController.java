package uniamerica.centralDeAjuda.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniamerica.centralDeAjuda.DTO.AlunoDTO;
import uniamerica.centralDeAjuda.DTO.MensagemDTO;
import uniamerica.centralDeAjuda.DTO.TicketDTO;
import uniamerica.centralDeAjuda.Services.TicketService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> buscarPorId(@PathVariable Long id) {
        TicketDTO ticketDTO = ticketService.findTicketById(id);
        if (ticketDTO != null) {
            return ResponseEntity.ok(ticketDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/abertos")
    public List<TicketDTO> buscarTicketsAbertos(){
        return ticketService.buscarTicketsAbertos();
    }

    @GetMapping("/historico")
    public List<TicketDTO> buscarHistoricoPorDataDevolucao() {
        return ticketService.buscarHistoricoPorDataDevolucao();
    }

    @PostMapping
    public ResponseEntity<MensagemDTO> cadastrarTicket(@RequestBody Map<String, Object> payload) {
        try{
            TicketDTO ticketDTO = new ObjectMapper().convertValue(payload.get("ticket"), TicketDTO.class);
            String userCreacao = (String) payload.get("userCreacao");
            return ResponseEntity.ok(ticketService.cadastrarTicket(ticketDTO, userCreacao));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemDTO> editarTicket(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            TicketDTO ticketDTO = new ObjectMapper().convertValue(payload.get("ticket"), TicketDTO.class);
            String userAlteracao = (String) payload.get("userAlteracao");
            String userFinalizacao = (String) payload.get("userFinalizacao");
            return ResponseEntity.ok(ticketService.editarTicket(id, ticketDTO, userAlteracao, userFinalizacao));
        } catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id, @RequestParam String userExclusao) {
        try {
            return ResponseEntity.ok(ticketService.cancelar(id, userExclusao));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }
}