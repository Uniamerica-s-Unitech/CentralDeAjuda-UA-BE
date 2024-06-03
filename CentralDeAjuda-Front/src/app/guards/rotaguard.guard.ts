// import { inject } from '@angular/core';
// import { CanActivateFn, Router } from '@angular/router';
// import { LoginService } from '../services/login.service';

// export const rotaguardGuard: CanActivateFn = (route, state) => {
//   let loginService = inject(LoginService);
//   let roteador = inject(Router);

//   if (loginService.getToken() == null) {
//     roteador.navigate(['/login']);
//     return false;
//   } else
//   return true;
// };
import { LoginService } from './../services/login.service';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class rotaguardGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router) { }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {

    const requiredRoles = next.data['requiredRoles'];
    if (!this.loginService.getIsLogged()) {
      this.router.navigate(['/']);
      return false;
    }
    const realRol = this.loginService.getIsAdmin() ? 'admin' : 'user';
    if (requiredRoles.indexOf(realRol) === -1) {
      this.router.navigate(['/']);
      return false;
    }
    return true;
  }

}