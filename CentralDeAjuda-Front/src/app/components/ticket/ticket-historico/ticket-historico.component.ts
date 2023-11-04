import { Component, Output, inject } from '@angular/core';
import { Marca } from 'src/app/models/marca';
import { Modelo } from 'src/app/models/modelo';
import { Ticket } from 'src/app/models/ticket';
import { MarcaService } from 'src/app/services/marca.service';
import { ModeloService } from 'src/app/services/modelo.service';
import { TicketService } from 'src/app/services/ticket.service';

@Component({
  selector: 'app-ticket-historico',
  templateUrl: './ticket-historico.component.html',
  styleUrls: ['./ticket-historico.component.scss']
})
export class TicketHistoricoComponent {
  listaTicketsHistoricosOriginal: Ticket[] = [];
  listaTicketsHistoricosFiltrada: Ticket[] = [];
  listaModelos : Modelo[] = [];
  listaMarcas : Marca[] = [];

  ticketService = inject(TicketService);
  modeloService = inject(ModeloService);
  marcaService = inject(MarcaService);

  filterModelo!: Modelo;
  filterMarca!: Marca;

  termoPesquisa!: "";

  constructor(){
    this.listarTickets();
    this.carregarMarcas();
    this.carregarModelos();
  }

  listarTickets(){
    this.ticketService.listarHistoricos().subscribe({
      next: lista =>{
        this.listaTicketsHistoricosOriginal = lista;
        this.listaTicketsHistoricosFiltrada = lista;
      }
    })
  }

  carregarModelos() {
    this.modeloService.listar().subscribe({
      next: lista => {
        this.listaModelos = lista;
      }
    });
  }
  carregarMarcas() {
    this.marcaService.listar().subscribe({
      next: lista => {
        this.listaMarcas = lista;
      }
    });
  }

  @Output() realizarPesquisa(termoPesquisa: string) {
    termoPesquisa.toLowerCase();
    if (!termoPesquisa) {
      this.listaTicketsHistoricosFiltrada = this.listaTicketsHistoricosOriginal;
    } else {
      this.listaTicketsHistoricosFiltrada = this.listaTicketsHistoricosOriginal.filter((ticket: Ticket) => {
        const alunoNome = ticket.alunoId.nome.toLowerCase();
        const alunoRa = ticket.alunoId.ra.toString().toLowerCase();
        const notebook = ticket.notebookId.patrimonio.toLowerCase();
        return (
          notebook.includes(termoPesquisa) ||
          alunoNome.includes(termoPesquisa) ||
          alunoRa.includes(termoPesquisa)
        );
      });
    }
  }

  @Output() realizarPesquisaPorModelo(filterModelo: Modelo) {
    if (filterModelo == null) {
      this.listaTicketsHistoricosFiltrada = this.listaTicketsHistoricosOriginal;
    } else {
      this.listaTicketsHistoricosFiltrada = this.listaTicketsHistoricosOriginal.filter((ticket: Ticket) => {
        const idModelo = ticket.notebookId.modeloId.id;
        return (
          idModelo === filterModelo.id
        );
      });
    }
  }

  @Output() realizarPesquisaPorMarca(filterMarca: Marca) {
    if (filterMarca == null) {
      this.listaTicketsHistoricosFiltrada = this.listaTicketsHistoricosOriginal;
    } else {
      this.listaTicketsHistoricosFiltrada = this.listaTicketsHistoricosOriginal.filter((ticket: Ticket) => {
        const idModelo = ticket.notebookId.modeloId.marcaId.id;
        return (
          idModelo === filterMarca.id
        );
      });
    }
  }

  byId(item1: any, item2: any){
    if(item1 != null && item2 != null){
      return item1.id === item2.id;
    }else{
      return item1 === item2;
    }
  }
}
