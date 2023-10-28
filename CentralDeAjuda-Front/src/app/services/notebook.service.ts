import { Injectable, inject } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Notebook } from '../models/notebook';

@Injectable({
  providedIn: 'root'
})
export class NotebookService {
  API: string = 'http://localhost:8080/notebook'
  http = inject(HttpClient);

  constructor() { }

  listar(): Observable<Notebook[]> {
    return this.http.get<Notebook[]>(`${this.API}` + "/lista");
  }

  save(notebook: Notebook): Observable<Notebook> {
    if (notebook.id) {
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Notebook>(this.API+"/"+`${notebook.id}`, notebook);
    } else {
      // Caso contrário, crie uma nova pessoa
      return this.http.post<Notebook>(this.API, notebook);
    }
  }

  deletar(id: number): Observable<any> {
    return this.http.delete(this.API + "/" + `${id}`);
  }
}

