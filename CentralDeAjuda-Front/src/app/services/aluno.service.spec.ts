import { TestBed } from '@angular/core/testing';

import { AlunoService } from './aluno.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { of } from 'rxjs';
import { Aluno } from '../models/aluno';

describe('AlunoService', () => {
  let service: AlunoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AlunoService],
      schemas: [
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    
    service = TestBed.inject(AlunoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('listar alunoes', () => {
    const mockAlunoes: Aluno[] = [{ id: 1, nome: 'aluno', ra: 123456}];
    spyOn(service.http, 'get').and.returnValue(of(mockAlunoes));

    service.listar().subscribe(alunoes => {
      expect(alunoes).toEqual(mockAlunoes);
    });

    expect(service.http.get).toHaveBeenCalledWith(`${service.API}/lista`);
  });

  it('salvar um aluno', () => {
    const mockAluno: Aluno = {id: 1, nome: 'Novo Aluno',  ra: 123456 };
    spyOn(service.http, 'post').and.returnValue(of({}));

    service.save(mockAluno).subscribe(() => {
      expect(service.http.post).toHaveBeenCalledWith(service.API, mockAluno);
    });
  });

  it('editar um aluno', () => {
    const mockAluno: Aluno = { id: 1, nome: 'Aluno Atualizado',  ra: 123456 };
    spyOn(service.http, 'put').and.returnValue(of({}));

    service.save(mockAluno).subscribe(() => {
      expect(service.http.put).toHaveBeenCalledWith(`${service.API}/${mockAluno.id}`, mockAluno);
    });
  });

  it('deletar um aluno', () => {
    const alunoId = 1;
    spyOn(service.http, 'delete').and.returnValue(of({}));

    service.deletar(alunoId).subscribe(() => {
      expect(service.http.delete).toHaveBeenCalledWith(`${service.API}/${alunoId}`);
    });
  });
});
