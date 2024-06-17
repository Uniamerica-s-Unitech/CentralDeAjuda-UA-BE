import { Aluno } from "./aluno";
import { Marca } from "./marca";
import { Modelo } from "./modelo";
import { Notebook } from "./notebook";
import { Ticket } from "./ticket";

export class Auditoria {
    id!: number;
    aluno!: Aluno;
    notebook!: Notebook;
    marca!: Marca;
    modelo!: Modelo;
    ticket!: Ticket;
    userCriacao!: string;
    userAlteracao!: string;
    userExclusao!: string;
    dataHoraCriacao!: Date;
    dataHoraAlteracao!: Date;
    dataHoraExclusao!: Date;
}