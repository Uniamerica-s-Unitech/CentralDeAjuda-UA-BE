import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Auditoria } from '../models/auditoria';

@Injectable({
  providedIn: 'root'
})
export class AuditoriaService {
  API: string = 'https://172.21.247.40/api/auditoria'
  http = inject(HttpClient);

  constructor() { }

  listar(): Observable<Auditoria[]> {
    return this.http.get<Auditoria[]>(`${this.API}` + "/lista");
  }
}
