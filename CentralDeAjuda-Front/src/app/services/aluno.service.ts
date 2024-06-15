import { Injectable, Input, inject } from '@angular/core';
import { Aluno } from '../models/aluno';
import { Observable, audit } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Mensagem } from '../models/mensagem';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})

export class AlunoService {
  API: string = 'https://172.21.247.40/api/aluno'
  http = inject(HttpClient);
  loginService = inject(LoginService);  
  // nomeUser = this.loginService.getUsername();
  
  constructor() { }

  listarAlunosSemVinculos(): Observable<Aluno[]> {
    return this.http.get<Aluno[]>(`${this.API}` + "/listaSemVinculo");
  }

  listar(): Observable<Aluno[]> {
    return this.http.get<Aluno[]>(`${this.API}` + "/lista");
  }

  save(aluno: Aluno): Observable<Mensagem> {
    const nomeUser = this.loginService.getUsername();
    if (aluno.id) {
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Mensagem>(`${this.API}/${aluno.id}`, { aluno, userAlteracao: nomeUser });
    } else {
      // Caso contrário, crie uma nova pessoa
      return this.http.post<Mensagem>(this.API, { aluno, userCreacao: nomeUser });
    }
    // if (aluno.id) {
    //   // Se a pessoa já tem um ID, atualize-a
    //   const nome = this.loginService.getUsername();
    //   this.auditoria.userAlteracao = nome;
    //   console.log(nome);
    //   return this.http.put<Mensagem>(this.API + "/" + `${aluno.id}`,aluno);
    // } else {
    //   // Caso contrário, crie uma nova pessoa
    //   return this.http.post<Mensagem>(this.API, aluno);
    // }
  }

  deletar(id: number): Observable<Mensagem> {
    const nomeUser = this.loginService.getUsername();
    return this.http.delete<Mensagem>(`${this.API}/${id}`, { params: { userExclusao: nomeUser } });
  }

}
