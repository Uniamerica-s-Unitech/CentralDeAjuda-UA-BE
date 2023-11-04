import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { Mensagem } from 'src/app/models/mensagem';
import { Modelo } from 'src/app/models/modelo';
import { Notebook } from 'src/app/models/notebook';
import { ModeloService } from 'src/app/services/modelo.service';
import { NotebookService } from 'src/app/services/notebook.service';
import { ToastrService } from 'ngx-toastr';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-notebook-detalhes',
  templateUrl: './notebook-detalhes.component.html',
  styleUrls: ['./notebook-detalhes.component.scss']
})
export class NotebookDetalhesComponent {
  @Input() notebook: Notebook = new Notebook();
  @Output() retorno = new EventEmitter<Mensagem>;

  notebookService = inject(NotebookService);
  modeloService = inject(ModeloService);
  toastr = inject(ToastrService);
  modalService = inject(NgbModal);
  modalRef!: NgbModalRef;

  listaModelos: Modelo[] = [];

  constructor() {
    this.carregarModelos();
  }

  carregarModelos() {
    this.modeloService.listar().subscribe({
      next: lista => {
        this.listaModelos = lista;
      }
    });
  }

  salvar(formulario: any) {
    if (!formulario.valid){
      this.toastr.error('Formulário inválido. Preencha os campos corretamente');
    }
    else {
      this.notebookService.save(this.notebook).subscribe({
        next: mensagem => { // QUANDO DÁ CERTO
          this.toastr.success(mensagem.mensagem);
          this.retorno.emit(mensagem);
        },
        error: erro => { // QUANDO DÁ ERRO
          this.toastr.error(erro.error.mensagem);
          console.error(erro);
        }
      });
    }
  }

  retornoModelo(modelo: any) {
    this.toastr.success('Modelo vinculado com sucesso');
    this.notebook.modeloId = modelo;
    this.modalRef.dismiss();
  }

  buscar(modal: any) {
    this.modalRef = this.modalService.open(modal, { size: 'lg' });
  }
}
