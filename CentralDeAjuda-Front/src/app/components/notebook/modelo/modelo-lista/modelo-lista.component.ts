import { Component ,EventEmitter,Output,inject} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Marca } from 'src/app/models/marca';
import { Mensagem } from 'src/app/models/mensagem';
import { Modelo } from 'src/app/models/modelo';
import { ModeloService } from 'src/app/services/modelo.service';

@Component({
  selector: 'app-modelo-lista',
  templateUrl: './modelo-lista.component.html',
  styleUrls: ['./modelo-lista.component.scss']
})
export class ModeloListaComponent {
  @Output() retorno = new EventEmitter<any>();

  listaModelosOriginal: Modelo[] = [];
  listaModelosFiltrada: Modelo[] = [];

  modeloParaEditar: Modelo = new Modelo();
  modeloParaExcluir: Modelo = new Modelo();
  indiceParaEdicao!: number;

  modalService = inject(NgbModal);
  modeloService = inject(ModeloService);

  tituloModal!: string;


  constructor(){
    this.listarModelos();
  }

  listarModelos(){
    this.modeloService.listar().subscribe({
      next: lista =>{
        this.listaModelosOriginal = lista;
        this.listaModelosFiltrada = lista;
      }
    })
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
        this.listarModelos();
        this.modalService.dismissAll(); // Atualize a lista após a exclusão
      }
    });
  }

  @Output() realizarPesquisa(pesquisaModelo: string) {
    const termoPesquisa = pesquisaModelo.toLowerCase();
    
    if (!termoPesquisa) {
      // Se o termo de pesquisa estiver vazio, restaurar a lista original
      this.listaModelosFiltrada = this.listaModelosOriginal;
    } else {
      // Caso contrário, aplicar a pesquisa
      this.listaModelosFiltrada = this.listaModelosOriginal.filter((modelo: Modelo) => {
        const nome = modelo.nome.toLowerCase();
        const marca = modelo.marcaId.nome.toLowerCase();
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
}