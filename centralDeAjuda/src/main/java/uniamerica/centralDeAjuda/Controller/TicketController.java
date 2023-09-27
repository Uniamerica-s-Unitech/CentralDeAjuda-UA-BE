package uniamerica.centralDeAjuda.Controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uniamerica.centralDeAjuda.DTO.TicketDTO;
import uniamerica.centralDeAjuda.Services.TicketService;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/abertos")
    public List<TicketDTO> buscarTicketsAbertos(){
        return ticketService.buscarTicketsAbertos();
    }

    @GetMapping("/historico")
    public List<TicketDTO> buscarHistoricoPorDataDevolucao() {
        return ticketService.buscarHistoricoPorDataDevolucao();
    }

        @PostMapping
        public ResponseEntity<String> cadastrarAluno(@RequestBody TicketDTO ticketDTO) {
            try{
                return ResponseEntity.ok(ticketService.cadastrarTicket(ticketDTO));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (EntityNotFoundException e) {
                return ResponseEntity.notFound().build();
            } catch(Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        }

        @PutMapping("/{id}")
        public ResponseEntity<String> editarAluno(@PathVariable Long id, @RequestBody TicketDTO ticketDTO) {
            try {
                return ResponseEntity.ok(ticketService.editarTicket(id, ticketDTO));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (EntityNotFoundException e) {
                return ResponseEntity.notFound().build();
            } catch(Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        }
}
