import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { MarcaListaComponent } from './marca-lista.component';
import { MarcaService } from 'src/app/services/marca.service';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { Marca } from 'src/app/models/marca';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

class MarcaServiceMock {
  listar() {
    return of([{ id: 1, nome: 'Marca 1' }, { id: 2, nome: 'Marca 2' }]);
  }

  deletar(id: number) {
    return of({ mensagem: 'Marca excluída com sucesso.' });
  }
}

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

class NgbModalMock {
  open(content: any, options: any): NgbModalRef {
    return { componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef;
  }

  dismissAll(reason?: any): void {}
}

describe('MarcaListaComponent', () => {
  let component: MarcaListaComponent;
  let fixture: ComponentFixture<MarcaListaComponent>;
  let marcaService: MarcaService;
  let toastrService: ToastrService;
  let modalService: NgbModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MarcaListaComponent],
      imports: [HttpClientTestingModule, ToastrModule,FormsModule],
      providers: [
        { provide: MarcaService, useClass: MarcaServiceMock },
        { provide: ToastrService, useClass: ToastrServiceMock },
        { provide: NgbModal, useClass: NgbModalMock },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
    });

    fixture = TestBed.createComponent(MarcaListaComponent);
    component = fixture.componentInstance;
    marcaService = TestBed.inject(MarcaService);
    toastrService = TestBed.inject(ToastrService);
    modalService = TestBed.inject(NgbModal);

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should list brands on initialization', () => {
    spyOn(marcaService, 'listar').and.callThrough();
    component.listarMarcas();
    expect(marcaService.listar).toHaveBeenCalled();
    expect(component.listaMarcasOriginal.length).toBe(2);
  });

  it('should open modal for adding a new brand', () => {
    spyOn(modalService, 'open').and.callThrough();
    component.cadastrarMarca({});
    expect(modalService.open).toHaveBeenCalled();
    expect(component.marcaParaEditar).toEqual(new Marca());
    expect(component.tituloModal).toBe('Cadastrar Marca');
  });

  it('should open modal for editing an existing brand', () => {
    const marca = { id: 1, nome: 'Marca 1' };
    spyOn(modalService, 'open').and.callThrough();
    component.editarMarca({}, marca, 0);
    expect(modalService.open).toHaveBeenCalled();
    expect(component.marcaParaEditar).toEqual(marca);
    expect(component.indiceParaEdicao).toBe(0);
    expect(component.tituloModal).toBe('Editar Marca');
  });

  it('should open modal for confirming deletion of a brand', () => {
    const marca = { id: 1, nome: 'Marca 1' };
    spyOn(modalService, 'open').and.callThrough();
    component.excluirMarca({}, marca, 0);
    expect(modalService.open).toHaveBeenCalled();
    expect(component.marcaParaExcluir).toEqual(marca);
    expect(component.indiceParaEdicao).toBe(0);
    expect(component.tituloModal).toBe('Deleter Marca');
  });

  it('should reset filtered list when search term is empty', () => {
    const termoPesquisa = '';
    component.listaMarcasOriginal = [
      { id: 1, nome: 'Marca 1' },
      { id: 2, nome: 'Marca 2' },
    ];
    component.realizarPesquisa(termoPesquisa);
    expect(component.listaMarcasFiltrada.length).toBe(2);
  });

  it('should emit brand on vincular', () => {
    spyOn(component.retorno, 'emit').and.callThrough();
    const marca = { id: 1, nome: 'Marca 1' };
    component.vincular(marca);
    expect(component.retorno.emit).toHaveBeenCalledWith(marca);
  });
});
