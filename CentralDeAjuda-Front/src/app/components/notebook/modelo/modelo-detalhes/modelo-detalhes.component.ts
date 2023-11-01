import { Component,EventEmitter,Input,Output,inject} from '@angular/core';
import { Marca } from 'src/app/models/marca';
import { Mensagem } from 'src/app/models/mensagem';
import { Modelo } from 'src/app/models/modelo';
import { MarcaService } from 'src/app/services/marca.service';
import { ModeloService } from 'src/app/services/modelo.service';

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

  salvar() {
    this.modeloService.save(this.modelo).subscribe({
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
