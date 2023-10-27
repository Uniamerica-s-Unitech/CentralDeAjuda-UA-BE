import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotebookListarComponent } from './notebook-listar.component';

describe('NotebookListarComponent', () => {
  let component: NotebookListarComponent;
  let fixture: ComponentFixture<NotebookListarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NotebookListarComponent]
    });
    fixture = TestBed.createComponent(NotebookListarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
