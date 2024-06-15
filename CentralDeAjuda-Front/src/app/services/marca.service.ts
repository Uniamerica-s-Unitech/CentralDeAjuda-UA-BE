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
  API: string = 'https://172.21.247.40/api/marca'
  http = inject(HttpClient);
  loginService = inject(LoginService);  
  // nomeUser = this.loginService.getUsername();

  constructor() { }

  listar(): Observable<Marca[]> {
    return this.http.get<Marca[]>(`${this.API}` + "/lista");
  }

  save(marca: Marca): Observable<Mensagem> {
    const nomeUser = this.loginService.getUsername();
    if (marca.id) {
      // Se a pessoa já tem um ID, atualize-a
      return this.http.put<Mensagem>(`${this.API}/${marca.id}`, { marca, userAlteracao: nomeUser });
    } else {
      // Caso contrário, crie uma nova pessoa
      return this.http.post<Mensagem>(this.API, { marca, userCreacao: nomeUser });
    }
  }

  deletar(id: number): Observable<any> {
    const nomeUser = this.loginService.getUsername();
    return this.http.delete<Mensagem>(`${this.API}/${id}`, { params: { userExclusao: nomeUser } });
  }
}
