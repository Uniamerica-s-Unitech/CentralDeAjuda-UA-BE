import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketListaComponent } from './ticket-lista.component';

describe('TicketListaComponent', () => {
  let component: TicketListaComponent;
  let fixture: ComponentFixture<TicketListaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TicketListaComponent]
    });
    fixture = TestBed.createComponent(TicketListaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
