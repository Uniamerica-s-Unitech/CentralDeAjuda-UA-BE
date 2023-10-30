import { Component,EventEmitter,Input,Output,inject} from '@angular/core';
import { Mensagem } from 'src/app/models/mensagem';
import { Modelo } from 'src/app/models/modelo';
import { Notebook } from 'src/app/models/notebook';
import { ModeloService } from 'src/app/services/modelo.service';
import { NotebookService } from 'src/app/services/notebook.service';

@Component({
  selector: 'app-notebook-detalhes',
  templateUrl: './notebook-detalhes.component.html',
  styleUrls: ['./notebook-detalhes.component.scss']
})
export class NotebookDetalhesComponent {
  @Input() notebook : Notebook = new Notebook();
  @Output() retorno = new EventEmitter<Mensagem>;

  notebookService = inject(NotebookService);
  modeloService = inject(ModeloService);

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

  salvar() {
    this.notebookService.save(this.notebook).subscribe({
      next: mensagem => { // QUANDO DÁ CERTO
        this.retorno.emit(mensagem);
      },
      error: erro => { // QUANDO DÁ ERRO
        alert('Exemplo de tratamento de erro/exception! Observe o erro no console!');
        console.error(erro);
      }
    });
  }

  byId(item1: any, item2: any){
    if(item1 != null && item2 != null){
      return item1.id === item2.id;
    }else{
      return item1 === item2;
    }
  }
}
