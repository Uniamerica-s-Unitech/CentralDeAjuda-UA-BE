import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { Aluno } from 'src/app/models/aluno';
import { Mensagem } from 'src/app/models/mensagem';
import { Notebook } from 'src/app/models/notebook';
import { Ticket } from 'src/app/models/ticket';
import { AlunoService } from 'src/app/services/aluno.service';
import { NotebookService } from 'src/app/services/notebook.service';
import { TicketService } from 'src/app/services/ticket.service';

@Component({
  selector: 'app-ticket-detalhes',
  templateUrl: './ticket-detalhes.component.html',
  styleUrls: ['./ticket-detalhes.component.scss']
})
export class TicketDetalhesComponent {
  @Input() ticket : Ticket = new Ticket();
  @Output() retorno = new EventEmitter<Mensagem>;

  ticketService = inject(TicketService);
  notebookService = inject(NotebookService);
  alunoService = inject(AlunoService);

  listaNotebooks: Notebook[] = [];
  listaAlunos: Aluno[] = [];

  constructor() {
    this.carregarAlunos();
    this.carregarNotebooks();
  }

  carregarNotebooks() {
    this.notebookService.listar().subscribe({
      next: lista => {
        this.listaNotebooks = lista;
      }
    });
  }
  carregarAlunos() {
    this.alunoService.listar().subscribe({
      next: lista => {
        this.listaAlunos = lista;
      }
    });
  }

  salvar() {
    this.ticketService.save(this.ticket).subscribe({
      next: mensagem => { // QUANDO DÁ CERTO
        this.retorno.emit(mensagem);
      },
      error: erro => { // QUANDO DÁ ERRO
        alert('Exemplo de tratamento de erro/exception! Observe o erro no console!');
        console.error(erro);
      }
    });
  }

  byId(item1: any, item2: any){
    if(item1 != null && item2 != null){
      return item1.id === item2.id;
    }else{
      return item1 === item2;
    }
  }
}
