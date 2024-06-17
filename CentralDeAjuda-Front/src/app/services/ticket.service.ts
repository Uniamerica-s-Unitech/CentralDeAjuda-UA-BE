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
  API: string = 'https://172.21.247.40/api/ticket'
  http = inject(HttpClient);
  loginService = inject(LoginService);  

  constructor() { }

  listarAbertos(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.API}` + "/abertos");
  }
  listarHistoricos(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.API}` + "/historico");
  }

  save(ticket: Ticket): Observable<Mensagem> {
    
    if (ticket.id) {
      const nomeUser = this.loginService.getUsername();
      // console.log("Data de devolucao",ticket.dataDevolucao);
      console.log(nomeUser);
      return this.http.put<Mensagem>(this.API+"/"+`${ticket.id}`, { ticket, userAlteracao: nomeUser });
    } else {
      const nomeUser = this.loginService.getUsername();
       console.log(nomeUser);
      return this.http.post<Mensagem>(this.API, { ticket, userCreacao: nomeUser });
    }
  }

  deletar(id: number): Observable<any> {
    const nomeUser = this.loginService.getUsername();
    return this.http.delete<Mensagem>(this.API + "/" + `${id}`, { params: { userExclusao: nomeUser } });
  }
}
