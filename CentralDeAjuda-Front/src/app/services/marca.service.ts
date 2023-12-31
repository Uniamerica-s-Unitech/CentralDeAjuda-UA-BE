import { Injectable, inject } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Marca } from '../models/marca';
import { Mensagem } from '../models/mensagem';

@Injectable({
  providedIn: 'root'
})
export class MarcaService {
  API: string = 'http://localhost:8080/marca'
  http = inject(HttpClient);

  constructor() { }

  listar(): Observable<Marca[]> {
    return this.http.get<Marca[]>(`${this.API}` + "/lista");
  }

  save(marca: Marca): Observable<Mensagem> {
    if (marca.id) {
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Mensagem>(this.API+"/"+`${marca.id}`, marca);
    } else {
      // Caso contrário, crie uma nova pessoa
      return this.http.post<Mensagem>(this.API, marca);
    }
  }

  deletar(id: number): Observable<any> {
    return this.http.delete<Mensagem>(this.API + "/" + `${id}`);
  }
}
