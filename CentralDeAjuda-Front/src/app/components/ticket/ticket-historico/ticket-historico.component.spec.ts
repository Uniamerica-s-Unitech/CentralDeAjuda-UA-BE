import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketHistoricoComponent } from './ticket-historico.component';

describe('TicketHistoricoComponent', () => {
  let component: TicketHistoricoComponent;
  let fixture: ComponentFixture<TicketHistoricoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TicketHistoricoComponent]
    });
    fixture = TestBed.createComponent(TicketHistoricoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
