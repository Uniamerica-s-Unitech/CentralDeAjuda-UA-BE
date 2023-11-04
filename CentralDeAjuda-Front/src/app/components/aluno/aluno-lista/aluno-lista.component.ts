import { Component, EventEmitter, Input, OnInit, Output, inject } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Aluno } from 'src/app/models/aluno';
import { Mensagem } from 'src/app/models/mensagem';
import { AlunoService } from 'src/app/services/aluno.service';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-aluno-lista',
  templateUrl: './aluno-lista.component.html',
  styleUrls: ['./aluno-lista.component.scss']
})
export class AlunoListaComponent implements OnInit{
  @Output() retorno = new EventEmitter<Aluno>();
  @Input() modoVincular: boolean = false;

  listaAlunosOriginal: Aluno[] = [];
  listaAlunosFiltrada: Aluno[] = [];

  alunoParaEditar: Aluno = new Aluno();
  alunoParaExcluir: Aluno = new Aluno();

  indiceParaEdicao!: number;

  modalService = inject(NgbModal);
  alunoService = inject(AlunoService);
  toastr = inject(ToastrService);

  tituloModal!: string;
  termoPesquisa!: "";

  //3º -  É executado quando o HTML termina de carregar
  /*ngAfterViewInit() {
    console.log('afterviewinit');
  }*/

  //2º É executado depois que todas as injeções de dados e parâmetros são recebidos
  ngOnInit(){
    //console.log('oninit');
    this.listarAlunos();

  }

  /*1º - Executa assim que a classe é chamada, mesmo que não tenha carregado as coisas
  constructor() {
    console.log('construtor');
  }*/

  listarAlunos() {
    if (this.modoVincular) {
      this.alunoService.listarAlunosSemVinculos().subscribe({
        next: lista => {
          this.listaAlunosOriginal = lista;
          this.listaAlunosFiltrada = lista;
        }
      });
    } else {
      this.alunoService.listar().subscribe({
        next: lista => {
          this.listaAlunosOriginal = lista;
          this.listaAlunosFiltrada = lista;
        }
      });
    }
  }

  atualizarListaAluno(menssagem: Mensagem) {
    this.modalService.dismissAll();
    this.listarAlunos();

  }

  cadastrarAluno(modalAluno: any) {
    this.alunoParaEditar = new Aluno();
    this.modalService.open(modalAluno, { size: 'md' });

    this.tituloModal = "Cadastrar Aluno";
  }

  editarAluno(modal: any, aluno: Aluno, indice: number) {
    this.alunoParaEditar = Object.assign({}, aluno);
    this.indiceParaEdicao = indice;

    this.modalService.open(modal, { size: 'md' });
    this.tituloModal = "Editar Aluno";
  }

  excluirAluno(modal: any, aluno: Aluno, indice: number) {
    this.alunoParaExcluir = Object.assign({}, aluno);
    this.indiceParaEdicao = indice;

    this.modalService.open(modal, { size: 'sm' });
    this.tituloModal = "Deleter Aluno";

  }

  confirmarExclusao(aluno: Aluno) {
    this.alunoService.deletar(aluno.id).subscribe({
      next: (mensagem: Mensagem) => {
        this.toastr.success(mensagem.mensagem);
        this.listarAlunos();
        this.modalService.dismissAll(); // Atualize a lista após a exclusão
      },
      error: erro => { // QUANDO DÁ ERRO
        this.toastr.error(erro.error.mensagem);
        console.error(erro);
      }
    });
  }

  @Output() realizarPesquisa(termoPesquisa: string) {
    termoPesquisa.toLowerCase();
    if (!termoPesquisa) {
      this.listaAlunosFiltrada = this.listaAlunosOriginal;
    } else {
      this.listaAlunosFiltrada = this.listaAlunosOriginal.filter((aluno: Aluno) => {
        const nome = aluno.nome.toLowerCase();
        const ra = aluno.ra;
        return (
          nome.includes(termoPesquisa)
        );
      });
    }
  }

  vincular(aluno: Aluno) {
    this.retorno.emit(aluno);
  }
}