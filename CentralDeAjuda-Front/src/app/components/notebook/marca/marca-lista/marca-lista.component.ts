import { Component ,EventEmitter,Output,inject} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Marca } from 'src/app/models/marca';
import { Mensagem } from 'src/app/models/mensagem';
import { MarcaService } from 'src/app/services/marca.service';

@Component({
  selector: 'app-marca-lista',
  templateUrl: './marca-lista.component.html',
  styleUrls: ['./marca-lista.component.scss']
})
export class MarcaListaComponent {
  @Output() retorno = new EventEmitter<any>();

  listaMarcasOriginal: Marca[] = [];
  listaMarcasFiltrada: Marca[] = [];

  marcaParaEditar: Marca = new Marca();
  marcaParaExcluir: Marca = new Marca();
  indiceParaEdicao!: number;

  modalService = inject(NgbModal);
  marcaService = inject(MarcaService);

  tituloModal!: string;
  
  constructor(){
    this.listarMarcas();
  }

  listarMarcas(){
    this.marcaService.listar().subscribe({
      next: lista =>{
        this.listaMarcasOriginal = lista;
        this.listaMarcasFiltrada = lista;
      }
    })
  }

  atualizarListaMarca(menssagem : Mensagem){
    this.modalService.dismissAll();
    this.listarMarcas();
    this.retorno.emit("ok");
  }

  cadastrarMarca(modalMarca : any){
    this.marcaParaEditar = new Marca();
    this.modalService.open(modalMarca,{size: 'md'});

    this.tituloModal = "Cadastrar Marca";
  }

  editarMarca(modal:any,marca:Marca,indice:number){
    this.marcaParaEditar = Object.assign({}, marca);
    this.indiceParaEdicao = indice;

    this.modalService.open(modal, {size: 'md'});
    this.tituloModal = "Editar Marca";
  }

  excluirMarca(modal : any, marca : Marca, indice : number){
    this.marcaParaExcluir = Object.assign({}, marca);
    this.indiceParaEdicao = indice;

    this.modalService.open(modal, {size: 'sm'});
    this.tituloModal = "Deleter Marca";
    
  }

  confirmarExclusao(marca: Marca) {
    this.marcaService.deletar(marca.id).subscribe({
      next: (mensagem:Mensagem) => {
        this.listarMarcas();
        this.modalService.dismissAll(); // Atualize a lista após a exclusão
      }
    });
  }

  @Output() realizarPesquisa(pesquisaMarca: string) {
    const termoPesquisa = pesquisaMarca.toLowerCase();
    
    if (!termoPesquisa) {
      // Se o termo de pesquisa estiver vazio, restaurar a lista original
      this.listaMarcasFiltrada = this.listaMarcasOriginal;
    } else {
      // Caso contrário, aplicar a pesquisa
      this.listaMarcasFiltrada = this.listaMarcasOriginal.filter((marca: Marca) => {
        const nome = marca.nome.toLowerCase();
        return (
          nome.includes(termoPesquisa)
        );
      });
    }
  }
}