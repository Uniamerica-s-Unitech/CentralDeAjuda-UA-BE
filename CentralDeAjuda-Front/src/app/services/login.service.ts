import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Login } from '../models/login';
import { Observable } from 'rxjs';
import { User } from '../models/user';
import { Mensagem } from '../models/mensagem';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  API: string = 'http://3.22.97.175:8080/api/login';
  http = inject(HttpClient);

  constructor() { }


  logar(login: Login): Observable<User> {
    return this.http.post<User>(this.API, login);
  }

  deslogar(): Observable<any> {
    return this.http.get<any>(this.API+'/deslogar');
  }

  addToken(token: string){
    localStorage.setItem('token', token);
  }

  removerToken(){
    localStorage.removeItem('token');
  }

  getToken(){
    return localStorage.getItem('token');
  }

  listarUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.API}`+'/lista');
  }

  saveUser(user: User): Observable<Mensagem> {
    if (user.id) {
      return this.http.put<Mensagem>(this.API+"/"+`${user.id}`, user);
    } else {
      return this.http.post<Mensagem>(this.API+"/user", user);
    }
  }

  deletarUser(id: number): Observable<Mensagem> {
    return this.http.delete<Mensagem>(this.API + "/" + `${id}`);
  }
}