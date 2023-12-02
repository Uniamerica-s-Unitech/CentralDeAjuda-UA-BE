import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketListaComponent } from './ticket-lista.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { Ticket } from 'src/app/models/ticket';
import { Aluno } from 'src/app/models/aluno';
import { Notebook } from 'src/app/models/notebook';
import { of } from 'rxjs';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

class NgbModalMock {
  open(content: any, options: any): NgbModalRef {
    return { componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef;
  }

  // Adicione um spy para dismissAll
  dismissAll(): void {}
}


describe('TicketListaComponent', () => {
  let component: TicketListaComponent;
  let fixture: ComponentFixture<TicketListaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TicketListaComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [
        { provide: ToastrService, useClass: ToastrServiceMock },
        { provide: NgbModal, useClass: NgbModalMock },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
    });
    fixture = TestBed.createComponent(TicketListaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open modal for creating a new ticket', () => {
    spyOn(component.modalService, 'open').and.returnValue({ componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef);

    component.cadastrarTicket({});

    expect(component.modalService.open).toHaveBeenCalled();
    expect(component.ticketParaEditar).toEqual(new Ticket());
    expect(component.tituloModal).toBe('Cadastrar Ticket');
  });

  it('should open modal for editing an existing ticket', () => {
    const ticket = {  id: 1, alunoId: {} as Aluno, notebookId: {} as Notebook, dataDevolucao: new Date(), dataEntrega: new Date(), observacao: "123" };
    spyOn(component.modalService, 'open').and.returnValue({ componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef);

    component.cancelarTicket({}, ticket, 0);

    expect(component.modalService.open).toHaveBeenCalled();
    expect(component.ticketParaEditar).toEqual(ticket);
    expect(component.indiceParaEdicao).toBe(0);
    expect(component.tituloModal).toBe('Excluir Ticket');
  });
});
