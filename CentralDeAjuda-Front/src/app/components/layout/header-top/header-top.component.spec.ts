import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderTopComponent } from './header-top.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

describe('HeaderTopComponent', () => {
  let component: HeaderTopComponent;
  let fixture: ComponentFixture<HeaderTopComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HeaderTopComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [
        { provide: ToastrService },
        { provide: NgbModal },
      ],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(HeaderTopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
