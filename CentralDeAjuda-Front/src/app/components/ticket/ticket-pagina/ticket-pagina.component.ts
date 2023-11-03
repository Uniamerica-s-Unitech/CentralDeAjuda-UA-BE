import { Component, ViewChild, inject} from '@angular/core';
import { Marca } from 'src/app/models/marca';
import { Modelo } from 'src/app/models/modelo';
import { MarcaService } from 'src/app/services/marca.service';
import { ModeloService } from 'src/app/services/modelo.service';
import { TicketListaComponent } from '../ticket-lista/ticket-lista.component';
import { TicketHistoricoComponent } from '../ticket-historico/ticket-historico.component';
import { ModeloListaComponent } from '../../notebook/modelo/modelo-lista/modelo-lista.component';
import { MarcaListaComponent } from '../../notebook/marca/marca-lista/marca-lista.component';

@Component({
  selector: 'app-ticket-pagina',
  templateUrl: './ticket-pagina.component.html',
  styleUrls: ['./ticket-pagina.component.scss']
})
export class TicketPaginaComponent {
  active = 1;
  
  pesquisaTicketAtivo: string = "";
  pesquisaTicketHistorico: string = "";
  pesquisaModelo: string = "";
  pesquisaMarca: string = "";

  filterModelo!: Modelo;
  filterMarca!: Marca;

  @ViewChild('ticketAtivo') ticketAtivo!: TicketListaComponent;
  @ViewChild('ticketHistorico') ticketHistorico!: TicketHistoricoComponent;
  @ViewChild('modelo') modelo!: ModeloListaComponent;
  @ViewChild('marca') marca!: MarcaListaComponent;

  listaModelos : Modelo[] = [];
  listaMarcas : Marca[] = [];

  modeloService = inject(ModeloService);
  marcaService = inject(MarcaService);

  constructor() {
   this.recarregarLista();
  }

  recarregarLista(){
    this.carregarModelos();
    this.carregarMarcas();
  }
  carregarModelos() {
    this.modeloService.listar().subscribe({
      next: lista => {
        this.listaModelos = lista;
      }
    });
  }
  carregarMarcas() {
    this.marcaService.listar().subscribe({
      next: lista => {
        this.listaMarcas = lista;
      }
    });
  }

  filtrarTickets(){
    this.ticketAtivo.realizarPesquisa(this.pesquisaTicketAtivo);
  }

  filtrarTicketPorModelo(){
    this.ticketAtivo.realizarPesquisaPorModelo(this.filterModelo);
  }
  filtrarTicketPorMarca(){
    this.ticketAtivo.realizarPesquisaPorMarca(this.filterMarca);
  }

  byId(item1: any, item2: any){
    if(item1 != null && item2 != null){
      return item1.id === item2.id;
    }else{
      return item1 === item2;
    }
  }
}
