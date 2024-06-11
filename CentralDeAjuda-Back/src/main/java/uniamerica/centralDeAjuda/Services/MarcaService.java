package uniamerica.centralDeAjuda.Services;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uniamerica.centralDeAjuda.DTO.MarcaDTO;
import uniamerica.centralDeAjuda.DTO.MensagemDTO;
import uniamerica.centralDeAjuda.Entity.Auditoria;
import uniamerica.centralDeAjuda.Entity.Marca;
import uniamerica.centralDeAjuda.Entity.Modelo;
import uniamerica.centralDeAjuda.Repository.AuditoriaRepository;
import uniamerica.centralDeAjuda.Repository.MarcaRepository;
import uniamerica.centralDeAjuda.Repository.ModeloRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private AuditoriaRepository auditoriaRepository;
    public MarcaDTO findMarcaById(Long id) {
        Marca marca = marcaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Marca não encontrada!"));
        return marcaToDTO(marca);
    }

    public List<MarcaDTO> listar() {
        return marcaRepository.findMarcaByAtivo().stream().map(this::marcaToDTO).toList();
    }

    public MensagemDTO cadastrarMarca(MarcaDTO marcaDTO, String userCreacao) throws Exception{
        Marca marca = toMarca(marcaDTO);

        Assert.notNull(marca.getNome(),"Nome inválido!");

        if (!marcaRepository.findByNomeMarca(marca.getNome()).isEmpty()){
            throw new Exception("Essa marca ja existe!");
        }

        marcaRepository.save(marca);

        Auditoria auditoria = new Auditoria();
        auditoria.setMarca(marca);
        auditoria.setDataHoraCriacao(LocalDateTime.now());
        auditoria.setUserCriacao(userCreacao);
        auditoriaRepository.save(auditoria);

        return new MensagemDTO("Marca cadastrada com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO editarMarca(Long id, MarcaDTO marcaDTO, String userAlteracao) throws Exception{
        Marca marca = toMarca(marcaDTO);

        Assert.notNull(marca.getNome(), "Nome inválido!");

        if (!marcaRepository.findByNomeMarca(marca.getNome()).isEmpty()){
            throw new Exception("Esse marca ja existe!");
        }

        marcaRepository.save(marca);

        Auditoria auditoria = new Auditoria();
        auditoria.setMarca(marca);
        auditoria.setDataHoraAlteracao(LocalDateTime.now());
        auditoria.setUserAlteracao(userAlteracao);
        auditoriaRepository.save(auditoria);

        return new MensagemDTO("Marca atualizada com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO deletar(Long id, String userExclusao) throws Exception{
        Marca marcaBanco = marcaRepository.findById(id)
                .orElseThrow(()->
                        new EntityNotFoundException("Marca com ID "+id+" nao existe!"));

        List<Modelo> marcaModeloAtivo = modeloRepository.findModeloByMarcaAtiva(marcaBanco);

        if (!marcaModeloAtivo.isEmpty()){
            throw new Exception("Não é possível excluir essa marca, pois existem modelos ativos associados a ela.");
        } else {
            desativarMarca(marcaBanco);

            Auditoria auditoria = new Auditoria();
            auditoria.setMarca(marcaBanco);
            auditoria.setDataHoraExclusao(LocalDateTime.now());
            auditoria.setUserExclusao(userExclusao);
            auditoriaRepository.save(auditoria);
        }
        return new MensagemDTO("Marca deletada com sucesso!", HttpStatus.CREATED);
    }

    private void desativarMarca(Marca marca) {
        marca.setAtivo(false);
        marcaRepository.save(marca);
    }

    public MarcaDTO marcaToDTO(Marca marca){
        MarcaDTO marcaDTO = new MarcaDTO();

        marcaDTO.setId(marca.getId());
        marcaDTO.setNome(marca.getNome());

        return marcaDTO;
    }

    public Marca toMarca(MarcaDTO marcaDTO){
        Marca novoMarca = new Marca();

        novoMarca.setId(marcaDTO.getId());
        novoMarca.setNome(marcaDTO.getNome());

        return novoMarca;
    }
}
