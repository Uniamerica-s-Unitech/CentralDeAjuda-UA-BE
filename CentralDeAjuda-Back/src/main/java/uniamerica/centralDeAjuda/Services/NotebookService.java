package uniamerica.centralDeAjuda.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uniamerica.centralDeAjuda.DTO.*;
import uniamerica.centralDeAjuda.Entity.Auditoria;
import uniamerica.centralDeAjuda.Entity.Modelo;
import uniamerica.centralDeAjuda.Entity.Notebook;
import uniamerica.centralDeAjuda.Entity.Ticket;
import uniamerica.centralDeAjuda.Repository.AuditoriaRepository;
import uniamerica.centralDeAjuda.Repository.ModeloRepository;
import uniamerica.centralDeAjuda.Repository.NotebookRepository;
import uniamerica.centralDeAjuda.Repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotebookService {
    @Autowired
    private NotebookRepository notebookRepository;
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private AuditoriaRepository auditoriaRepository;
    @Autowired
    private TicketRepository ticketRepository;

    public NotebookDTO findNotebookById(Long id) {
        Notebook notebook = notebookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notebook não encontrado!"));
        return notebookToDTO(notebook);
    }

    public List<NotebookDTO> listar() {
        return notebookRepository.findNotebookByAtivo().stream().map(this::notebookToDTO).toList();
    }

    public List<NotebookDTO> listarSemVinculo()  {
        return notebookRepository.findNotebookByAtivoSemVinculo().stream().map(this::notebookToDTO).toList();
    }

    public MensagemDTO cadastrarNotebook(NotebookDTO notebookDTO, String userCreacao) throws Exception{
        Notebook notebook = toNotebook(notebookDTO);

        Assert.notNull(notebook.getPatrimonio(),"Patrimonio inválido!");
        if (!notebookRepository.findByIdPatrimonio(notebook.getPatrimonio()).isEmpty()){
            throw new Exception("Esse Patrimonio ja existe!");
        }
        Assert.notNull(notebook.getModeloId(),"Modelo inválido!");

        notebookRepository.save(notebook);

        Auditoria auditoria = new Auditoria();
        auditoria.setNotebook(notebook);
        auditoria.setDataHoraCriacao(LocalDateTime.now());
        auditoria.setUserCriacao(userCreacao);
        auditoriaRepository.save(auditoria);

        return new MensagemDTO("Notebook cadastrado com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO editarNotebook(Long id, NotebookDTO notebookDTO, String userAlteracao) throws Exception{
        Notebook notebook = toNotebook(notebookDTO);

        Assert.notNull(notebook.getPatrimonio(),"Patrimonio inválido!");
        if (!notebookRepository.findByIdPatrimonioEditar(notebook.getPatrimonio(),id).isEmpty()){
            throw new Exception("Esse Patrimonio ja existe!");
        }
        Assert.notNull(notebook.getModeloId(),"Modelo inválido!");

        notebookRepository.save(notebook);

        Auditoria auditoria = new Auditoria();
        auditoria.setNotebook(notebook);
        auditoria.setDataHoraAlteracao(LocalDateTime.now());
        auditoria.setUserAlteracao(userAlteracao);
        auditoriaRepository.save(auditoria);

        return new MensagemDTO("Notebook atualizado com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO deletar(Long id, String userExclusao) throws Exception{
        Notebook notebookBanco = notebookRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Notebook com ID "+id+" nao existe!"));

        List<Ticket> notebookTicketsAtivos = ticketRepository.findTicketsAbertosPorNotebook(notebookBanco);


        if (!notebookTicketsAtivos.isEmpty()){
            throw new Exception("Não é possível excluir esse notebook, pois existem ticket ativos associados a ele.");
        } else {
            desativarNotebook(notebookBanco);

            Auditoria auditoria = new Auditoria();
            auditoria.setNotebook(notebookBanco);
            auditoria.setDataHoraExclusao(LocalDateTime.now());
            auditoria.setUserExclusao(userExclusao);
            auditoriaRepository.save(auditoria);
        }
        return new MensagemDTO("Notebook deletado com sucesso!", HttpStatus.CREATED);
    }

    private void desativarNotebook(Notebook notebook) {
        notebook.setAtivo(false);
        notebookRepository.save(notebook);
    }

    public NotebookDTO notebookToDTO(Notebook notebook){
        NotebookDTO notebookDTO = new NotebookDTO();

        notebookDTO.setId(notebook.getId());
        notebookDTO.setPatrimonio(notebook.getPatrimonio());

        ModeloDTO modeloDTO = new ModeloDTO();

        modeloDTO.setId(notebook.getModeloId().getId());
        modeloDTO.setNome(notebook.getModeloId().getNome());

        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setId(notebook.getModeloId().getMarcaId().getId());
        marcaDTO.setNome(notebook.getModeloId().getMarcaId().getNome());

        modeloDTO.setMarcaId(marcaDTO);

        modeloDTO.setMarcaId(modeloDTO.getMarcaId());

        notebookDTO.setModeloId(modeloDTO);
        return notebookDTO;
    }

    public Notebook toNotebook(NotebookDTO notebookDTO){
        Notebook novoNotebook = new Notebook();

        novoNotebook.setId(notebookDTO.getId());
        novoNotebook.setPatrimonio(notebookDTO.getPatrimonio());

        Modelo modelo = new Modelo();
        modelo.setId(notebookDTO.getModeloId().getId());

        novoNotebook.setModeloId(modelo);

        return novoNotebook;
    }
}