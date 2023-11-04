import { Component, ViewChild, inject} from '@angular/core';
import { Marca } from 'src/app/models/marca';
import { Modelo } from 'src/app/models/modelo';
import { MarcaService } from 'src/app/services/marca.service';
import { ModeloService } from 'src/app/services/modelo.service';
import { ModeloListaComponent } from '../modelo/modelo-lista/modelo-lista.component';
import { MarcaListaComponent } from '../marca/marca-lista/marca-lista.component';

@Component({
  selector: 'app-notebook-pagina',
  templateUrl: './notebook-pagina.component.html',
  styleUrls: ['./notebook-pagina.component.scss']
})
export class NotebookPaginaComponent {
  active = 1;
}