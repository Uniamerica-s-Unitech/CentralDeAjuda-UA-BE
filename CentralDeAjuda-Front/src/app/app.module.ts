import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AlunoListaComponent } from './components/aluno/aluno-lista/aluno-lista.component';
import { AlunoDetalhesComponent } from './components/aluno/aluno-detalhes/aluno-detalhes.component';
import { NotebookDetalhesComponent } from './components/notebook/notebook-detalhes/notebook-detalhes.component';
import { NotebookListarComponent } from './components/notebook/notebook-listar/notebook-listar.component';
import { TicketListaComponent } from './components/ticket/ticket-lista/ticket-lista.component';
import { TicketDetalhesComponent } from './components/ticket/ticket-detalhes/ticket-detalhes.component';
import { MarcaDetalhesComponent } from './components/notebook/marca/marca-detalhes/marca-detalhes.component';
import { MarcaListaComponent } from './components/notebook/marca/marca-lista/marca-lista.component';
import { ModeloListaComponent } from './components/notebook/modelo/modelo-lista/modelo-lista.component';
import { ModeloDetalhesComponent } from './components/notebook/modelo/modelo-detalhes/modelo-detalhes.component';
import { TicketHistoricoComponent } from './components/ticket/ticket-historico/ticket-historico.component';
import { IndexComponent } from './components/layout/index/index.component';
import { FooterComponent } from './components/layout/footer/footer.component';
import { HeaderComponent } from './components/layout/header/header.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    AlunoListaComponent,
    AlunoDetalhesComponent,
    NotebookDetalhesComponent,
    NotebookListarComponent,
    TicketListaComponent,
    TicketDetalhesComponent,
    MarcaDetalhesComponent,
    MarcaListaComponent,
    ModeloListaComponent,
    ModeloDetalhesComponent,
    TicketHistoricoComponent,
    IndexComponent,
    FooterComponent,
    HeaderComponent
    ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
