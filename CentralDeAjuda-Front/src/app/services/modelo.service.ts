import { Injectable, inject } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Modelo } from '../models/modelo';
import { Mensagem } from '../models/mensagem';

@Injectable({
  providedIn: 'root'
})
export class ModeloService {
  API: string = 'http://localhost:8080/modelo'
  http = inject(HttpClient);

  constructor() { }

  listar(): Observable<Modelo[]> {
    return this.http.get<Modelo[]>(`${this.API}` + "/lista");
  }

  save(modelo: Modelo): Observable<Mensagem> {
    if (modelo.id) {
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Mensagem>(this.API+"/"+`${modelo.id}`, modelo);
    } else {
      // Caso contrário, crie uma nova pessoa
      return this.http.post<Mensagem>(this.API, modelo);
    }
  }

  deletar(id: number): Observable<any> {
    return this.http.delete<Mensagem>(this.API + "/" + `${id}`);
  }
}
