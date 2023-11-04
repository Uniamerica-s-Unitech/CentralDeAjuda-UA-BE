import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from './components/layout/index/index.component';
import { AlunoListaComponent } from './components/aluno/aluno-lista/aluno-lista.component';
import { NotebookPaginaComponent } from './components/notebook/notebook-pagina/notebook-pagina.component';
import { TicketPaginaComponent } from './components/ticket/ticket-pagina/ticket-pagina.component';

const routes: Routes = [
  {path: "",component:IndexComponent,children:[
    {path:"ticket",component: TicketPaginaComponent},
    {path:"aluno",component:AlunoListaComponent},
    {path:"notebook",component:NotebookPaginaComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
