import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotebookPaginaComponent } from './notebook-pagina.component';

describe('NotebookPaginaComponent', () => {
  let component: NotebookPaginaComponent;
  let fixture: ComponentFixture<NotebookPaginaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NotebookPaginaComponent]
    });
    fixture = TestBed.createComponent(NotebookPaginaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
