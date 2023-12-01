import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketListaComponent } from './ticket-lista.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

class NgbModalMock {
  open(content: any, options: any): NgbModalRef {
    return { componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef;
  }
}


describe('TicketListaComponent', () => {
  let component: TicketListaComponent;
  let fixture: ComponentFixture<TicketListaComponent>;
  let modalService: NgbModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TicketListaComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [
        { provide: ToastrService, useClass: ToastrServiceMock },
        { provide: NgbModal, useClass: NgbModalMock },
      ],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(TicketListaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open modal for adding a new ticket', () => {
    spyOn(modalService, 'open').and.callThrough();

    component.cadastrarTicket({});

    expect(modalService.open).toHaveBeenCalled();
  });
});
