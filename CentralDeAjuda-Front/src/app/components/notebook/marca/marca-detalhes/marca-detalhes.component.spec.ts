import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MarcaDetalhesComponent } from './marca-detalhes.component';

describe('MarcaDetalhesComponent', () => {
  let component: MarcaDetalhesComponent;
  let fixture: ComponentFixture<MarcaDetalhesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MarcaDetalhesComponent]
    });
    fixture = TestBed.createComponent(MarcaDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
