import { Injectable, inject } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Marca } from '../models/marca';
import { Mensagem } from '../models/mensagem';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class MarcaService {
  API: string = 'http://localhost:8080/api/marca'
  http = inject(HttpClient);
  loginService = inject(LoginService);  
  nomeUser = this.loginService.getUsername();

  constructor() { }

  listar(): Observable<Marca[]> {
    return this.http.get<Marca[]>(`${this.API}` + "/lista");
  }

  save(marca: Marca): Observable<Mensagem> {
    if (marca.id) {
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Mensagem>(`${this.API}/${marca.id}`, { marca, userAlteracao: this.nomeUser });
    } else {
      // Caso contrário, crie uma nova pessoa
      return this.http.post<Mensagem>(this.API, { marca, userCreacao: this.nomeUser });
    }
  }

  deletar(id: number): Observable<any> {
    return this.http.delete<Mensagem>(`${this.API}/${id}`, { params: { userExclusao: this.nomeUser } });
  }
}
