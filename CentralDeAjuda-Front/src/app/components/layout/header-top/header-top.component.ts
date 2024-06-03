import { Component, Input, inject } from '@angular/core';
import { AlunoService } from 'src/app/services/aluno.service';
import { NotebookService } from 'src/app/services/notebook.service';
import { TicketService } from 'src/app/services/ticket.service';

@Component({
  selector: 'app-header-top',
  templateUrl: './header-top.component.html',
  styleUrls: ['./header-top.component.scss']
})
export class HeaderTopComponent {
  @Input() isLogged: boolean | undefined;
  @Input() isAdmin: boolean | undefined;

  notebookService = inject(NotebookService);
  alunoService = inject(AlunoService);
  ticketService = inject(TicketService);

  qtdNotebooks: number = 0;
  qtdAlunos: number = 0;
  qtdTickets: number = 0;


  constructor(){
    
    this.carregarListas();
  }

  carregarListas(){
    this.listarNotebooks();
    this.listarAlunos();
    this.listarTickets(); 
  }

  listarNotebooks(){
    this.notebookService.listar().subscribe({
      next: lista =>{
        this.qtdNotebooks = lista.length;
      }
    })
  }
  listarAlunos(){
    this.alunoService.listar().subscribe({
      next: lista =>{
        this.qtdAlunos = lista.length;
      }
    })
  }
  listarTickets(){
    this.ticketService.listarAbertos().subscribe({
      next: lista =>{
        this.qtdTickets = lista.length;
      }
    })
  }


}
