import { Injectable, inject } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  constructor(private oauthService: OAuthService, private router: Router){}

  public login(): void {
    this.oauthService.initImplicitFlowInternal();
    this.router.navigate(['/ticket']);
  }

  public logout(): void {
    this.oauthService.logOut();
  }

  public getUsername(): string {
    return this.oauthService.getIdentityClaims()[`preferred_username`];
  }

  public getIsLogged(): boolean {
    return (this.oauthService.hasValidIdToken() && this.oauthService.hasValidAccessToken());
  }

  public getIsAdmin(): boolean {
    const token = this.oauthService.getAccessToken();
    const payload = token.split('.')[1];
    const payloadDecodedJson = atob(payload);
    const payloadDecoded = JSON.parse(payloadDecodedJson);
    // console.log(payloadDecoded);
    return payloadDecoded.realm_access.roles.indexOf('realm-admin') !== -1;
  }














  // API: string = 'https://172.21.247.40/api/login';
  // http = inject(HttpClient);

  // constructor() { }


  // logar(login: Login): Observable<User> {
  //   return this.http.post<User>(this.API, login);
  // }

  // deslogar(): Observable<any> {
  //   return this.http.get<any>(this.API+'/deslogar');
  // }

  // addToken(token: string){
  //   localStorage.setItem('token', token);
  // }

  // removerToken(){
  //   localStorage.removeItem('token');
  // }

  // getToken(){
  //   return localStorage.getItem('token');
  // }

  // listarUsers(): Observable<User[]> {
  //   return this.http.get<User[]>(`${this.API}`+'/lista');
  // }

  // saveUser(user: User): Observable<Mensagem> {
  //   if (user.id) {
  //     return this.http.put<Mensagem>(this.API+"/"+`${user.id}`, user);
  //   } else {
  //     return this.http.post<Mensagem>(this.API+"/user", user);
  //   }
  // }

  // deletarUser(id: number): Observable<Mensagem> {
  //   return this.http.delete<Mensagem>(this.API + "/" + `${id}`);
  // }
}