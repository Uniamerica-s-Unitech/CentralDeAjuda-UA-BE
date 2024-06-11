import { Component ,EventEmitter,Input,OnInit,Output,inject} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Marca } from 'src/app/models/marca';
import { Mensagem } from 'src/app/models/mensagem';
import { Modelo } from 'src/app/models/modelo';
import { MarcaService } from 'src/app/services/marca.service';
import { ModeloService } from 'src/app/services/modelo.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-modelo-lista',
  templateUrl: './modelo-lista.component.html',
  styleUrls: ['./modelo-lista.component.scss']
})
export class ModeloListaComponent implements OnInit{
  @Output() retorno = new EventEmitter<any>();
  @Input() modoVincular: boolean = false;

  listaModelosOriginal: Modelo[] = [];
  listaModelosFiltrada: Modelo[] = [];
  listaMarcas : Marca[] = [];

  modeloParaEditar: Modelo = new Modelo();
  modeloParaExcluir: Modelo = new Modelo();
  indiceParaEdicao!: number;

  modalService = inject(NgbModal);
  modeloService = inject(ModeloService);
  marcaService = inject(MarcaService);
  toastr = inject(ToastrService);


  filterMarca!: Marca;

  tituloModal!: string;
  termoPesquisa!: "";

  ngOnInit(){
    this.listarModelos();
  }

  constructor(){
    this.carregarMarcas();
  }

  listarModelos(){
    this.modeloService.listar().subscribe({
      next: lista =>{
        this.listaModelosOriginal = lista;
        this.listaModelosFiltrada = lista;
      }
    })
  }

  carregarMarcas() {
    this.marcaService.listar().subscribe({
      next: lista => {
        this.listaMarcas = lista;
      }
    });
  }

  atualizarListaModelo(menssagem : Mensagem){
    this.modalService.dismissAll();
    this.listarModelos();
    this.retorno.emit("ok");
  }

  cadastrarModelo(modalModelo : any){
    this.modeloParaEditar = new Modelo();
    this.modalService.open(modalModelo,{size: 'md'});

    this.tituloModal = "Cadastrar Modelo";
  }

  editarModelo(modal:any,modelo:Modelo,indice:number){
    this.modeloParaEditar = Object.assign({}, modelo);
    this.indiceParaEdicao = indice;

    this.modalService.open(modal, {size: 'md'});
    this.tituloModal = "Editar Modelo";
  }

  excluirModelo(modal : any, modelo : Modelo, indice : number){
    this.modeloParaExcluir = Object.assign({}, modelo);
    this.indiceParaEdicao = indice;

    this.modalService.open(modal, {size: 'sm'});
    this.tituloModal = "Deleter Modelo";
    
  }

  confirmarExclusao(modelo: Modelo) {
    this.modeloService.deletar(modelo.id).subscribe({
      next: (mensagem:Mensagem) => {
        this.toastr.success(mensagem.mensagem);
        this.listarModelos();
        this.modalService.dismissAll(); // Atualize a lista após a exclusão
      },
      error: erro => { // QUANDO DÁ ERRO
        this.toastr.error(erro.error.mensagem);
        // console.error(erro);
      }
    });
  }

  @Output() realizarPesquisa(termoPesquisa: string) {
    termoPesquisa.toLowerCase();
    
    if (!termoPesquisa) {
      // Se o termo de pesquisa estiver vazio, restaurar a lista original
      this.listaModelosFiltrada = this.listaModelosOriginal;
    } else {
      // Caso contrário, aplicar a pesquisa
      this.listaModelosFiltrada = this.listaModelosOriginal.filter((modelo: Modelo) => {
        const nome = modelo.nome.toLowerCase();
        return (
          nome.includes(termoPesquisa)
        );
      });
    }
  }

  @Output() realizarPesquisaPorMarca(filterMarca: Marca) {
    if (filterMarca == null) {
      // Se o termo de pesquisa estiver vazio, restaurar a lista original
      this.listaModelosFiltrada = this.listaModelosOriginal;
    } else {
      // Caso contrário, aplicar a pesquisa
      this.listaModelosFiltrada = this.listaModelosOriginal.filter((modelo: Modelo) => {
        const idMarca = modelo.marcaId.id;
        return (
          idMarca === filterMarca.id
        );
      });
    }
  }
  vincular(modelo: Modelo) {
    this.retorno.emit(modelo);
  }

  byId(item1: any, item2: any){
    if(item1 != null && item2 != null){
      return item1.id === item2.id;
    }else{
      return item1 === item2;
    }
  }
}