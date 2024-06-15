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
  API: string = 'https://172.21.247.40/api/modelo'
  http = inject(HttpClient);
  loginService = inject(LoginService);
  // nomeUser = this.loginService.getUsername();

  constructor() { }

  listar(): Observable<Modelo[]> {
    return this.http.get<Modelo[]>(`${this.API}` + "/lista");
  }

  save(modelo: Modelo): Observable<Mensagem> {
    const nomeUser = this.loginService.getUsername();
    if (modelo.id) {
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Mensagem>(`${this.API}/${modelo.id}`, { modelo, userAlteracao: nomeUser });
    } else {
      // Caso contrário, crie uma nova pessoa
      return this.http.post<Mensagem>(`${this.API}`, { modelo, userAlteracao: nomeUser });
    }
  }

  deletar(id: number): Observable<any> {
    const nomeUser = this.loginService.getUsername();
    return this.http.delete<Mensagem>(`${this.API}/${id}`, { params: { userExclusao: nomeUser } });
  }
}
