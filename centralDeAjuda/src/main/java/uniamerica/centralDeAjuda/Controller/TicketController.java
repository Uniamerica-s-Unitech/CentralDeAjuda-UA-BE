package uniamerica.centralDeAjuda.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uniamerica.centralDeAjuda.DTO.TicketDTO;
import uniamerica.centralDeAjuda.Services.TicketService;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/lista")
    public List<TicketDTO> listar(){
        return ticketService.listar();
    }

    @GetMapping("/abertos")
    public List<TicketDTO> buscarTicketsAbertos() {
        return ticketService.buscarTicketsAbertos();
    }

    @GetMapping("/historico")
    public List<TicketDTO> buscarHistoricoPorDataDevolucao() {
        return ticketService.buscarHistoricoPorDataDevolucao();
    }

    @PostMapping
    public TicketDTO cadastrar(@RequestBody TicketDTO ticketDTO) {
        return ticketService.cadastrar(ticketDTO);
    }

    @PutMapping("/{id}")
    public TicketDTO editar(@RequestBody TicketDTO ticketDTO,@PathVariable Long id) {
        return ticketService.editar(id, ticketDTO);
    }
}
