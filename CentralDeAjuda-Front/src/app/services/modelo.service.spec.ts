import { TestBed } from '@angular/core/testing';

import { ModeloService } from './modelo.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { Modelo } from '../models/modelo';
import { Marca } from '../models/marca';
import { of } from 'rxjs';

describe('ModeloService', () => {
  let service: ModeloService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ModeloService],
      schemas: [
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    service = TestBed.inject(ModeloService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('listar modeloes', () => {
    const mockModeloes: Modelo[] = [{ id: 1,nome: "modelo", marcaId: {} as Marca }];
    spyOn(service.http, 'get').and.returnValue(of(mockModeloes));

    service.listar().subscribe(modeloes => {
      expect(modeloes).toEqual(mockModeloes);
    });

    expect(service.http.get).toHaveBeenCalledWith(`${service.API}/lista`);
  });

  it('salvar um modelo', () => {
    const mockModelo: Modelo = { id: 1,nome: "modelo", marcaId: {} as Marca };
    spyOn(service.http, 'post').and.returnValue(of({}));

    service.save(mockModelo).subscribe(() => {
      expect(service.http.post).toHaveBeenCalledWith(service.API, mockModelo);
    });
  });

  it('editar um modelo', () => {
    const mockModelo: Modelo = { id: 1,nome: "modelo", marcaId: {} as Marca };
    spyOn(service.http, 'put').and.returnValue(of({}));

    service.save(mockModelo).subscribe(() => {
      expect(service.http.put).toHaveBeenCalledWith(`${service.API}/${mockModelo.id}`, mockModelo);
    });
  });

  it('deletar um modelo', () => {
    const modeloId = 1;
    spyOn(service.http, 'delete').and.returnValue(of({}));

    service.deletar(modeloId).subscribe(() => {
      expect(service.http.delete).toHaveBeenCalledWith(`${service.API}/${modeloId}`);
    });
  });
});
