import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Ticket } from '../models/ticket';
import { Observable } from 'rxjs';
import { Mensagem } from '../models/mensagem';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  API: string = 'http://localhost:8080/api/ticket'
  http = inject(HttpClient);
  loginService = inject(LoginService);  
  nomeUser = this.loginService.getUsername();

  constructor() { }

  listarAbertos(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.API}` + "/abertos");
  }
  listarHistoricos(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.API}` + "/historico");
  }

  save(ticket: Ticket): Observable<Mensagem> {
    if (ticket.id) {
      // console.log("Data de devolucao",ticket.dataDevolucao);
      return this.http.put<Mensagem>(this.API+"/"+`${ticket.id}`, ticket);
    } else {
      // console.log("Data de entrega",ticket.dataEntrega);
      return this.http.post<Mensagem>(this.API, ticket);
    }
  }

  deletar(id: number): Observable<any> {
    return this.http.delete<Mensagem>(this.API + "/" + `${id}`, { params: { userExclusao: this.nomeUser } });
  }
}
