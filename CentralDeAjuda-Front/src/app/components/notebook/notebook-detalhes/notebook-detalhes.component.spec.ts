import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotebookDetalhesComponent } from './notebook-detalhes.component';

describe('NotebookDetalhesComponent', () => {
  let component: NotebookDetalhesComponent;
  let fixture: ComponentFixture<NotebookDetalhesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NotebookDetalhesComponent]
    });
    fixture = TestBed.createComponent(NotebookDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
