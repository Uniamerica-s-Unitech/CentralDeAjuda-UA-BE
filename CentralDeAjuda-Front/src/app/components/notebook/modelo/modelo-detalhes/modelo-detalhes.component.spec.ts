import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModeloDetalhesComponent } from './modelo-detalhes.component';

describe('ModeloDetalhesComponent', () => {
  let component: ModeloDetalhesComponent;
  let fixture: ComponentFixture<ModeloDetalhesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModeloDetalhesComponent]
    });
    fixture = TestBed.createComponent(ModeloDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
