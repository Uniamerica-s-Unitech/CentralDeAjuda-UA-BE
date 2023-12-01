import { TestBed } from '@angular/core/testing';

import { TicketService } from './ticket.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { of } from 'rxjs';
import { Ticket } from '../models/ticket';
import { Aluno } from '../models/aluno';
import { Notebook } from '../models/notebook';

describe('TicketService', () => {
  let service: TicketService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TicketService],
      schemas: [
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    service = TestBed.inject(TicketService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('listar tickets abertos', () => {
    const mockTickets: Ticket[] = [{ id: 1, alunoId: {} as Aluno, notebookId: {} as Notebook, dataDevolucao: new Date(), dataEntrega: new Date(), observacao: "123"}];
    spyOn(service.http, 'get').and.returnValue(of(mockTickets));

    service.listarAbertos().subscribe(tickets => {
      expect(tickets).toEqual(mockTickets);
    });

    expect(service.http.get).toHaveBeenCalledWith(`${service.API}/abertos`);
  });

  it('listar tickets finalizados', () => {
    const mockTickets: Ticket[] = [{ id: 2, alunoId: {} as Aluno, notebookId: {} as Notebook, dataDevolucao: new Date(), dataEntrega: new Date(), observacao: "123" }];
    spyOn(service.http, 'get').and.returnValue(of(mockTickets));

    service.listarHistoricos().subscribe(tickets => {
      expect(tickets).toEqual(mockTickets);
    });

    expect(service.http.get).toHaveBeenCalledWith(`${service.API}/historico`);
  });

  it('salvar um ticket', () => {
    const mockTicket: Ticket = {id: 2, alunoId: {} as Aluno, notebookId: {} as Notebook, dataDevolucao: new Date(), dataEntrega: new Date(), observacao: "123"  };
    spyOn(service.http, 'post').and.returnValue(of({}));

    service.save(mockTicket).subscribe(() => {
      expect(service.http.post).toHaveBeenCalledWith(service.API, mockTicket);
    });
  });

  it('editar um ticket', () => {
    const mockTicket: Ticket = { id: 2, alunoId: {} as Aluno, notebookId: {} as Notebook, dataDevolucao: new Date(), dataEntrega: new Date(), observacao: "123"  };
    spyOn(service.http, 'put').and.returnValue(of({}));

    service.save(mockTicket).subscribe(() => {
      expect(service.http.put).toHaveBeenCalledWith(`${service.API}/${mockTicket.id}`, mockTicket);
    });
  });

  it('deletar um ticket', () => {
    const ticketId = 1;
    spyOn(service.http, 'delete').and.returnValue(of({}));

    service.deletar(ticketId).subscribe(() => {
      expect(service.http.delete).toHaveBeenCalledWith(`${service.API}/${ticketId}`);
    });
  });
});
