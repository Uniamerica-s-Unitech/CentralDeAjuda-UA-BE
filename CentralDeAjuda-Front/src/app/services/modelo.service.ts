import { Injectable, inject } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Modelo } from '../models/modelo';

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

  save(modelo: Modelo): Observable<Modelo> {
    if (modelo.id) {
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Modelo>(this.API+"/"+`${modelo.id}`, modelo);
    } else {
      // Caso contrário, crie uma nova pessoa
      return this.http.post<Modelo>(this.API, modelo);
    }
  }

  deletar(id: number): Observable<any> {
    return this.http.delete(this.API + "/" + `${id}`);
  }
}
