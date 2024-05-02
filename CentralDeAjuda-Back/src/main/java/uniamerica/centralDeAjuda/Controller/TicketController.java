package uniamerica.centralDeAjuda.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uniamerica.centralDeAjuda.DTO.MensagemDTO;
import uniamerica.centralDeAjuda.DTO.TicketDTO;
import uniamerica.centralDeAjuda.Entity.Ticket;
import uniamerica.centralDeAjuda.Services.TicketService;

import java.util.List;

@RestController
@RequestMapping("/ticket")
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
    public ResponseEntity<MensagemDTO> cadastrarTicket(@RequestBody TicketDTO ticketDTO) {
        try{
            return ResponseEntity.ok(ticketService.cadastrarTicket(ticketDTO));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemDTO> editarTicket(@PathVariable Long id, @RequestBody TicketDTO ticketDTO) {
        try {
            return ResponseEntity.ok(ticketService.editarTicket(id, ticketDTO));
        } catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ticketService.cancelar(id));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }
}