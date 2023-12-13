//package uniamerica.centralDeAjuda.DTOTest;
//
//import org.junit.Test;
//import uniamerica.centralDeAjuda.DTO.AlunoDTO;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//public class AlunoDTOTeste {
//
//    @Test
//    public void testAlunoDTOValidation() {
//        // Crie uma instância de AlunoDTO para testar
//        AlunoDTO alunoDTO = new AlunoDTO();
//        alunoDTO.setId(1L);
//        alunoDTO.setAtivo(true);
//        alunoDTO.setNome("João");
//        alunoDTO.setRa("123456");
//
//        // Verifique as anotações de validação individualmente
//        assertThat(alunoDTO.getId()).isNotNull();
//        assertThat(alunoDTO.getAtivo()).isTrue();
//        assertThat(alunoDTO.getNome()).isNotBlank().hasSizeBetween(2, 100);
//        assertThat(alunoDTO.getRa()).isNotBlank().hasSize(6);
//    }
//}
