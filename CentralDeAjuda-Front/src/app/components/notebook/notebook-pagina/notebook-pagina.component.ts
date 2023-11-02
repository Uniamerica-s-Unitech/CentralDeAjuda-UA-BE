import { Component, ViewChild, inject} from '@angular/core';
import { Marca } from 'src/app/models/marca';
import { Modelo } from 'src/app/models/modelo';
import { MarcaService } from 'src/app/services/marca.service';
import { ModeloService } from 'src/app/services/modelo.service';
import { NotebookListarComponent } from '../notebook-listar/notebook-listar.component';
import { ModeloListaComponent } from '../modelo/modelo-lista/modelo-lista.component';
import { MarcaListaComponent } from '../marca/marca-lista/marca-lista.component';

@Component({
  selector: 'app-notebook-pagina',
  templateUrl: './notebook-pagina.component.html',
  styleUrls: ['./notebook-pagina.component.scss']
})
export class NotebookPaginaComponent {
  active = 1;
  
  pesquisaNotebook: string = "";
  pesquisaModelo: string = "";
  pesquisaMarca: string = "";

  filterModelo!: Modelo;
  filterMarca!: Marca;

  @ViewChild('notebook') notebook!: NotebookListarComponent;
  @ViewChild('modelo') modelo!: ModeloListaComponent;
  @ViewChild('marca') marca!: MarcaListaComponent;

  listaModelos : Modelo[] = [];
  listaMarcas : Marca[] = [];

  modeloService = inject(ModeloService);
  marcaService = inject(MarcaService);

  constructor() {
   this.recarregarLista();
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

  filtrarNotebooks(){
    this.notebook.realizarPesquisa(this.pesquisaNotebook);
  }
  filtrarModelos(){
    this.modelo.realizarPesquisa(this.pesquisaModelo);
  }
  filtrarMarcas(){
    this.marca.realizarPesquisa(this.pesquisaMarca);
  }

  filtrarNotebookPorModelo(){
    this.notebook.realizarPesquisaPorModelo(this.filterModelo);
  }
  filtrarNotebookPorMarca(){
    this.notebook.realizarPesquisaPorMarca(this.filterMarca);
  }
  filtrarModeloPorMarca(){
    this.modelo.realizarPesquisaPorMarca(this.filterMarca);
  }

  byId(item1: any, item2: any){
    if(item1 != null && item2 != null){
      return item1.id === item2.id;
    }else{
      return item1 === item2;
    }
  } 
}