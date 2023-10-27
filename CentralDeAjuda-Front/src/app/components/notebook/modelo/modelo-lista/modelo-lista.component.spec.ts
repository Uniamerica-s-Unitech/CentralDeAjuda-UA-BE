import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModeloListaComponent } from './modelo-lista.component';

describe('ModeloListaComponent', () => {
  let component: ModeloListaComponent;
  let fixture: ComponentFixture<ModeloListaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModeloListaComponent]
    });
    fixture = TestBed.createComponent(ModeloListaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
