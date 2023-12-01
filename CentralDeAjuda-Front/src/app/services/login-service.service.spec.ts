import { TestBed } from '@angular/core/testing';

import { LoginService } from './login.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { User } from '../models/user';
import { of } from 'rxjs';

describe('LoginServiceService', () => {
  let service: LoginService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [LoginService],
      schemas: [
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    service = TestBed.inject(LoginService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('listar users', () => {
    const mockUsers: User[] = [{ id: 1, username: 'User Teste', password: "senha",role: "admin"}];
    spyOn(service.http, 'get').and.returnValue(of(mockUsers));

    service.listarUsers().subscribe(users => {
      expect(users).toEqual(mockUsers);
    });

    expect(service.http.get).toHaveBeenCalledWith(`${service.API}/lista`);
  });

  it('salvar um user', () => {
    const mockUser: User = {id: 1, username: 'User Teste', password: "senha",role: "admin" };
    spyOn(service.http, 'post').and.returnValue(of({}));

    service.saveUser(mockUser).subscribe(() => {
      expect(service.http.post).toHaveBeenCalledWith(service.API, mockUser);
    });
  });

  it('editar um user', () => {
    const mockUser: User = { id: 1, username: 'User Teste', password: "senha",role: "admin" };
    spyOn(service.http, 'put').and.returnValue(of({}));

    service.saveUser(mockUser).subscribe(() => {
      expect(service.http.put).toHaveBeenCalledWith(`${service.API}/${mockUser.id}`, mockUser);
    });
  });

  it('deletar um user', () => {
    const userId = 1;
    spyOn(service.http, 'delete').and.returnValue(of({}));

    service.deletarUser(userId).subscribe(() => {
      expect(service.http.delete).toHaveBeenCalledWith(`${service.API}/${userId}`);
    });
  });
});
