import { Component ,EventEmitter,Input,OnInit,Output,inject} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Marca } from 'src/app/models/marca';
import { Mensagem } from 'src/app/models/mensagem';
import { Modelo } from 'src/app/models/modelo';
import { Notebook } from 'src/app/models/notebook';
import { MarcaService } from 'src/app/services/marca.service';
import { ModeloService } from 'src/app/services/modelo.service';
import { NotebookService } from 'src/app/services/notebook.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-notebook-listar',
  templateUrl: './notebook-listar.component.html',
  styleUrls: ['./notebook-listar.component.scss']
})
export class NotebookListarComponent implements OnInit{
  @Output() retorno = new EventEmitter<any>();
  @Input() modoVincular: boolean = false;

  listaNotebooksOriginal: Notebook[] = [];
  listaNotebooksFiltrada: Notebook[] = [];
  listaModelos : Modelo[] = [];
  listaMarcas : Marca[] = [];

  notebookParaEditar: Notebook = new Notebook();
  notebookParaExcluir: Notebook = new Notebook();
  indiceParaEdicao!: number;

  modalService = inject(NgbModal);
  notebookService = inject(NotebookService);
  modeloService = inject(ModeloService);
  marcaService = inject(MarcaService);
  toastr = inject(ToastrService);

  filterModelo!: Modelo;
  filterMarca!: Marca;

  tituloModal!: string;
  termoPesquisa!: "";

  ngOnInit(){
    this.listarNotebooks();
  }

  constructor(){
    this.recarregarLista();
  }

  listarNotebooks(){
    if (this.modoVincular) {
      this.notebookService.listarNotebooksSemVinculos().subscribe({
        next: lista => {
          this.listaNotebooksOriginal = lista;
          this.listaNotebooksFiltrada = lista;
        }
      });
    } else {
      this.notebookService.listar().subscribe({
        next: lista => {
          this.listaNotebooksOriginal = lista;
          this.listaNotebooksFiltrada = lista;
        }
      });
    }
  }

  recarregarLista(){
    this.carregarModelos();
    this.carregarMarcas();
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
        this.toastr.success(mensagem.mensagem);
        this.listarNotebooks();
        this.modalService.dismissAll();
      },
      error: erro => { // QUANDO DÁ ERRO
        this.toastr.error(erro.error.mensagem);
        console.error(erro);
      }
    });
  }

  @Output() realizarPesquisa(termoPesquisa: string) {
    termoPesquisa.toLowerCase();
    
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

  vincular(notebook: Notebook){
    this.retorno.emit(notebook);
  }

  byId(item1: any, item2: any){
    if(item1 != null && item2 != null){
      return item1.id === item2.id;
    }else{
      return item1 === item2;
    }
  }
}