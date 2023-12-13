//package uniamerica.centralDeAjuda.ServiceTest;
//
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.boot.test.context.SpringBootTest;
//import uniamerica.centralDeAjuda.DTO.AlunoDTO;
//import uniamerica.centralDeAjuda.Entity.Aluno;
//import uniamerica.centralDeAjuda.Repository.AlunoRepository;
//import uniamerica.centralDeAjuda.Repository.TicketRepository;
//import uniamerica.centralDeAjuda.Services.AlunoService;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class AlunoServiceTeste {
//    @InjectMocks
//    private AlunoService alunoService;
//    @Mock
//    private AlunoRepository alunoRepository;
//    @Mock
//    private TicketRepository ticketRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void findAlunoByIdTeste() {
//        Aluno aluno = new Aluno();
//        aluno.setId(1L);
//
//        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
//
//        AlunoDTO alunoDTO = alunoService.findAlunoById(1L);
//
//        assertNotNull(alunoDTO);
//        assertEquals(1L, alunoDTO.getId());
//    }
//
//    @Test
//    void findAlunoByIdTesteNULLTeste() {
//        when(alunoRepository.findById(2L)).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class, () -> alunoService.findAlunoById(2L));
//    }
//
//    @Test
//    void listarAlunosTeste() {
//        // Crie uma lista de alunos simulada para retornar do repositório
//        List<Aluno> alunos = new ArrayList<>();
//        Aluno aluno1 = new Aluno();
//        aluno1.setId(1L);
//        aluno1.setNome("João");
//        aluno1.setRa("123456");
//        alunos.add(aluno1);
//
//        when(alunoRepository.findAlunoByAtivo()).thenReturn(alunos);
//
//        // Chame o método listar
//        List<AlunoDTO> alunoDTOs = alunoService.listar();
//
//        // Verifique se o resultado não está vazio e possui o mesmo tamanho que a lista simulada
//        assertNotNull(alunoDTOs);
//        assertEquals(alunos.size(), alunoDTOs.size());
//
//        // Verifique o mapeamento do aluno para alunoDTO em pelo menos um elemento da lista
//        AlunoDTO alunoDTO = alunoDTOs.get(0);
//        assertEquals(aluno1.getId(), alunoDTO.getId());
//        assertEquals(aluno1.getNome(), alunoDTO.getNome());
//        assertEquals(aluno1.getRa(), alunoDTO.getRa());
//    }
//
//    @Test
//    void cadastrarAlunoTeste() {
//        // Crie um DTO de Aluno com dados de exemplo
//        AlunoDTO alunoDTO = new AlunoDTO();
//        alunoDTO.setNome("João");
//        alunoDTO.setRa("123456");
//
//        // Chame o método de serviço para cadastrar o aluno
//        String mensagem = alunoService.cadastrarAluno(alunoDTO);
//
//        // Realize asserções para verificar se o aluno foi cadastrado com sucesso
//        assertEquals("Aluno cadastrado com sucesso!", mensagem);
//
//        // Converta o DTO em uma entidade para verificar o mapeamento
//        Aluno alunoCadastrado = alunoService.toAluno(alunoDTO);
//
//        // Verifique se os atributos da entidade foram mapeados corretamente
//        assertEquals(alunoDTO.getNome(), alunoCadastrado.getNome());
//        assertEquals(alunoDTO.getRa(), alunoCadastrado.getRa());
//    }
//
//    @Test
//    void editarAlunoTeste() {
//        Long id = 1L;
//        AlunoDTO alunoDTO = new AlunoDTO();
//        alunoDTO.setId(id);
//        alunoDTO.setNome("João Editado");
//        alunoDTO.setRa("123456");
//
//        Aluno alunoExistente = new Aluno();
//        alunoExistente.setId(id);
//        alunoExistente.setNome("João Original");
//        alunoExistente.setRa("123456");
//
//        when(alunoRepository.existsById(id)).thenReturn(true);
//        when(alunoRepository.findByRA(alunoDTO.getRa())).thenReturn(new ArrayList<>());
//        when(alunoRepository.save(any(Aluno.class))).thenReturn(alunoExistente);
//
//        String mensagem = alunoService.editarAluno(id, alunoDTO);
//
//        assertEquals("Aluno atualizado com sucesso!", mensagem);
//
//        // Verifique o mapeamento do alunoDTO para aluno
//        ArgumentCaptor<Aluno> alunoCaptor = ArgumentCaptor.forClass(Aluno.class);
//        verify(alunoRepository).save(alunoCaptor.capture());
//
//        Aluno alunoEditado = alunoCaptor.getValue();
//        assertEquals(alunoDTO.getId(), alunoEditado.getId());
//        assertEquals(alunoDTO.getNome(), alunoEditado.getNome());
//        assertEquals(alunoDTO.getRa(), alunoEditado.getRa());
//    }
//
//    @Test
//    void editarAlunoNaoExisteTest() {
//        Long alunoId = 1L;
//        AlunoDTO alunoDTO = new AlunoDTO();
//        alunoDTO.setId(alunoId);
//
//        when(alunoRepository.existsById(alunoId)).thenReturn(false);
//
//        assertThrows(IllegalArgumentException.class, () -> alunoService.editarAluno(alunoId, alunoDTO));
//    }
//
//    @Test
//    void deletarAlunoTeste() {
//        Long id = 1L;
//        Aluno aluno = new Aluno();
//        aluno.setId(id);
//        aluno.setNome("João");
//        aluno.setRa("123456");
//
//        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));
//        when(ticketRepository.findTicketsAbertosPorAluno(aluno)).thenReturn(new ArrayList<>());
//
//        // Chame o método deletar
//        assertDoesNotThrow(() -> alunoService.deletar(id));
//
//        // Verifique se o aluno foi desativado (verificando o mapeamento)
//        ArgumentCaptor<Aluno> alunoCaptor = ArgumentCaptor.forClass(Aluno.class);
//        verify(alunoRepository).save(alunoCaptor.capture());
//
//        Aluno alunoDesativado = alunoCaptor.getValue();
//        assertFalse(alunoDesativado.getAtivo());
//    }
//}