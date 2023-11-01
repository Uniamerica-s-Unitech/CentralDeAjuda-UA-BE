import { Component ,inject} from '@angular/core';
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
  listaMarcas: Marca[] = [];

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
        this.listaMarcas = lista;
      }
    })
  }

  atualizarListaMarca(menssagem : Mensagem){
    this.modalService.dismissAll();
    this.listarMarcas();
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
}
