import { Injectable, inject } from '@angular/core';
import { Aluno } from '../models/aluno';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Mensagem } from '../models/mensagem';

@Injectable({
  providedIn: 'root'
})
export class AlunoService {
  API: string = 'https://172.21.247.48:8080/aluno'
  http = inject(HttpClient);

  constructor() { }

  listarAlunosSemVinculos(): Observable<Aluno[]> {
    return this.http.get<Aluno[]>(`${this.API}` + "/listaSemVinculo");
  }

  listar(): Observable<Aluno[]> {
    return this.http.get<Aluno[]>(`${this.API}` + "/lista");
  }

  save(aluno: Aluno): Observable<Mensagem> {
    if (aluno.id) {
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Mensagem>(this.API+"/"+`${aluno.id}`, aluno);
    } else {
      // Caso contrário, crie uma nova pessoa
      return this.http.post<Mensagem>(this.API, aluno);
    }
  }

  deletar(id: number): Observable<Mensagem> {
    return this.http.delete<Mensagem>(this.API + "/" + `${id}`);
  }

}
