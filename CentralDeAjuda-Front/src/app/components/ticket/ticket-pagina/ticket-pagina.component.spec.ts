import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketPaginaComponent } from './ticket-pagina.component';

describe('TicketPaginaComponent', () => {
  let component: TicketPaginaComponent;
  let fixture: ComponentFixture<TicketPaginaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TicketPaginaComponent]
    });
    fixture = TestBed.createComponent(TicketPaginaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
