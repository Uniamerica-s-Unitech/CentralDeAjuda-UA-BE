import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Mensagem } from 'src/app/models/mensagem';
import { User } from 'src/app/models/user';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-cadastrar-detalhes',
  templateUrl: './cadastrar-detalhes.component.html',
  styleUrls: ['./cadastrar-detalhes.component.scss']
})
export class CadastrarDetalhesComponent {
  @Input() user:User = new User();
  @Output() retorno = new EventEmitter<Mensagem>;

  loginService = inject(LoginService);
  toastr = inject(ToastrService);

  salvar(formulario: any) {
    if (!formulario.valid){
      this.toastr.error('Formulário inválido. Preencha os campos corretamente');
    }else{
      this.loginService.saveUser(this.user).subscribe({
        next: mensagem => {
          this.toastr.success(mensagem.mensagem);
          this.retorno.emit(mensagem);
        },
        error: erro => {
          this.toastr.error(erro.error.mensagem);
        }
      });
    }
  }
  byId(item1: any, item2: any) {
    if (item1 != null && item2 != null)
      return item1.id === item2.id;
    else
      return item1 === item2;
  }
}
