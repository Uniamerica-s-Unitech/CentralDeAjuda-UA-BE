import { Component,EventEmitter,Input,Output,inject} from '@angular/core';
import { Marca } from 'src/app/models/marca';
import { Mensagem } from 'src/app/models/mensagem';
import { MarcaService } from 'src/app/services/marca.service';

@Component({
  selector: 'app-marca-detalhes',
  templateUrl: './marca-detalhes.component.html',
  styleUrls: ['./marca-detalhes.component.scss']
})
export class MarcaDetalhesComponent {
 
  @Input() marca : Marca = new Marca();
  @Output() retorno = new EventEmitter<Mensagem>;

  marcaService = inject(MarcaService);

  constructor() {}

  salvar() {
    this.marcaService.save(this.marca).subscribe({
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
