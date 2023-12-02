import { ComponentFixture, TestBed } from "@angular/core/testing";
import { ToastrModule, ToastrService } from "ngx-toastr";
import { of } from "rxjs";
import { AlunoService } from "src/app/services/aluno.service";
import { AlunoDetalhesComponent } from "./aluno-detalhes.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { FormsModule } from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from "@angular/core";

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

describe('AlunoDetalhesComponent', () => {
  let component: AlunoDetalhesComponent;
  let fixture: ComponentFixture<AlunoDetalhesComponent>;
  let toastrService: ToastrService;
  let alunoService: AlunoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AlunoDetalhesComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [
        { provide: ToastrService, useClass: ToastrServiceMock },
        { provide: NgbModal },
        AlunoService, // Adicione o serviço real, não o mock
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
    });
    fixture = TestBed.createComponent(AlunoDetalhesComponent);
    component = fixture.componentInstance;
    toastrService = TestBed.inject(ToastrService);
    alunoService = TestBed.inject(AlunoService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display error message on invalid form submission', () => {
    spyOn(toastrService, 'error').and.callThrough();
    spyOn(alunoService, 'save').and.returnValue(of({ mensagem: 'Aluno salvo com sucesso.',status: 200 }));

    const form = {
      valid: false,
    };
    component.aluno = { id: 1,ra: 123, nome: 'Teste' };

    component.salvar(form);

    expect(toastrService.error).toHaveBeenCalledWith('Formulário inválido. Preencha os campos corretamente');
    expect(alunoService.save).not.toHaveBeenCalled();
  });

  it('should save student on valid form submission', () => {
    spyOn(toastrService, 'success').and.callThrough();
    spyOn(alunoService, 'save').and.returnValue(of({ mensagem: 'Aluno salvo com sucesso.' ,status: 200}));
    spyOn(component.retorno, 'emit').and.callThrough();

    const form = {
      valid: true,
    };
    component.aluno = { id: 1,ra: 123, nome: 'Teste' };

    component.salvar(form);

    expect(alunoService.save).toHaveBeenCalledWith(component.aluno);
    expect(toastrService.success).toHaveBeenCalledWith('Aluno salvo com sucesso.');
    expect(component.retorno.emit).toHaveBeenCalledWith({ mensagem: 'Aluno salvo com sucesso.',status: 200 });
  });
});
