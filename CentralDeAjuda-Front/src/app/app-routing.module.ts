import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from './components/layout/index/index.component';
import { AlunoListaComponent } from './components/aluno/aluno-lista/aluno-lista.component';
import { NotebookPaginaComponent } from './components/notebook/notebook-pagina/notebook-pagina.component';
import { TicketPaginaComponent } from './components/ticket/ticket-pagina/ticket-pagina.component';
import { CadastrarListComponent } from './components/sistema/cadastrar/cadastrar-list/cadastrar-list.component';
import { rotaguardGuard } from './guards/rotaguard.guard';
import { LogComponent } from './components/log/log.component';

const routes: Routes = [
  // {path: '', component: AlunoListaComponent},
  // {path: '**', redirectTo: '', pathMatch: 'full'}
  { path: "", redirectTo: "login", pathMatch: 'full'},
  // { path: "login", component: LoginComponent },
  { 
    path: "", 
    component:IndexComponent,
    children:[
      {path:"ticket",component: TicketPaginaComponent ,canActivate: [rotaguardGuard],data: {requiredRoles: ['admin']},},
      {path:"aluno",component:AlunoListaComponent,canActivate: [rotaguardGuard],data: {requiredRoles: ['admin']},},
      {path:"notebook",component:NotebookPaginaComponent,canActivate: [rotaguardGuard],data: {requiredRoles: ['admin']},},
      {path:"cadastrar", component:CadastrarListComponent,canActivate: [rotaguardGuard],data: {requiredRoles: ['admin']},},
      {path:"log", component:LogComponent,canActivate: [rotaguardGuard],data: {requiredRoles: ['admin']},}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],//, {useHash: true}
  exports: [RouterModule]
})
export class AppRoutingModule { }
