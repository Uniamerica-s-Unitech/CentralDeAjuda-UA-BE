import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { TicketHistoricoComponent } from './ticket-historico.component';
import { of } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { TicketService } from 'src/app/services/ticket.service';
import { Aluno } from 'src/app/models/aluno';
import { Notebook } from 'src/app/models/notebook';

class TicketServiceMock {
  listarHistoricos() {
    return of([]);
  }
}

describe('TicketHistoricoComponent', () => {
  let component: TicketHistoricoComponent;
  let fixture: ComponentFixture<TicketHistoricoComponent>;
  let ticketService: TicketService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TicketHistoricoComponent],
      imports: [HttpClientTestingModule],
      providers: [{ provide: TicketService, useClass: TicketServiceMock }],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
    });
    fixture = TestBed.createComponent(TicketHistoricoComponent);
    component = fixture.componentInstance;
    ticketService = TestBed.inject(TicketService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should update listaTicketsFiltrada with the result of listarFinalizados', fakeAsync(() => {
    const mockTickets = [
      {  id: 1, alunoId: {} as Aluno, notebookId: {} as Notebook, dataDevolucao: new Date(), dataEntrega: new Date(), observacao: "123" }
    ];
    spyOn(ticketService, 'listarHistoricos').and.returnValue(of(mockTickets));

    component.listarTickets();
    tick();

    expect(component.listaTicketsHistoricosFiltrada).toEqual(mockTickets);
    expect(component.listaTicketsHistoricosOriginal).toEqual(mockTickets);
  }));
});
