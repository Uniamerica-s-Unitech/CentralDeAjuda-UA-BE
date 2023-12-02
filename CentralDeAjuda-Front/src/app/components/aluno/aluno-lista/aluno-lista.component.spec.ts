import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlunoListaComponent } from './aluno-lista.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { Aluno } from 'src/app/models/aluno';
import { of } from 'rxjs';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

class NgbModalMock {
  open(content: any, options: any): NgbModalRef {
    return { componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef;
  }

  // Adicione um spy para dismissAll
  dismissAll(): void {}
}

describe('AlunoListaComponent', () => {
  let component: AlunoListaComponent;
  let fixture: ComponentFixture<AlunoListaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AlunoListaComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [
        { provide: ToastrService, useClass: ToastrServiceMock },
        { provide: NgbModal, useClass: NgbModalMock },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
    });
    fixture = TestBed.createComponent(AlunoListaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open modal for creating a new aluno', () => {
    spyOn(component.modalService, 'open').and.returnValue({ componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef);

    component.cadastrarAluno({});

    expect(component.modalService.open).toHaveBeenCalled();
    expect(component.alunoParaEditar).toEqual(new Aluno());
    expect(component.tituloModal).toBe('Cadastrar Aluno');
  });

  it('should open modal for editing an existing aluno', () => {
    const aluno = { id: 1, nome: 'Aluno 1', ra:123123 };
    spyOn(component.modalService, 'open').and.returnValue({ componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef);

    component.editarAluno({}, aluno, 0);

    expect(component.modalService.open).toHaveBeenCalled();
    expect(component.alunoParaEditar).toEqual(aluno);
    expect(component.indiceParaEdicao).toBe(0);
    expect(component.tituloModal).toBe('Editar Aluno');
  });

  it('should open modal for confirming deletion of a aluno', () => {
    const aluno = { id: 1, nome: 'Aluno 1', ra:123123};
    spyOn(component.modalService, 'open').and.returnValue({ componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef);

    component.excluirAluno({}, aluno, 0);

    expect(component.modalService.open).toHaveBeenCalled();
    expect(component.alunoParaExcluir).toEqual(aluno);
    expect(component.indiceParaEdicao).toBe(0);
    expect(component.tituloModal).toBe('Deleter Aluno');
  });

  it('should confirm deletion of aluno', () => {
    const aluno = { id: 1, nome: 'Aluno 1', ra:123123};
    spyOn(component.alunoService, 'deletar').and.returnValue(of({ mensagem: 'Aluno excluído com sucesso.',status: 200 }));
    spyOn(component.toastr, 'success').and.callThrough();
    spyOn(component.modalService, 'dismissAll').and.callThrough();

    component.confirmarExclusao(aluno);

    expect(component.toastr.success).toHaveBeenCalledWith('Aluno excluído com sucesso.');
    expect(component.modalService.dismissAll).toHaveBeenCalled();
  });
});
