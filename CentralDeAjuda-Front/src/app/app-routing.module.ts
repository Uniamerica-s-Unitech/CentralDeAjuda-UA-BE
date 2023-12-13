import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from './components/layout/index/index.component';
import { AlunoListaComponent } from './components/aluno/aluno-lista/aluno-lista.component';
import { NotebookPaginaComponent } from './components/notebook/notebook-pagina/notebook-pagina.component';
import { TicketPaginaComponent } from './components/ticket/ticket-pagina/ticket-pagina.component';
import { LoginComponent } from './components/sistema/login/login.component';
import { rotaguardGuard } from './guards/rotaguard.guard';
import { CadastrarListComponent } from './components/sistema/cadastrar/cadastrar-list/cadastrar-list.component';

const routes: Routes = [
  { path: "", redirectTo: "login", pathMatch: 'full'},
  { path: "login", component: LoginComponent },
  { 
    path: "admin", 
    component:IndexComponent,
    canActivate: [rotaguardGuard], 
    data: { roles: ['ADMIN'] }, 
    children:[
      {path:"ticket",component: TicketPaginaComponent},
      {path:"aluno",component:AlunoListaComponent},
      {path:"notebook",component:NotebookPaginaComponent},
      {path: "cadastrar", component:CadastrarListComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
