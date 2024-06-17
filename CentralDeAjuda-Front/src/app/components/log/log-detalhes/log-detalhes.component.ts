import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Auditoria } from 'src/app/models/auditoria';
import { Mensagem } from 'src/app/models/mensagem';

@Component({
  selector: 'app-log-detalhes',
  templateUrl: './log-detalhes.component.html',
  styleUrls: ['./log-detalhes.component.scss']
})
export class LogDetalhesComponent {
  @Input() audit : Auditoria = new Auditoria();
}