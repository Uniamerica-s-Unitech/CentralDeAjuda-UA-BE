import { Component, Output, inject } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Auditoria } from 'src/app/models/auditoria';
import { AuditoriaService } from 'src/app/services/auditoria.service';

@Component({
  selector: 'app-log',
  templateUrl: './log.component.html',
  styleUrls: ['./log.component.scss']
})
export class LogComponent {
  listaAuditoriaOriginal: Auditoria[] = [];
  listaAuditoriaFiltrada: Auditoria[] = [];

  auditoriaService = inject(AuditoriaService);
  modalService = inject(NgbModal);

  auditDetalhes!: Auditoria;
  indice!: number;
  filterAcao!: string;
  filterObjeito!: string;

  constructor() {
    this.listarAudits();
  }

  listarAudits() {
    this.auditoriaService.listar().subscribe({
      next: lista => {
        this.listaAuditoriaOriginal = lista;
        this.listaAuditoriaFiltrada = lista;
      }
    });
  }

  detalhes(modal: any, audit: Auditoria, indice: number) {
    this.auditDetalhes = Object.assign({}, audit);
    this.indice = indice;

    this.modalService.open(modal, { size: 'lg' });
  }

  @Output() realizarPesquisaPorAcao(filterAcao: string) {
    this.filterAcao = filterAcao;
    this.aplicarFiltros();
  }

  @Output() realizarPesquisaPorObjeito(filterObjeito: string) {
    this.filterObjeito = filterObjeito;
    this.aplicarFiltros();
  }

  aplicarFiltros() {
    this.listaAuditoriaFiltrada = this.listaAuditoriaOriginal.filter((auditoria: Auditoria) => {
      const alteracao = auditoria.dataHoraAlteracao;
      const criacao = auditoria.dataHoraCriacao;
      const excluicao = auditoria.dataHoraExclusao;
      const finalizacao = auditoria.dataHoraFinalizacao;

      const aluno = auditoria.aluno;
      const notebook = auditoria.notebook;
      const marca = auditoria.marca;
      const modelo = auditoria.modelo;
      const ticket = auditoria.ticket;

      let acaoValida = true;
      let objetoValido = true;

      // Filtrar por ação
      if (this.filterAcao) {
        if (this.filterAcao === 'criar') {
          acaoValida = criacao != null;
        } else if (this.filterAcao === 'alterar') {
          acaoValida = alteracao != null;
        } else if (this.filterAcao === 'excluir') {
          acaoValida = excluicao != null;
        } else if (this.filterAcao === 'finalizacao') {
          acaoValida = finalizacao != null;
        }
      }

      // Filtrar por objeto
      if (this.filterObjeito) {
        if (this.filterObjeito === 'aluno') {
          objetoValido = aluno != null;
        } else if (this.filterObjeito === 'notebook') {
          objetoValido = notebook != null;
        } else if (this.filterObjeito === 'marca') {
          objetoValido = marca != null;
        } else if (this.filterObjeito === 'modelo') {
          objetoValido = modelo != null;
        } else if (this.filterObjeito === 'ticket') {
          objetoValido = ticket != null;
        }
      }

      return acaoValida && objetoValido;
    });
  }

  byId(item1: any, item2: any) {
    if (item1 != null && item2 != null) {
      return item1.id === item2.id;
    } else {
      return item1 === item2;
    }
  }
}