import { Component ,inject} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Mensagem } from 'src/app/models/mensagem';
import { Notebook } from 'src/app/models/notebook';
import { NotebookService } from 'src/app/services/notebook.service';

@Component({
  selector: 'app-notebook-listar',
  templateUrl: './notebook-listar.component.html',
  styleUrls: ['./notebook-listar.component.scss']
})
export class NotebookListarComponent {
  active = 1;

  listaNotebooks: Notebook[] = [];

  notebookParaEditar: Notebook = new Notebook();
  notebookParaExcluir: Notebook = new Notebook();
  indiceParaEdicao!: number;

  modalService = inject(NgbModal);
  notebookService = inject(NotebookService);

  tituloModal!: string;


  constructor(){
    this.listarNotebooks();
  }

  listarNotebooks(){
    this.notebookService.listar().subscribe({
      next: lista =>{
        this.listaNotebooks = lista;
      }
    })
  }

  atualizarListaNotebook(menssagem : Mensagem){
    this.modalService.dismissAll();
    this.listarNotebooks();
  }

  cadastrarNotebook(modalNotebook : any){
    this.notebookParaEditar = new Notebook();
    this.modalService.open(modalNotebook,{size: 'md'});

    this.tituloModal = "Cadastrar Notebook";
  }

  editarNotebook(modal:any,notebook:Notebook,indice:number){
    this.notebookParaEditar = Object.assign({}, notebook);
    this.indiceParaEdicao = indice;

    this.modalService.open(modal, {size: 'md'});
    this.tituloModal = "Editar Notebook";
  }

  excluirNotebook(modal : any, notebook : Notebook, indice : number){
    this.notebookParaExcluir = Object.assign({}, notebook);
    this.indiceParaEdicao = indice;

    this.modalService.open(modal, {size: 'sm'});
    this.tituloModal = "Deleter Notebook";
    
  }

  confirmarExclusao(notebook: Notebook) {
    this.notebookService.deletar(notebook.id).subscribe({
      next: (mensagem:Mensagem) => {
        this.listarNotebooks();
        this.modalService.dismissAll(); // Atualize a lista após a exclusão
      }
    });
  }
}
