import { Component, EventEmitter, Output, inject } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Marca } from 'src/app/models/marca';
import { Mensagem } from 'src/app/models/mensagem';
import { Modelo } from 'src/app/models/modelo';
import { Ticket } from 'src/app/models/ticket';
import { TicketService } from 'src/app/services/ticket.service';


@Component({
  selector: 'app-ticket-lista',
  templateUrl: './ticket-lista.component.html',
  styleUrls: ['./ticket-lista.component.scss']
})
export class TicketListaComponent {
  @Output() retorno = new EventEmitter<any>();

  listaTicketsOriginal: Ticket[] = [];
  listaTicketsFiltrada: Ticket[] = [];

  ticketParaEditar: Ticket = new Ticket();
  ticketParaExcluir: Ticket = new Ticket();
  indiceParaEdicao!: number;
  modoModal!: number; //1: editar, 2: finalizar, 3: cancelar

  modalService = inject(NgbModal);
  ticketService = inject(TicketService);

  tituloModal!: string;
  termoPesquisa!: "";

  constructor(){
    this.listarTickets();
  }

  listarTickets(){
    this.ticketService.listarAbertos().subscribe({
      next: lista =>{
        this.listaTicketsOriginal = lista;
        this.listaTicketsFiltrada = lista;
      }
    })
  }

  atualizarListaTicket(menssagem : Mensagem){
    this.modalService.dismissAll();
    this.listarTickets();
    this.retorno.emit("ok");
  }

  cadastrarTicket(modalTicket : any){
    this.modoModal = 1; //estou passando o 1 pro detalhe saber q vou cadastrar

    this.ticketParaEditar = new Ticket();
    this.modalService.open(modalTicket,{size: 'md'});

    this.tituloModal = "Cadastrar Ticket";
  }

  finalizarTicket(modal:any,ticket:Ticket,indice:number){
    this.ticketParaEditar = Object.assign({}, ticket);
    this.indiceParaEdicao = indice;
    this.modoModal = 2; //estou passando o 2 pro detalhe saber q vou finalizar

    this.modalService.open(modal, {size: 'md'});
    this.tituloModal = "Finalizar Ticket";
  }

  cancelarTicket(modal:any,ticket:Ticket,indice:number){
    this.ticketParaEditar = Object.assign({}, ticket);
    this.indiceParaEdicao = indice;

    this.modoModal = 3; //estou passando o 3 pro detalhe saber q vou cancelar

    this.modalService.open(modal, {size: 'md'});
    this.tituloModal = "Excluir Ticket";
  }

  @Output() realizarPesquisa(termoPesquisa: string) {
    termoPesquisa.toLowerCase();
    if (!termoPesquisa) {
      this.listaTicketsFiltrada = this.listaTicketsOriginal;
    } else {
      this.listaTicketsFiltrada = this.listaTicketsOriginal.filter((ticket: Ticket) => {
        const alunoNome = ticket.alunoId.nome.toLowerCase();
        const alunoRa = ticket.alunoId.ra;
        const notebook = ticket.notebookId.patrimonio.toLowerCase();
        return (
          notebook.includes(termoPesquisa) ||
          alunoNome.includes(termoPesquisa)
        );
      });
    }
  }

  @Output() realizarPesquisaPorModelo(filterModelo: Modelo) {
    if (filterModelo == null) {
      this.listaTicketsFiltrada = this.listaTicketsOriginal;
    } else {
      this.listaTicketsFiltrada = this.listaTicketsOriginal.filter((ticket: Ticket) => {
        const idModelo = ticket.notebookId.modeloId.id;
        return (
          idModelo === filterModelo.id
        );
      });
    }
  }

  @Output() realizarPesquisaPorMarca(filterMarca: Marca) {
    if (filterMarca == null) {
      this.listaTicketsFiltrada = this.listaTicketsOriginal;
    } else {
      this.listaTicketsFiltrada = this.listaTicketsOriginal.filter((ticket: Ticket) => {
        const idModelo = ticket.notebookId.modeloId.marcaId.id;
        return (
          idModelo === filterMarca.id
        );
      });
    }
  }
}
