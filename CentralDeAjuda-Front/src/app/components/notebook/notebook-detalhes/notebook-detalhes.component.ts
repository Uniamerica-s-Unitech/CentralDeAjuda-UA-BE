import { Component,EventEmitter,Input,Output,inject} from '@angular/core';
import { Mensagem } from 'src/app/models/mensagem';
import { Notebook } from 'src/app/models/notebook';
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

  constructor() {}

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
}
