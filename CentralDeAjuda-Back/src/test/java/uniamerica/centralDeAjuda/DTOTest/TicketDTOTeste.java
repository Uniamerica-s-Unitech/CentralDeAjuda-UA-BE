//package uniamerica.centralDeAjuda.DTOTest;
//
//import org.junit.Test;
//import uniamerica.centralDeAjuda.DTO.*;
//
//import java.time.LocalDateTime;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//public class TicketDTOTeste {
//    @Test
//    public void testTicketDTOValidation() {
//        // Crie uma instância de TicketDTO para testar
//        TicketDTO ticketDTO = new TicketDTO();
//        ticketDTO.setId(1L);
//        ticketDTO.setAtivo(true);
//
//        // Crie uma instância de AlunoDTO e defina seus atributos
//        AlunoDTO alunoDTO = new AlunoDTO();
//        alunoDTO.setId(1L);
//        alunoDTO.setAtivo(true);
//        alunoDTO.setNome("Aluno1");
//        alunoDTO.setRa("123456");
//
//        // Crie uma instância de ModeloDTO e defina seus atributos
//        ModeloDTO modeloDTO = new ModeloDTO();
//        modeloDTO.setId(1L);
//        modeloDTO.setAtivo(true);
//        modeloDTO.setNome("Modelo1");
//
//        // Crie uma instância de MarcaDTO e defina seus atributos
//        MarcaDTO marcaDTO = new MarcaDTO();
//        marcaDTO.setId(1L);
//        marcaDTO.setAtivo(true);
//        marcaDTO.setNome("Marca1");
//
//        // Defina o atributo alunoId do TicketDTO
//        ticketDTO.setAlunoId(alunoDTO);
//
//        // Defina o atributo notebookId do TicketDTO
//        NotebookDTO notebookDTO = new NotebookDTO();
//        notebookDTO.setId(1L);
//        notebookDTO.setAtivo(true);
//        notebookDTO.setPatrimonio("Patrimonio1");
//        notebookDTO.setModeloId(modeloDTO);
//        modeloDTO.setMarcaId(marcaDTO);
//        ticketDTO.setNotebookId(notebookDTO);
//
//        // Defina os atributos de data
//        ticketDTO.setDataEntrega(LocalDateTime.now());
//
//        // Verifique as anotações de validação individualmente
//        assertThat(ticketDTO.getId()).isNotNull();
//        assertThat(ticketDTO.getAtivo()).isTrue();
//        assertThat(ticketDTO.getAlunoId()).isNotNull();
//        assertThat(ticketDTO.getNotebookId()).isNotNull();
//        assertThat(ticketDTO.getDataEntrega()).isNotNull();
//        assertThat(ticketDTO.getDataDevolucao()).isNull(); // Data de devolução pode ser nula
//    }
//}
