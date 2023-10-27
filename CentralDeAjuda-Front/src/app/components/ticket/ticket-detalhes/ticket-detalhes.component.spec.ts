import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketDetalhesComponent } from './ticket-detalhes.component';

describe('TicketDetalhesComponent', () => {
  let component: TicketDetalhesComponent;
  let fixture: ComponentFixture<TicketDetalhesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TicketDetalhesComponent]
    });
    fixture = TestBed.createComponent(TicketDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
