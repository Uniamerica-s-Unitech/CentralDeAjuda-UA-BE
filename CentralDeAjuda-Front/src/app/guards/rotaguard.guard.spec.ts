// import { TestBed } from '@angular/core/testing';
// import { CanActivateFn } from '@angular/router';

// import { rotaguardGuard } from './rotaguard.guard';
// import { HttpClientTestingModule } from '@angular/common/http/testing';
// import { RouterTestingModule } from '@angular/router/testing';
// import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

// describe('rotaguardGuard', () => {
//   const executeGuard: CanActivateFn = (...guardParameters) => 
//       TestBed.runInInjectionContext(() => rotaguardGuard(...guardParameters));

//   beforeEach(() => {
//     TestBed.configureTestingModule({
//       imports: [HttpClientTestingModule, RouterTestingModule], //SE O COMPONENTE INVOCA ALGUM SERVICE, INCLUÍMOS ESSA DEPENDÊNCIA DE HTTP DE TESTE
//       declarations: [rotaguardGuard],
//       schemas: [
//         CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
//     });
//   });

//   it('should be created', () => {
//     expect(executeGuard).toBeTruthy();
//   });
// });
