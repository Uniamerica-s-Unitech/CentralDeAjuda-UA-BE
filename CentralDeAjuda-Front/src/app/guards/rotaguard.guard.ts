import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../services/login.service';
import { KeycloakService } from '../services/KeycloakService';

export const rotaguardGuard: CanActivateFn = () => {//route, state
  // let loginService = inject(LoginService);
  // let roteador = inject(Router);

  // if (loginService.getToken() == null) {
  //   roteador.navigate(['/login']);
  //   return false;
  // } else
  // return true;
  const tokenService = inject(KeycloakService);
  const router = inject(Router);
  if (tokenService.keycloak.isTokenExpired()) {
    router.navigate(['https://172.21.247.50:4200/#/admin/aluno']);
    return false;
  }
  return true;
};