package uniamerica.centralDeAjuda.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uniamerica.centralDeAjuda.DTO.TicketDTO;
import uniamerica.centralDeAjuda.Services.TicketService;

import java.time.LocalDateTime;
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

    @GetMapping("/historico")
    public List<TicketDTO> buscarHistoricoPorDataDevolucao(@RequestParam("dataDevolucao") LocalDateTime dataDevolucao) {
        return ticketService.buscarHistoricoPorDataDevolucao(dataDevolucao);
    }

    @PostMapping
    public TicketDTO cadastrar(@RequestBody TicketDTO ticketDTO) {
        return ticketService.cadastrar(ticketDTO);
    }

    @PutMapping("/{id}")
    public TicketDTO editar(@PathVariable Long id, @RequestBody TicketDTO ticketDTO) {
        return ticketService.editar(id, ticketDTO);
    }
}
