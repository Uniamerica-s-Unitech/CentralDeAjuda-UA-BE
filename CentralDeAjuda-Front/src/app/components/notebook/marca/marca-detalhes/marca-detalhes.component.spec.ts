import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { MarcaDetalhesComponent } from './marca-detalhes.component';
import { MarcaService } from 'src/app/services/marca.service';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { of } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

class MarcaServiceMock {
  save(marca: any) {
    return of({ mensagem: 'Marca salva com sucesso.' });
  }
}

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

describe('MarcaDetalhesComponent', () => {
  let component: MarcaDetalhesComponent;
  let fixture: ComponentFixture<MarcaDetalhesComponent>;
  let marcaService: MarcaService;
  let toastrService: ToastrService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MarcaDetalhesComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [
        { provide: ToastrService, useClass: ToastrServiceMock },
        { provide: NgbModal},
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
    });
    fixture = TestBed.createComponent(MarcaDetalhesComponent);
    component = fixture.componentInstance;
    marcaService = TestBed.inject(MarcaService);
    toastrService = TestBed.inject(ToastrService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should save marca on valid form submission', () => {
    spyOn(marcaService, 'save').and.returnValue(of({ mensagem: 'Marca salva com sucesso.',status: 200 }));
    spyOn(toastrService, 'success').and.callThrough();
    spyOn(component.retorno, 'emit').and.callThrough();

    const form = {
      valid: true,
    };
    component.marca = { id: 1, nome: 'Teste' }; // Adicione um valor numérico para 'id'

    component.salvar(form);

    expect(marcaService.save).toHaveBeenCalledWith({ id: 1, nome: 'Teste' });
    expect(toastrService.success).toHaveBeenCalledWith('Marca salva com sucesso.');
    expect(component.retorno.emit).toHaveBeenCalledWith({ mensagem: 'Marca salva com sucesso.',status: 200  });
  });


  it('should show error message on invalid form submission', () => {
    spyOn(marcaService, 'save').and.callThrough();
    spyOn(toastrService, 'error').and.callThrough();
    spyOn(component.retorno, 'emit').and.callThrough();

    const form = {
      valid: false,
    };
    component.marca = { id: 1, nome: 'Teste' };

    component.salvar(form);

    expect(marcaService.save).not.toHaveBeenCalled();
    expect(toastrService.error).toHaveBeenCalledWith('Formulário inválido. Preencha os campos corretamente');
    expect(component.retorno.emit).not.toHaveBeenCalled();
  });
  });
