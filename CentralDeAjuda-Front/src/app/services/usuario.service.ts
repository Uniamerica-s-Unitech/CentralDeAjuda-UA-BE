import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  userURL = 'https://172.21.247.40/api/user/';

//   httpOptions = { headers: new HttpHeaders({'Content-Type' : 'application/json'})};

  constructor(private httpClient: HttpClient) { }

  public create(user: Usuario): Observable<any> {
    return this.httpClient.post<any>(this.userURL + 'create', user);
  }
}