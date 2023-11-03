import { Component, Output, inject } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Aluno } from 'src/app/models/aluno';
import { Mensagem } from 'src/app/models/mensagem';
import { AlunoService } from 'src/app/services/aluno.service';

@Component({
  selector: 'app-aluno-lista',
  templateUrl: './aluno-lista.component.html',
  styleUrls: ['./aluno-lista.component.scss']
})
export class AlunoListaComponent {

  listaAlunosOriginal: Aluno[] = [];
  listaAlunosFiltrada: Aluno[] = [];

  alunoParaEditar: Aluno = new Aluno();
  alunoParaExcluir: Aluno = new Aluno();

  indiceParaEdicao!: number; 

  modalService = inject(NgbModal);
  alunoService = inject(AlunoService);

  tituloModal!: string;
  termoPesquisa!: "";

  constructor(){
    this.listarAlunos();
  }

  listarAlunos(){
    this.alunoService.listar().subscribe({
      next: lista =>{
        this.listaAlunosOriginal = lista;
        this.listaAlunosFiltrada = lista;
      }
    })
  }

  atualizarListaAluno(menssagem : Mensagem){
    this.modalService.dismissAll();
    this.listarAlunos();

  }

  cadastrarAlunos(modalAluno : any){
    this.alunoParaEditar = new Aluno();
    this.modalService.open(modalAluno,{size: 'md'});

    this.tituloModal = "Cadastrar Aluno";
  }

  editarAluno(modal:any,aluno:Aluno,indice:number){
    this.alunoParaEditar = Object.assign({}, aluno);
    this.indiceParaEdicao = indice;

    this.modalService.open(modal, {size: 'md'});
    this.tituloModal = "Editar Aluno";
  }

  excluirAluno(modal : any, aluno : Aluno, indice : number){
    this.alunoParaExcluir = Object.assign({}, aluno);
    this.indiceParaEdicao = indice;

    this.modalService.open(modal, {size: 'sm'});
    this.tituloModal = "Deleter Aluno";
    
  }

  confirmarExclusao(aluno: Aluno) {
    this.alunoService.deletar(aluno.id).subscribe({
      next: (mensagem:Mensagem) => {
        this.listarAlunos();
        this.modalService.dismissAll(); // Atualize a lista após a exclusão
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
}