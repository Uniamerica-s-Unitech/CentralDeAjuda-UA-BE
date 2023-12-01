import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { TicketDetalhesComponent } from './ticket-detalhes.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { TicketService } from 'src/app/services/ticket.service';
import { Ticket } from 'src/app/models/ticket';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

describe('TicketDetalhesComponent', () => {
  let component: TicketDetalhesComponent;
  let fixture: ComponentFixture<TicketDetalhesComponent>;
  let ticketService: TicketService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TicketDetalhesComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [{ provide: ToastrService, useClass: ToastrServiceMock }],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(TicketDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  beforeEach(() => {
    ticketService = TestBed.inject(TicketService);

    let ticket = new Ticket();
    ticket.observacao = "ok";
    component.ticket = ticket;
    fixture.detectChanges();
  });

  it('deve chamar o método save ao enviar o formulário', fakeAsync(() => { //colocar o fakeAsync toda vez que rolar coisa assíncrona
    let spy = spyOn(ticketService, 'save').and.callThrough();

    let form = fixture.debugElement.nativeElement.querySelector('form');
    form.dispatchEvent(new Event('ngSubmit')); //disparar o mesmo evento que tá configurado na tag

    tick(); //simular uma demora assíncrona
    fixture.detectChanges();
    expect(spy).toHaveBeenCalled();
  }));

  it('deve chamar o método save ao enviar o formulário passando objeto', fakeAsync(() => {
    let spy = spyOn(ticketService, 'save').and.callThrough();

    let ticket = new Ticket();
    ticket.observacao = "ok";
    component.ticket = ticket;
    fixture.detectChanges();

    let form = fixture.debugElement.nativeElement.querySelector('form');
    console.log(form);
    form.dispatchEvent(new Event('ngSubmit'));

    tick();
    fixture.detectChanges();
    expect(spy).toHaveBeenCalledWith(ticket);
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
