package uniamerica.centralDeAjuda.Services;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uniamerica.centralDeAjuda.DTO.MarcaDTO;
import uniamerica.centralDeAjuda.Entity.Marca;
import uniamerica.centralDeAjuda.Entity.Modelo;
import uniamerica.centralDeAjuda.Repository.MarcaRepository;
import uniamerica.centralDeAjuda.Repository.ModeloRepository;

import java.util.List;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private ModeloRepository modeloRepository;

    public MarcaDTO findMarcaById(Long id) {
        Marca marca = marcaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Marca não encontrada!"));
        return marcaToDTO(marca);
    }

    public List<MarcaDTO> listar() {
        return marcaRepository.findMarcaByAtivo().stream().map(this::marcaToDTO).toList();
    }

    public String cadastrarMarca(MarcaDTO marcaDTO) {
        Marca marca = toMarca(marcaDTO);

        Assert.notNull(marca.getNome(),"Nome inválido!");

        if (!marcaRepository.findByNomeMarca(marca.getNome()).isEmpty()){
            throw new IllegalArgumentException("Esse RA ja existe!");
        }

        marcaRepository.save(marca);
        return "Marca cadastrada com sucesso!";
    }

    public String editarMarca(Long id, MarcaDTO marcaDTO) {
        if (marcaRepository.existsById(id)) {
            Marca marca = toMarca(marcaDTO);

            Assert.notNull(marca.getNome(), "Nome inválido!");

            if (!marcaRepository.findByNomeMarca(marca.getNome()).isEmpty()){
                throw new IllegalArgumentException("Esse RA ja existe!");
            }

            marcaRepository.save(marca);
            return "Marca atualizada com sucesso!";

        }else {
            throw new IllegalArgumentException("Marca não encontrado com o ID fornecido: " + id);
        }
    }

    public void deletar(Long id) {
        Marca marcaBanco = marcaRepository.findById(id)
                .orElseThrow(()->
                        new EntityNotFoundException("Marca com ID "+id+" nao existe!"));

        List<Modelo> marcaModeloAtivo = modeloRepository.findModeloByMarcaAtiva(marcaBanco);

        if (!marcaModeloAtivo.isEmpty()){
            throw new IllegalArgumentException("Não é possível excluir essa marca tem modelo ativo.");
        } else {
            desativarMarca(marcaBanco);
        }
    }

    private void desativarMarca(Marca marca) {
        marca.setAtivo(false);
        marcaRepository.save(marca);
    }

    public MarcaDTO marcaToDTO(Marca marca){
        MarcaDTO marcaDTO = new MarcaDTO();

        marcaDTO.setId(marca.getId());
        marcaDTO.setAtivo(marca.getAtivo());
        marcaDTO.setNome(marca.getNome());

        return marcaDTO;
    }

    public Marca toMarca(MarcaDTO marcaDTO){
        Marca novoMarca = new Marca();

        novoMarca.setId(marcaDTO.getId());
        novoMarca.setAtivo(marcaDTO.getAtivo());
        novoMarca.setNome(marcaDTO.getNome());

        return novoMarca;
    }
}
