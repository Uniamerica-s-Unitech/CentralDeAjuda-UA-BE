import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { Aluno } from 'src/app/models/aluno';
import { Mensagem } from 'src/app/models/mensagem';
import { AlunoService } from 'src/app/services/aluno.service';

@Component({
  selector: 'app-aluno-detalhes',
  templateUrl: './aluno-detalhes.component.html',
  styleUrls: ['./aluno-detalhes.component.scss']
})
export class AlunoDetalhesComponent {
  @Input() aluno : Aluno = new Aluno();
  @Output() retorno = new EventEmitter<Mensagem>;

  alunoService = inject(AlunoService);


  constructor() {
  }

  salvar() {
    this.alunoService.save(this.aluno).subscribe({
      next: mensagem => { // QUANDO DÁ CERTO
        this.retorno.emit(mensagem);
      },
      error: erro => { // QUANDO DÁ ERRO
        alert('Exemplo de tratamento de erro/exception! Observe o erro no console!');
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
