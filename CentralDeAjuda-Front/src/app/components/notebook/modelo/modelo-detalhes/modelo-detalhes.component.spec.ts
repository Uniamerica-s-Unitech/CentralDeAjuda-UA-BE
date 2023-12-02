import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModeloDetalhesComponent } from './modelo-detalhes.component';
import { FormsModule } from '@angular/forms';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { of } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { ModeloService } from 'src/app/services/modelo.service';
import { Marca } from 'src/app/models/marca';

class ModeloServiceMock {
  save(modelo: any) {
    return of({ mensagem: 'Modelo salva com sucesso.' });
  }
}

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

describe('ModeloDetalhesComponent', () => {
  let component: ModeloDetalhesComponent;
  let fixture: ComponentFixture<ModeloDetalhesComponent>;
  let modeloService: ModeloService;
  let toastrService: ToastrService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModeloDetalhesComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [
        { provide: ToastrService, useClass: ToastrServiceMock },
        { provide: NgbModal},
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
    });
    fixture = TestBed.createComponent(ModeloDetalhesComponent);
    component = fixture.componentInstance;
    modeloService = TestBed.inject(ModeloService);
    toastrService = TestBed.inject(ToastrService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should save modelo on valid form submission', () => {
    spyOn(modeloService, 'save').and.returnValue(of({ mensagem: 'Modelo salva com sucesso.',status: 200 }));
    spyOn(toastrService, 'success').and.callThrough();
    spyOn(component.retorno, 'emit').and.callThrough();

    const form = {
      valid: true,
    };
    component.modelo = { id: 1, nome: 'Teste',marcaId: {} as Marca }; // Adicione um valor numérico para 'id'

    component.salvar(form);

    expect(modeloService.save).toHaveBeenCalledWith({ id: 1, nome: 'Teste',marcaId: {} as Marca  });
    expect(toastrService.success).toHaveBeenCalledWith('Modelo salva com sucesso.');
    expect(component.retorno.emit).toHaveBeenCalledWith({ mensagem: 'Modelo salva com sucesso.',status: 200  });
  });


    it('should show error message on invalid form submission', () => {
      spyOn(modeloService, 'save').and.callThrough();
      spyOn(toastrService, 'error').and.callThrough();
      spyOn(component.retorno, 'emit').and.callThrough();

      const form = {
        valid: false,
      };
      component.modelo = { id: 1, nome: 'Teste',marcaId: {} as Marca  };

      component.salvar(form);

      expect(modeloService.save).not.toHaveBeenCalled();
      expect(toastrService.error).toHaveBeenCalledWith('Formulário inválido. Preencha os campos corretamente');
      expect(component.retorno.emit).not.toHaveBeenCalled();
    });
  });
