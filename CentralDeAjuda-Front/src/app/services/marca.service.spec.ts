import { TestBed } from '@angular/core/testing';

import { MarcaService } from './marca.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TicketService } from './ticket.service';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { Marca } from '../models/marca';
import { of } from 'rxjs';

describe('MarcaService', () => {
  let service: MarcaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TicketService],
      schemas: [
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    service = TestBed.inject(MarcaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('listar marcaes', () => {
    const mockMarcaes: Marca[] = [{ id: 1,nome: "marca" }];
    spyOn(service.http, 'get').and.returnValue(of(mockMarcaes));

    service.listar().subscribe(marcaes => {
      expect(marcaes).toEqual(mockMarcaes);
    });

    expect(service.http.get).toHaveBeenCalledWith(`${service.API}/lista`);
  });

  it('salvar um marca', () => {
    const mockMarca: Marca = { id: 1,nome: "marca" };
    spyOn(service.http, 'post').and.returnValue(of({}));

    service.save(mockMarca).subscribe(() => {
      expect(service.http.post).toHaveBeenCalledWith(service.API, mockMarca);
    });
  });

  it('editar um marca', () => {
    const mockMarca: Marca = { id: 1,nome: "marca" };
    spyOn(service.http, 'put').and.returnValue(of({}));

    service.save(mockMarca).subscribe(() => {
      expect(service.http.put).toHaveBeenCalledWith(`${service.API}/${mockMarca.id}`, mockMarca);
    });
  });

  it('deletar um marca', () => {
    const marcaId = 1;
    spyOn(service.http, 'delete').and.returnValue(of({}));

    service.deletar(marcaId).subscribe(() => {
      expect(service.http.delete).toHaveBeenCalledWith(`${service.API}/${marcaId}`);
    });
  });
});
