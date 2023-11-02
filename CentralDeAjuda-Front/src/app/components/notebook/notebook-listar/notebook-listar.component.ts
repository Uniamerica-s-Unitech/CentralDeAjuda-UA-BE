import { Component ,EventEmitter,Output,inject} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Marca } from 'src/app/models/marca';
import { Mensagem } from 'src/app/models/mensagem';
import { Modelo } from 'src/app/models/modelo';
import { Notebook } from 'src/app/models/notebook';
import { NotebookService } from 'src/app/services/notebook.service';

@Component({
  selector: 'app-notebook-listar',
  templateUrl: './notebook-listar.component.html',
  styleUrls: ['./notebook-listar.component.scss']
})
export class NotebookListarComponent {
  @Output() retorno = new EventEmitter<any>();

  listaNotebooksOriginal: Notebook[] = [];
  listaNotebooksFiltrada: Notebook[] = [];

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
        this.listaNotebooksOriginal = lista;
        this.listaNotebooksFiltrada = lista;
      }
    })
  }

  atualizarListaNotebook(menssagem : Mensagem){
    this.modalService.dismissAll();
    this.listarNotebooks();
    this.retorno.emit("ok");
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
        this.modalService.dismissAll();
      }
    });
  }

  @Output() realizarPesquisa(pesquisaNotebook: string) {
    const termoPesquisa = pesquisaNotebook.toLowerCase();
    
    if (!termoPesquisa) {
      this.listaNotebooksFiltrada = this.listaNotebooksOriginal;
    } else {
      this.listaNotebooksFiltrada = this.listaNotebooksOriginal.filter((notebook: Notebook) => {
        const patrimonio = notebook.patrimonio.toLowerCase();
        return (
          patrimonio.includes(termoPesquisa)
        );
      });
    }
  }

  @Output() realizarPesquisaPorModelo(filterModelo: Modelo) {
    if (filterModelo == null) {
      this.listaNotebooksFiltrada = this.listaNotebooksOriginal;
    } else {
      this.listaNotebooksFiltrada = this.listaNotebooksOriginal.filter((notebook: Notebook) => {
        const idModelo = notebook.modeloId.id;
        return (
          idModelo === filterModelo.id
        );
      });
    }
  }

  @Output() realizarPesquisaPorMarca(filterMarca: Marca) {
    if (filterMarca == null) {
      this.listaNotebooksFiltrada = this.listaNotebooksOriginal;
    } else {
      this.listaNotebooksFiltrada = this.listaNotebooksOriginal.filter((notebook: Notebook) => {
        const idModelo = notebook.modeloId.marcaId.id;
        return (
          idModelo === filterMarca.id
        );
      });
    }
  }
}