import { Component, inject } from '@angular/core';
import { NotebookService } from 'src/app/services/notebook.service';

@Component({
  selector: 'app-header-top',
  templateUrl: './header-top.component.html',
  styleUrls: ['./header-top.component.scss']
})
export class HeaderTopComponent {

  notebookService = inject(NotebookService);
  qtdNotebooks: number = 0;

  constructor(){
    this.listarNotebooks(); 

    setInterval(() => {
      this.listarNotebooks(); 
    }, 3000);
  }


  listarNotebooks(){
    this.notebookService.listar().subscribe({
      next: lista =>{
        this.qtdNotebooks = lista.length;
      }
    })
  }

}
