import { Component,EventEmitter,Input,Output,inject} from '@angular/core';
import { Marca } from 'src/app/models/marca';
import { Mensagem } from 'src/app/models/mensagem';
import { MarcaService } from 'src/app/services/marca.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-marca-detalhes',
  templateUrl: './marca-detalhes.component.html',
  styleUrls: ['./marca-detalhes.component.scss']
})
export class MarcaDetalhesComponent {
  @Input() marca : Marca = new Marca();
  @Output() retorno = new EventEmitter<Mensagem>;

  marcaService = inject(MarcaService);
  toastr = inject(ToastrService);
  
  salvar(formulario: any) {
    if (!formulario.valid){
      this.toastr.error('Formulário inválido. Preencha os campos corretamente');
    }
    else {
      this.marcaService.save(this.marca).subscribe({
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
}