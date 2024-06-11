import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AlunoListaComponent } from './components/aluno/aluno-lista/aluno-lista.component';
import { AlunoDetalhesComponent } from './components/aluno/aluno-detalhes/aluno-detalhes.component';
import { NotebookDetalhesComponent } from './components/notebook/notebook-detalhes/notebook-detalhes.component';
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
import { HttpClientModule } from '@angular/common/http';
import { NotebookPaginaComponent } from './components/notebook/notebook-pagina/notebook-pagina.component';
import { HeaderTopComponent } from './components/layout/header-top/header-top.component';
import { TicketPaginaComponent } from './components/ticket/ticket-pagina/ticket-pagina.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { NotebookListaComponent } from './components/notebook/notebook-lista/notebook-lista.component';
import { RouterModule } from '@angular/router';
import { OAuthModule } from 'angular-oauth2-oidc';
import { CadastrarDetalhesComponent } from './components/sistema/cadastrar/cadastrar-detalhes/cadastrar-detalhes.component';
import { CadastrarListComponent } from './components/sistema/cadastrar/cadastrar-list/cadastrar-list.component';


@NgModule({
  declarations: [
    AppComponent,
    AlunoListaComponent,
    AlunoDetalhesComponent,
    NotebookDetalhesComponent,
    NotebookListaComponent,
    TicketListaComponent,
    TicketDetalhesComponent,
    MarcaDetalhesComponent,
    MarcaListaComponent,
    ModeloListaComponent,
    ModeloDetalhesComponent,
    TicketHistoricoComponent,
    IndexComponent,
    FooterComponent,
    HeaderComponent,
    NotebookPaginaComponent,
    HeaderTopComponent,
    TicketPaginaComponent,
    CadastrarDetalhesComponent,
    CadastrarListComponent
    ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    RouterModule,
    OAuthModule.forRoot({
      resourceServer: {
          allowedUrls: [],
          sendAccessToken: true
      }
  })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
