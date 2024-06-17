import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogDetalhesComponent } from './log-detalhes.component';

describe('LogDetalhesComponent', () => {
  let component: LogDetalhesComponent;
  let fixture: ComponentFixture<LogDetalhesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LogDetalhesComponent]
    });
    fixture = TestBed.createComponent(LogDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
