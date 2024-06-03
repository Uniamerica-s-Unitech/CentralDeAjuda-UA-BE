import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Mensagem } from 'src/app/models/mensagem';
import { User } from 'src/app/models/user';
import { Usuario } from 'src/app/models/usuario';
import { LoginService } from 'src/app/services/login.service';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-cadastrar-list',
  templateUrl: './cadastrar-list.component.html',
  styleUrls: ['./cadastrar-list.component.scss']
})
export class CadastrarListComponent {
  @Input() usuario : Usuario = new Usuario();
  usuarioService = inject(UsuarioService);
  router = inject(Router);

  cadastrar(): void{
    this.usuarioService.create(this.usuario).subscribe(
      data => {
        console.log(data);
        // this.volver();
      },
      err => console.log(err)
    );
  }
  // volver(): void {
  //   this.router.navigate(['/']);
  // }







  // @Output() retorno = new EventEmitter<any>();

  // listaUsersOrginal: User[] = [];
  // listaUsersFiltrada: User[] = [];

  // modalService = inject(NgbModal);
  // loginService = inject(LoginService);
  // toastr = inject(ToastrService);

  // userParaEditar: User = new User();
  // userParaExcluir: User = new User();

  // indiceSelecionadoParaEdicao!: number;
  // modalRef!: NgbModalRef;
  // tituloModal!: string;
  // termoPesquisa!: "";

  // constructor() {
  //   this.listarUsers();
  // }

  // listarUsers(){
  //   this.loginService.listarUsers().subscribe({
  //     next: lista => {
  //       this.listaUsersOrginal = lista;
  //       this.listaUsersFiltrada = lista;
  //     }
  //   })
  // }

  // atualizarLista(mensagem: Mensagem) {
  //   this.modalService.dismissAll();
  //   this.listarUsers();
  //   this.retorno.emit("ok");
  // }

  // cadastrarUser(modalUser : any){
  //   this.userParaEditar = new User();
  //   this.modalService.open(modalUser, { size: 'md' });

  //   this.tituloModal = "Cadastrar User";
  // }

  // editarUser(modal: any, user: User, indice: number) {
  //   this.userParaEditar = Object.assign({}, user); //clonando o objeto se for edição... pra não mexer diretamente na referência da lista
  //   this.indiceSelecionadoParaEdicao = indice;

  //   this.modalService.open(modal, { size: 'md' });

  //   this.tituloModal = "Editar User";
  // }

  // excluirUser(modal: any, user: User, indice: number) {
  //   this.userParaExcluir = Object.assign({}, user);
  //   this.indiceSelecionadoParaEdicao = indice;

  //   this.modalService.open(modal, { size: 'sm' });
  //   this.tituloModal = "Deleter User";

  // }

  // confirmarExclusao(user: User) {
  //   this.loginService.deletarUser(user.id).subscribe({
  //     next: (mensagem: Mensagem) => {
  //       this.toastr.success(mensagem.mensagem);
  //       this.listarUsers();
  //       this.modalService.dismissAll();
  //     },
  //     error: erro => {
  //       this.toastr.error(erro.error.mensagem);
  //     }
  //   });
  // }

  // @Output() realizarPesquisa(termoPesquisa: string) {
  //   termoPesquisa.toLowerCase();
  //   if (!termoPesquisa) {
  //     this.listaUsersFiltrada = this.listaUsersOrginal;
  //   } else {
  //     this.listaUsersFiltrada = this.listaUsersOrginal.filter((user: User) => {
  //       const nome = user.username.toLowerCase();
  //       return (
  //         nome.includes(termoPesquisa)
  //       );
  //     });
  //   }
  // }
}
