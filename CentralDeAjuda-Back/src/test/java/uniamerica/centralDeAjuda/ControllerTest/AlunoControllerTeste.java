//package uniamerica.centralDeAjuda.ControllerTest;
//
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import uniamerica.centralDeAjuda.Controller.AlunoController;
//import uniamerica.centralDeAjuda.DTO.AlunoDTO;
//import uniamerica.centralDeAjuda.Services.AlunoService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class AlunoControllerTeste {
//
//    @InjectMocks
//    private AlunoController alunoController;
//
//    @Mock
//    private AlunoService alunoService;
//
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void buscarByIdTeste(){
//        Long alunoId = 1L;
//        AlunoDTO alunoDTO = new AlunoDTO();
//
//        when(alunoService.findAlunoById(alunoId)).thenReturn(alunoDTO);
//
//        ResponseEntity<AlunoDTO> response = alunoController.buscarPorId(alunoId);
//
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals(alunoDTO, response.getBody());
//    }
//
//    @Test
//    void buscarByIdTesteNaoExiste(){
//        Long alunoId = 1L;
//
//        when(alunoService.findAlunoById(alunoId)).thenReturn(null);
//
//        ResponseEntity<AlunoDTO> response = alunoController.buscarPorId(alunoId);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void listarAlunosTeste() {
//        List<AlunoDTO> alunos = new ArrayList<>();
//
//        when(alunoService.listar()).thenReturn(alunos);
//
//        List<AlunoDTO> alunoDTOList = alunoController.listar();
//
//        assertEquals(alunos.size(),alunoDTOList.size());
//    }
//
//    @Test
//    void cadastrarAlunoTeste() {
//        AlunoDTO alunoDTO = new AlunoDTO();
//        alunoDTO.setId(1L);
//        alunoDTO.setAtivo(true);
//        alunoDTO.setNome("Homam");
//        alunoDTO.setRa("123456");
//
//        when(alunoService.cadastrarAluno(alunoDTO)).thenReturn("Aluno cadastrado com sucesso!");
//
//        ResponseEntity<String> response = alunoController.cadastrarAluno(alunoDTO);
//
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals("Aluno cadastrado com sucesso!",response.getBody());
//    }
//
//    @Test
//    void cadastrarAlunoTesteNomeNull() {
//        AlunoDTO alunoDTO = new AlunoDTO();
//        alunoDTO.setId(1L);
//        alunoDTO.setRa("123456");
//
//        when(alunoService.cadastrarAluno(alunoDTO)).thenThrow(EntityNotFoundException.class);
//
//        ResponseEntity<String> response = alunoController.cadastrarAluno(alunoDTO);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void cadastrarAlunoTesteRANull() {
//        AlunoDTO alunoDTO = new AlunoDTO();
//        alunoDTO.setId(1L);
//        alunoDTO.setNome("Homam");
//
//        when(alunoService.cadastrarAluno(alunoDTO)).thenThrow(EntityNotFoundException.class);
//
//        ResponseEntity<String> response = alunoController.cadastrarAluno(alunoDTO);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void editarAlunoTeste() {
//        Long alunoId = 1L;
//        AlunoDTO alunoDTO = new AlunoDTO();
//        alunoDTO.setId(1L);
//        alunoDTO.setAtivo(true);
//        alunoDTO.setNome("Hisham");
//        alunoDTO.setRa("123456");
//
//        when(alunoService.editarAluno(alunoId,alunoDTO)).thenReturn("Aluno atualizado com sucesso!");
//
//        ResponseEntity<String> response = alunoController.editarAluno(alunoId,alunoDTO);
//
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals("Aluno atualizado com sucesso!",response.getBody());
//    }
//
//    @Test
//    void editarAlunoTesteNaoExiste() {
//        Long alunoId = 1L;
//        AlunoDTO alunoDTO = new AlunoDTO();
//
//        when(alunoService.editarAluno(alunoId,alunoDTO)).thenThrow(EntityNotFoundException.class);
//
//        ResponseEntity<String> response = alunoController.editarAluno(alunoId,alunoDTO);
//
//        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    void deletarAlunoTeste() {
//        Long alunoId = 1L;
//
//        ResponseEntity<String> response = alunoController.deletar(alunoId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Aluno deletado com sucesso!", response.getBody());
//    }
//}