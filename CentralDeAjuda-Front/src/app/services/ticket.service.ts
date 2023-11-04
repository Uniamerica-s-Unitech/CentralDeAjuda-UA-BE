import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Ticket } from '../models/ticket';
import { Observable } from 'rxjs';
import { Mensagem } from '../models/mensagem';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  API: string = 'http://localhost:8080/ticket'
  http = inject(HttpClient);

  constructor() { }

  listarAbertos(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.API}` + "/abertos");
  }
  listarHistoricos(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.API}` + "/historico");
  }

  save(ticket: Ticket): Observable<Mensagem> {
    if (ticket.id) {
      console.log('a');
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Mensagem>(this.API+"/"+`${ticket.id}`, ticket);
    } else {
      // Caso contrário, crie uma nova pessoa
      console.log('b');
      return this.http.post<Mensagem>(this.API, ticket);
    }
  }

  deletar(id: number): Observable<any> {
    return this.http.delete<Mensagem>(this.API + "/" + `${id}`);
  }
}
