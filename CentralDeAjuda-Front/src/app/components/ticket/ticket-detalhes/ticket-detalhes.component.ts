import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { Aluno } from 'src/app/models/aluno';
import { Mensagem } from 'src/app/models/mensagem';
import { Notebook } from 'src/app/models/notebook';
import { Ticket } from 'src/app/models/ticket';
import { AlunoService } from 'src/app/services/aluno.service';
import { NotebookService } from 'src/app/services/notebook.service';
import { TicketService } from 'src/app/services/ticket.service';
import { ToastrService } from 'ngx-toastr';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-ticket-detalhes',
  templateUrl: './ticket-detalhes.component.html',
  styleUrls: ['./ticket-detalhes.component.scss']
})
export class TicketDetalhesComponent {
  @Input() modo: number = 1; //1: editar, 2: finalizar, 3: cancelar
  @Input() ticket: Ticket = new Ticket();
  @Output() retorno = new EventEmitter<Mensagem>;

  ticketService = inject(TicketService);
  notebookService = inject(NotebookService);
  alunoService = inject(AlunoService);
  toastr = inject(ToastrService);
  modalService = inject(NgbModal);
  modalRef!: NgbModalRef;

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
    if (this.modo == 1) //normal - cadastrando um novo
      this.ticket.dataEntrega = new Date();
    else if (this.modo == 2) //finalizar
      this.ticket.dataDevolucao = new Date();
    else if (this.modo == 3){ //cancelar
      this.ticketService.deletar(this.ticket.id).subscribe({
        next: mensagem => { // QUANDO DÁ CERTO
          this.toastr.success(mensagem.mensagem);
          this.retorno.emit(mensagem);
        },
        error: erro => { // QUANDO DÁ ERRO
          this.toastr.error(erro.error.mensagem);
          console.error(erro);
        }
      });
    }
    
    if(this.modo == 1 || this.modo == 2){
      this.ticketService.save(this.ticket).subscribe({
        next: mensagem => { // QUANDO DÁ CERTO
          this.toastr.success(mensagem.mensagem);
          this.retorno.emit(mensagem);
        },
        error: erro => { // QUANDO DÁ ERRO
          this.toastr.error(erro.error.mensagem);
          console.error(erro);
        }
      });
    }

    
  }

  retornoAluno(aluno: any) {
    this.toastr.success('Aluno vinculado com sucesso');
    this.ticket.alunoId = aluno;
    this.modalRef.dismiss();
  }

  retornoNotebook(notebook: any) {
    this.toastr.success('Notebook vinculado com sucesso');
    this.ticket.notebookId = notebook;
    this.modalRef.dismiss();
  }

  buscar(modal: any) {
    this.modalRef = this.modalService.open(modal, { size: 'lg' });
  }

}
