import { Component,EventEmitter,Input,Output,inject} from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Marca } from 'src/app/models/marca';
import { Mensagem } from 'src/app/models/mensagem';
import { Modelo } from 'src/app/models/modelo';
import { MarcaService } from 'src/app/services/marca.service';
import { ModeloService } from 'src/app/services/modelo.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-modelo-detalhes',
  templateUrl: './modelo-detalhes.component.html',
  styleUrls: ['./modelo-detalhes.component.scss']
})
export class ModeloDetalhesComponent {
  @Input() modelo : Modelo = new Modelo();
  @Output() retorno = new EventEmitter<Mensagem>;

  modeloService = inject(ModeloService);
  marcaService = inject(MarcaService);
  toastr = inject(ToastrService);
  modalService = inject(NgbModal);
  modalRef!: NgbModalRef;

  listaMarcas: Marca[] = [];

  constructor() {
    this.carregarMarcas();
  }

  carregarMarcas() {
    this.marcaService.listar().subscribe({
      next: lista => {
        this.listaMarcas = lista;
      }
    });
  }

  salvar(formulario: any) {
    if (!formulario.valid){
      this.toastr.error('Formulário inválido. Preencha os campos corretamente');
    }
    else{
      this.modeloService.save(this.modelo).subscribe({
        next: mensagem => { // QUANDO DÁ CERTO
          this.toastr.success(mensagem.mensagem);
          this.retorno.emit(mensagem);
        },
        error: erro => { // QUANDO DÁ ERRO
          this.toastr.error(erro.error.mensagem);
          // console.error(erro);
        }
      });
    }
  }

  retornoMarca(marca: any) {
    this.toastr.success('Marca vinculado com sucesso');
    this.modelo.marcaId = marca;
    this.modalRef.dismiss();
  }

  buscar(modal: any) {
    this.modalRef = this.modalService.open(modal, { size: 'lg' });
  }
}