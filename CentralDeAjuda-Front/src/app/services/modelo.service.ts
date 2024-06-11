import { Injectable, inject } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Modelo } from '../models/modelo';
import { Mensagem } from '../models/mensagem';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class ModeloService {
  API: string = 'http://localhost:8080/api/modelo'
  http = inject(HttpClient);
  loginService = inject(LoginService);
  nomeUser = this.loginService.getUsername();

  constructor() { }

  listar(): Observable<Modelo[]> {
    return this.http.get<Modelo[]>(`${this.API}` + "/lista");
  }

  save(modelo: Modelo): Observable<Mensagem> {
    if (modelo.id) {
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Mensagem>(`${this.API}/${modelo.id}`, { modelo, userAlteracao: this.nomeUser });
    } else {
      // Caso contrário, crie uma nova pessoa
      return this.http.post<Mensagem>(`${this.API}`, { modelo, userAlteracao: this.nomeUser });
    }
  }

  deletar(id: number): Observable<any> {
    return this.http.delete<Mensagem>(`${this.API}/${id}`, { params: { userExclusao: this.nomeUser } });
  }
}
