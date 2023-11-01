import { Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-notebook-pagina',
  templateUrl: './notebook-pagina.component.html',
  styleUrls: ['./notebook-pagina.component.scss']
})
export class NotebookPaginaComponent {
  active = 1;

  realizarPesquisa() {
    const termoPesquisa = this.termoPesquisa.toLowerCase();
    
    if (!termoPesquisa) {
      // Se o termo de pesquisa estiver vazio, restaurar a lista original
      this.listarNotebooks();
    } else {
      // Caso contrário, aplicar a pesquisa
      this.listaNotebooks = this.listaNotebooks.filter((notebook) => {
        const patrimonio = notebook.patrimonio.toLowerCase();
        const modelo = notebook.modeloId.nome.toLowerCase();
        const marca = notebook.modeloId.marcaId.nome.toLowerCase();
        return (
          patrimonio.includes(termoPesquisa) ||
          modelo.includes(termoPesquisa) ||
          marca.includes(termoPesquisa)
        );
      });
    }
  }
    
}
