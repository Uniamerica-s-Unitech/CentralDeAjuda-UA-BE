import { Aluno } from "./aluno";
import { Notebook } from "./notebook";

export class Ticket {
    id!: number;
    alunoId!: Aluno;
    notebookId!: Notebook;
    dataEntrega!: Date;
    dataDevolucao!: Date;
}
