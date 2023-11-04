import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotebookListaComponent } from './notebook-lista.component';

describe('NotebookListarComponent', () => {
  let component: NotebookListaComponent;
  let fixture: ComponentFixture<NotebookListaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NotebookListaComponent]
    });
    fixture = TestBed.createComponent(NotebookListaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
