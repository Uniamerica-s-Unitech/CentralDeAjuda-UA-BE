import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotebookPaginaComponent } from './notebook-pagina.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { NgbNav } from '@ng-bootstrap/ng-bootstrap';

describe('NotebookPaginaComponent', () => {
  let component: NotebookPaginaComponent;
  let fixture: ComponentFixture<NotebookPaginaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NotebookPaginaComponent],
      imports: [HttpClientTestingModule,NgbNav],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
    });
    fixture = TestBed.createComponent(NotebookPaginaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have the default active value set to 1', () => {
    expect(component.active).toBe(1);
  });

  it('should set active value to 2 when changing active', () => {
    component.active = 2;
    expect(component.active).toBe(2);
  });
});
