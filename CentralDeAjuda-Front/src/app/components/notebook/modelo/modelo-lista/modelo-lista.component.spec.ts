import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ModeloListaComponent } from './modelo-lista.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { Modelo } from 'src/app/models/modelo';
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

describe('ModeloListaComponent', () => {
  let component: ModeloListaComponent;
  let fixture: ComponentFixture<ModeloListaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModeloListaComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [
        { provide: ToastrService, useClass: ToastrServiceMock },
        { provide: NgbModal, useClass: NgbModalMock },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
    });
    fixture = TestBed.createComponent(ModeloListaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open modal for creating a new modelo', () => {
    spyOn(component.modalService, 'open').and.returnValue({ componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef);

    component.cadastrarModelo({});

    expect(component.modalService.open).toHaveBeenCalled();
    expect(component.modeloParaEditar).toEqual(new Modelo());
    expect(component.tituloModal).toBe('Cadastrar Modelo');
  });

  it('should open modal for editing an existing modelo', () => {
    const modelo = { id: 1, nome: 'Modelo 1', marcaId: { id: 1, nome: 'Marca 1' } } as Modelo;
    spyOn(component.modalService, 'open').and.returnValue({ componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef);

    component.editarModelo({}, modelo, 0);

    expect(component.modalService.open).toHaveBeenCalled();
    expect(component.modeloParaEditar).toEqual(modelo);
    expect(component.indiceParaEdicao).toBe(0);
    expect(component.tituloModal).toBe('Editar Modelo');
  });

  it('should open modal for confirming deletion of a modelo', () => {
    const modelo = { id: 1, nome: 'Modelo 1', marcaId: { id: 1, nome: 'Marca 1' } } as Modelo;
    spyOn(component.modalService, 'open').and.returnValue({ componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef);

    component.excluirModelo({}, modelo, 0);

    expect(component.modalService.open).toHaveBeenCalled();
    expect(component.modeloParaExcluir).toEqual(modelo);
    expect(component.indiceParaEdicao).toBe(0);
    expect(component.tituloModal).toBe('Deleter Modelo');
  });

  it('should confirm deletion of modelo', () => {
    const modelo = { id: 1, nome: 'Modelo 1', marcaId: { id: 1, nome: 'Marca 1' } } as Modelo;
    spyOn(component.modeloService, 'deletar').and.returnValue(of({ mensagem: 'Modelo excluído com sucesso.' }));
    spyOn(component.toastr, 'success').and.callThrough();
    spyOn(component.modalService, 'dismissAll').and.callThrough();

    component.confirmarExclusao(modelo);

    expect(component.toastr.success).toHaveBeenCalledWith('Modelo excluído com sucesso.');
    expect(component.modalService.dismissAll).toHaveBeenCalled();
  });
});
