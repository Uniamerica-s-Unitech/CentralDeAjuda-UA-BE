package uniamerica.centralDeAjuda.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uniamerica.centralDeAjuda.DTO.AlunoDTO;
import uniamerica.centralDeAjuda.Entity.Aluno;
import uniamerica.centralDeAjuda.Entity.Ticket;
import uniamerica.centralDeAjuda.Repository.AlunoRepository;
import uniamerica.centralDeAjuda.Repository.TicketRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TicketRepository ticketRepository;

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id).orElse(null);
    }

    public List<AlunoDTO> listar() {
        List<Aluno> alunos = alunoRepository.findAll();
        return alunos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AlunoDTO cadastrar(AlunoDTO alunoDTO) {
        Aluno aluno = new Aluno();
        BeanUtils.copyProperties(alunoDTO, aluno);

        Assert.notNull(aluno.getNome(),"Nome invalido");
        if (!alunoRepository.findByNome(aluno.getNome()).isEmpty()){
            throw new IllegalArgumentException("Esse nome ja existe");
        }

        Assert.notNull(aluno.getRa(),"RA invalido");
        if (!aluno.getRa().matches("\\d{6}")) {
            throw new IllegalArgumentException("Formato do RA inválido. Deve conter 6 dígitos numéricos.");
        }

        if (!alunoRepository.findByRA(aluno.getRa()).isEmpty()){
            throw new IllegalArgumentException("Esse RA ja existe");
        }

        aluno = alunoRepository.save(aluno);
        return convertToDTO(aluno);
    }

    public AlunoDTO editar(Long id, AlunoDTO alunoDTO) {
        if (alunoRepository.existsById(id)) {
            Aluno aluno = alunoRepository.findById(id).orElse(null);
            if (aluno != null) {
                BeanUtils.copyProperties(alunoDTO, aluno,"id");


                Assert.notNull(aluno.getNome(), "Nome invalido");
                if (!alunoRepository.findByNomePut(aluno.getNome(), id).isEmpty()) {
                    throw new IllegalArgumentException("Esse nome ja existe");
                }

                Assert.notNull(aluno.getRa(), "RA invalido");
                if (!aluno.getRa().matches("\\d{6}")) {
                    throw new IllegalArgumentException("Formato do RA inválido. Deve conter 6 dígitos numéricos.");
                }

                if (!alunoRepository.findByRaPut(aluno.getRa(),id).isEmpty()){
                    throw new IllegalArgumentException("Esse RA ja existe");
                }

                aluno = alunoRepository.save(aluno);
                return convertToDTO(aluno);
            }
        }else {
            throw new IllegalArgumentException("Aluno não encontrado com o ID fornecido: " + id);
        }
        return null;
    }

    public void deletar(Aluno aluno) {
        Aluno alunoBanco = alunoRepository.findById(aluno.getId()).orElse(null);
        if (alunoBanco != null) {

            List<Ticket> ticketsAtivos = ticketRepository.findByAluno(alunoBanco);

            if (ticketsAtivos.isEmpty()) {
                alunoRepository.delete(alunoBanco);
            } else {

                if (ticketsAtivos.stream().allMatch(ticket -> ticket.getDataDevolucao() != null)) {
                    alunoRepository.delete(alunoBanco);
                } else {
                    throw new IllegalArgumentException("Não é possível excluir o aluno, pois ele está associado a tickets ativos.");
                }
            }
        } else {
            throw new IllegalArgumentException("Aluno não encontrado com o ID fornecido: " + aluno.getId());
        }
    }

    private AlunoDTO convertToDTO(Aluno aluno) {
        AlunoDTO alunoDTO = new AlunoDTO();
        BeanUtils.copyProperties(aluno, alunoDTO);
        return alunoDTO;
    }
}
