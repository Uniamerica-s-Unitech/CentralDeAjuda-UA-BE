import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { AlunoDetalhesComponent } from './aluno-detalhes.component';
import { AlunoService } from 'src/app/services/aluno.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ToastrModule } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { Aluno } from 'src/app/models/aluno';


class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

describe('AlunoDetalhesComponent', () => {
  let component: AlunoDetalhesComponent;
  let fixture: ComponentFixture<AlunoDetalhesComponent>;
  let alunoService: AlunoService;


  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AlunoDetalhesComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [{ provide: ToastrModule, useClass: ToastrServiceMock }],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(AlunoDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  beforeEach(()=>{
    alunoService = TestBed.inject(AlunoService);
    let aluno = new Aluno();
    aluno.nome = "aluno";
    fixture.detectChanges();
  });
  it('deve chamar o método save ao enviar o formulário', fakeAsync(() => { //colocar o fakeAsync toda vez que rolar coisa assíncrona
    let spy = spyOn(alunoService, 'save').and.callThrough();

    let form = fixture.debugElement.nativeElement.querySelector('form');
    form.dispatchEvent(new Event('ngSubmit')); //disparar o mesmo evento que tá configurado na tag

    tick(); //simular uma demora assíncrona
    fixture.detectChanges();
    expect(spy).toHaveBeenCalled();
  }));
  it('deve chamar o método save ao enviar o formulário passando objeto', fakeAsync(() => {
    let spy = spyOn(alunoService, 'save').and.callThrough();

    let aluno = new Aluno();
    aluno.nome = "aluno";
    component.aluno = aluno;
    fixture.detectChanges();

    let form = fixture.debugElement.nativeElement.querySelector('form');
    console.log(form);
    form.dispatchEvent(new Event('ngSubmit'));

    tick();
    fixture.detectChanges();
    expect(spy).toHaveBeenCalledWith(aluno);
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display an error message if the form is invalid', fakeAsync(() => {
    spyOn(component.toastr, 'error');
    
    component.salvar({ valid: false });

    tick();

    expect(component.toastr.error).toHaveBeenCalledWith('Formulário inválido. Preencha os campos corretamente');
  }));

    
}



);
