import { TestBed } from '@angular/core/testing';

import { NotebookService } from './notebook.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { of } from 'rxjs';
import { Notebook } from '../models/notebook';
import { Modelo } from '../models/modelo';

describe('NotebookService', () => {
  let service: NotebookService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [NotebookService],
      schemas: [
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    service = TestBed.inject(NotebookService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('listar notebookes', () => {
    const mockNotebookes: Notebook[] = [{ id: 1, modeloId: {} as Modelo, patrimonio: "123"}];
    spyOn(service.http, 'get').and.returnValue(of(mockNotebookes));

    service.listar().subscribe(notebookes => {
      expect(notebookes).toEqual(mockNotebookes);
    });

    expect(service.http.get).toHaveBeenCalledWith(`${service.API}/lista`);
  });

  it('salvar um notebook', () => {
    const mockNotebook: Notebook = {id: 1, modeloId: {} as Modelo, patrimonio: "123" };
    spyOn(service.http, 'post').and.returnValue(of({}));

    service.save(mockNotebook).subscribe(() => {
      expect(service.http.post).toHaveBeenCalledWith(service.API, mockNotebook);
    });
  });

  it('editar um notebook', () => {
    const mockNotebook: Notebook = { id: 1, modeloId: {} as Modelo, patrimonio: "123"};
    spyOn(service.http, 'put').and.returnValue(of({}));

    service.save(mockNotebook).subscribe(() => {
      expect(service.http.put).toHaveBeenCalledWith(`${service.API}/${mockNotebook.id}`, mockNotebook);
    });
  });

  it('deletar um notebook', () => {
    const notebookId = 1;
    spyOn(service.http, 'delete').and.returnValue(of({}));

    service.deletar(notebookId).subscribe(() => {
      expect(service.http.delete).toHaveBeenCalledWith(`${service.API}/${notebookId}`);
    });
  });
});
