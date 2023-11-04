import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { Aluno } from 'src/app/models/aluno';
import { Mensagem } from 'src/app/models/mensagem';
import { AlunoService } from 'src/app/services/aluno.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-aluno-detalhes',
  templateUrl: './aluno-detalhes.component.html',
  styleUrls: ['./aluno-detalhes.component.scss']
})
export class AlunoDetalhesComponent {
  @Input() aluno : Aluno = new Aluno();
  @Output() retorno = new EventEmitter<Mensagem>;

  alunoService = inject(AlunoService);
  toastr = inject(ToastrService);


  salvar(formulario: any) {
    if (!formulario.valid){
      this.toastr.error('Formulário inválido. Preencha os campos corretamente');
    }
    else {
      this.alunoService.save(this.aluno).subscribe({
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
}