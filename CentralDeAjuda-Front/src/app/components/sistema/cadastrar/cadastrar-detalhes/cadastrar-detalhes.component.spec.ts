import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { CadastrarDetalhesComponent } from './cadastrar-detalhes.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { ToastrService, ToastrModule } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { LoginService } from 'src/app/services/login.service';
import { Login } from 'src/app/models/login';
import { User } from 'src/app/models/user';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

describe('CadastrarDetalhesComponent', () => {
  let component: CadastrarDetalhesComponent;
  let fixture: ComponentFixture<CadastrarDetalhesComponent>;
  let loginService: LoginService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CadastrarDetalhesComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [{ provide: ToastrService, useClass: ToastrServiceMock }],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(CadastrarDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  beforeEach(() => {
    loginService = TestBed.inject(LoginService);

    let user = new User();
    user.username = "homam";
    component.user = user;
    fixture.detectChanges();
  });

  it('deve chamar o método save ao enviar o formulário', fakeAsync(() => { //colocar o fakeAsync toda vez que rolar coisa assíncrona
    let spy = spyOn(loginService, 'saveUser').and.callThrough();

    let form = fixture.debugElement.nativeElement.querySelector('form');
    form.dispatchEvent(new Event('ngSubmit')); //disparar o mesmo evento que tá configurado na tag

    tick(); //simular uma demora assíncrona
    fixture.detectChanges();
    expect(spy).toHaveBeenCalled();
  }));

  it('deve chamar o método saveUser ao enviar o formulário passando objeto', fakeAsync(() => {
    let spy = spyOn(loginService, 'saveUser').and.callThrough();

    let user = new User();
    user.username = "homam";
    component.user = user;
    fixture.detectChanges();

    let form = fixture.debugElement.nativeElement.querySelector('form');
    console.log(form);
    form.dispatchEvent(new Event('ngSubmit'));

    tick();
    fixture.detectChanges();
    expect(spy).toHaveBeenCalledWith(user);
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
});
