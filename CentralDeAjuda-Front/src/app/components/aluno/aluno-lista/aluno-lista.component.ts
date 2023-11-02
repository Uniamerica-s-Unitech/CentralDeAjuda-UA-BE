import { Component, inject } from '@angular/core';
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

  listaAlunos: Aluno[] = [];
  alunoParaEditar: Aluno = new Aluno();
  alunoParaExcluir: Aluno = new Aluno();
  indiceParaEdicao!: number; 
  num!: number;
  modalService = inject(NgbModal);
  alunoService = inject(AlunoService);
  tituloModal!: string;
  constructor(){
    this.listarAlunos();

  }

listarAlunos(){
  this.alunoService.listar().subscribe({
    next: lista =>{
      this.listaAlunos = lista;
      this.num = this.listaAlunos.length;
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

}
