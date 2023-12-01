import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastrarListComponent } from './cadastrar-list.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

describe('CadastrarListComponent', () => {
  let component: CadastrarListComponent;
  let fixture: ComponentFixture<CadastrarListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CadastrarListComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [{ provide: ToastrService, useClass: ToastrServiceMock }],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(CadastrarListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
