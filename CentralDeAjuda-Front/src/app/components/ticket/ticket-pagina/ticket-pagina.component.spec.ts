import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketPaginaComponent } from './ticket-pagina.component';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { NgbModal, NgbNav } from '@ng-bootstrap/ng-bootstrap';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';

describe('TicketPaginaComponent', () => {
  let component: TicketPaginaComponent;
  let fixture: ComponentFixture<TicketPaginaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TicketPaginaComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule,NgbNav],
      providers: [
        { provide: ToastrService },
        { provide: NgbModal},
      ],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(TicketPaginaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
