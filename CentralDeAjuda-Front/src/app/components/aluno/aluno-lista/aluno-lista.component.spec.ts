import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AlunoListaComponent } from './aluno-lista.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}


class NgbModalMock {
  open(content: any, options: any): NgbModalRef {
    return { componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef;
  }
}



describe('AlunoListaComponent', () => {
  let component: AlunoListaComponent;
  let fixture: ComponentFixture<AlunoListaComponent>;
  let modalService: NgbModal;


  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AlunoListaComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [
        { provide: ToastrService, useClass: ToastrServiceMock },
        { provide: NgbModal, useClass: NgbModalMock },
      ],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]

    });
    fixture = TestBed.createComponent(AlunoListaComponent);
    component = fixture.componentInstance;
    modalService = TestBed.inject(NgbModal);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should open modal for adding a new aluno', () => {
    spyOn(modalService, 'open').and.callThrough();

    component.cadastrarAluno({});

    expect(modalService.open).toHaveBeenCalled();
  });

  it('should open modal for editing a aluno', () => {
    spyOn(modalService, 'open').and.callThrough();
    const aluno = { id: 1, nome: 'Test Aluno', ra : 123456};

    component.editarAluno({}, aluno , 123456 );

    expect(component.alunoParaEditar).toEqual(aluno);
    expect(modalService.open).toHaveBeenCalled();
  });

  it('should open modal for deleting a aluno', () => {
    spyOn(modalService, 'open').and.callThrough();
    const aluno = { id: 1, nome: 'Test Aluno', ra: 123456};

    component.excluirAluno({}, aluno, 123456);

    expect(component.alunoParaExcluir).toEqual(aluno);
    expect(modalService.open).toHaveBeenCalled();
  });

  it('should emit "vincular" event when vincular is called', () => {
    spyOn(component.retorno, 'emit');

    const aluno = { id: 1, nome: 'Test Aluno', ra: 123456};
    component.vincular(aluno);

    expect(component.retorno.emit).toHaveBeenCalledWith(aluno);
  });

});
