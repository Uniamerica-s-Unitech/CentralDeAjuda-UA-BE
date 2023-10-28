import { Injectable, inject } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Marca } from '../models/marca';

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

  save(marca: Marca): Observable<Marca> {
    if (marca.id) {
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Marca>(this.API+"/"+`${marca.id}`, marca);
    } else {
      // Caso contrário, crie uma nova pessoa
      return this.http.post<Marca>(this.API, marca);
    }
  }

  deletar(id: number): Observable<any> {
    return this.http.delete(this.API + "/" + `${id}`);
  }
}
