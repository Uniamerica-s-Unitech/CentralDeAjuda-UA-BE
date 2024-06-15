import { Injectable, inject } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Notebook } from '../models/notebook';
import { Mensagem } from '../models/mensagem';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class NotebookService {
  API: string = 'https://172.21.247.40/api/notebook'
  http = inject(HttpClient);
  loginService = inject(LoginService);  
  // nomeUser = this.loginService.getUsername();

  constructor() { }

  listarNotebooksSemVinculos(): Observable<Notebook[]> {
    return this.http.get<Notebook[]>(`${this.API}` + "/listaSemVinculo");
  }

  listar(): Observable<Notebook[]> {
    return this.http.get<Notebook[]>(`${this.API}` + "/lista");
  }

  save(notebook: Notebook): Observable<Mensagem> {
    const nomeUser = this.loginService.getUsername();
    if (notebook.id) {
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Mensagem>(`${this.API}/${notebook.id}`, { notebook, userAlteracao: nomeUser });
    } else {
      // Caso contrário, crie uma nova pessoa
      return this.http.post<Mensagem>(this.API, { notebook, userCreacao: nomeUser });
    }
  }

  deletar(id: number): Observable<Mensagem> {
    const nomeUser = this.loginService.getUsername();
    return this.http.delete<Mensagem>(`${this.API}/${id}`, { params: { userExclusao: nomeUser } });
  }
}

