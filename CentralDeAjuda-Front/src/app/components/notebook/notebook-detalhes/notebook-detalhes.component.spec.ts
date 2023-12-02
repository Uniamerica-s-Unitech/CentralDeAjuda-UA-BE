import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NotebookDetalhesComponent } from './notebook-detalhes.component';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { of } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { NotebookService } from 'src/app/services/notebook.service';
import { Modelo } from 'src/app/models/modelo';

class NotebookServiceMock {
  save(notebook: any) {
    return of({ mensagem: 'Notebook salva com sucesso.' });
  }
}

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

describe('NotebookDetalhesComponent', () => {
  let component: NotebookDetalhesComponent;
  let fixture: ComponentFixture<NotebookDetalhesComponent>;
  let notebookService: NotebookService;
  let toastrService: ToastrService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NotebookDetalhesComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [
        { provide: ToastrService, useClass: ToastrServiceMock },
        { provide: NgbModal},
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
    });
    fixture = TestBed.createComponent(NotebookDetalhesComponent);
    component = fixture.componentInstance;
    notebookService = TestBed.inject(NotebookService);
    toastrService = TestBed.inject(ToastrService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should save notebook on valid form submission', () => {
    spyOn(notebookService, 'save').and.returnValue(of({ mensagem: 'Notebook salva com sucesso.',status: 200 }));
    spyOn(toastrService, 'success').and.callThrough();
    spyOn(component.retorno, 'emit').and.callThrough();

    const form = {
      valid: true,
    };
    component.notebook = { id: 1, patrimonio:'123',modeloId: {} as Modelo }; // Adicione um valor numérico para 'id'

    component.salvar(form);

    expect(notebookService.save).toHaveBeenCalledWith({ id: 1, patrimonio:'123',modeloId: {} as Modelo  });
    expect(toastrService.success).toHaveBeenCalledWith('Notebook salva com sucesso.');
    expect(component.retorno.emit).toHaveBeenCalledWith({ mensagem: 'Notebook salva com sucesso.',status: 200  });
  });


    it('should show error message on invalid form submission', () => {
      spyOn(notebookService, 'save').and.callThrough();
      spyOn(toastrService, 'error').and.callThrough();
      spyOn(component.retorno, 'emit').and.callThrough();

      const form = {
        valid: false,
      };
      component.notebook = { id: 1, patrimonio:'123',modeloId: {} as Modelo  };

      component.salvar(form);

      expect(notebookService.save).not.toHaveBeenCalled();
      expect(toastrService.error).toHaveBeenCalledWith('Formulário inválido. Preencha os campos corretamente');
      expect(component.retorno.emit).not.toHaveBeenCalled();
    });
});
