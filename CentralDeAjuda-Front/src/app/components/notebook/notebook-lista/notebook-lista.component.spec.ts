import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NotebookListaComponent } from './notebook-lista.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { Notebook } from 'src/app/models/notebook';
import { of } from 'rxjs';
import { Modelo } from 'src/app/models/modelo';

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

describe('NotebookListarComponent', () => {
  let component: NotebookListaComponent;
  let fixture: ComponentFixture<NotebookListaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NotebookListaComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [
        { provide: ToastrService, useClass: ToastrServiceMock },
        { provide: NgbModal, useClass: NgbModalMock },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
    });
    fixture = TestBed.createComponent(NotebookListaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should open modal for creating a new notebook', () => {
    spyOn(component.modalService, 'open').and.returnValue({ componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef);

    component.cadastrarNotebook({});

    expect(component.modalService.open).toHaveBeenCalled();
    expect(component.notebookParaEditar).toEqual(new Notebook());
    expect(component.tituloModal).toBe('Cadastrar Notebook');
  });

  it('should open modal for editing an existing notebook', () => {
    const notebook = { id: 1, patrimonio:'123',modeloId: {} as Modelo  };
    spyOn(component.modalService, 'open').and.returnValue({ componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef);

    component.editarNotebook({}, notebook, 0);

    expect(component.modalService.open).toHaveBeenCalled();
    expect(component.notebookParaEditar).toEqual(notebook);
    expect(component.indiceParaEdicao).toBe(0);
    expect(component.tituloModal).toBe('Editar Notebook');
  });

  it('should open modal for confirming deletion of a notebook', () => {
    const notebook = { id: 1, patrimonio:'123',modeloId: {} as Modelo  };
    spyOn(component.modalService, 'open').and.returnValue({ componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef);

    component.excluirNotebook({}, notebook, 0);

    expect(component.modalService.open).toHaveBeenCalled();
    expect(component.notebookParaExcluir).toEqual(notebook);
    expect(component.indiceParaEdicao).toBe(0);
    expect(component.tituloModal).toBe('Deleter Notebook');
  });

  it('should confirm deletion of notebook', () => {
    const notebook = { id: 1, patrimonio:'123',modeloId: {} as Modelo  };
    spyOn(component.notebookService, 'deletar').and.returnValue(of({ mensagem: 'Notebook excluído com sucesso.', status: 200 }));
    spyOn(component.toastr, 'success').and.callThrough();
    spyOn(component.modalService, 'dismissAll').and.callThrough();

    component.confirmarExclusao(notebook);

    expect(component.toastr.success).toHaveBeenCalledWith('Notebook excluído com sucesso.');
    expect(component.modalService.dismissAll).toHaveBeenCalled();
  });
});
