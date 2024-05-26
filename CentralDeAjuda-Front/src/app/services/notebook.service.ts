import { Injectable, inject } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Notebook } from '../models/notebook';
import { Mensagem } from '../models/mensagem';

@Injectable({
  providedIn: 'root'
})
export class NotebookService {
  API: string = 'https://172.21.247.48:443/notebook'
  http = inject(HttpClient);

  constructor() { }

  listarNotebooksSemVinculos(): Observable<Notebook[]> {
    return this.http.get<Notebook[]>(`${this.API}` + "/listaSemVinculo");
  }

  listar(): Observable<Notebook[]> {
    return this.http.get<Notebook[]>(`${this.API}` + "/lista");
  }

  save(notebook: Notebook): Observable<Mensagem> {
    if (notebook.id) {
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Mensagem>(this.API+"/"+`${notebook.id}`, notebook);
    } else {
      // Caso contrário, crie uma nova pessoa
      return this.http.post<Mensagem>(this.API, notebook);
    }
  }

  deletar(id: number): Observable<Mensagem> {
    return this.http.delete<Mensagem>(this.API + "/" + `${id}`);
  }
}

