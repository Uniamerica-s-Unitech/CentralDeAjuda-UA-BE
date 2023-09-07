package uniamerica.centralDeAjuda.Services;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uniamerica.centralDeAjuda.DTO.MarcaDTO;
import uniamerica.centralDeAjuda.Entity.Marca;
import uniamerica.centralDeAjuda.Repository.MarcaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public List<MarcaDTO> listar(){
        List<Marca> marcas = marcaRepository.findAll();
        return marcas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public MarcaDTO findById(Long id){
        Marca marca = marcaRepository.findById(id).orElse(null);
        return convertToDTO(marca);
    }
    public MarcaDTO cadastrar(MarcaDTO marcaDTO){
        Marca marca = new Marca();
        BeanUtils.copyProperties(marcaDTO, marca);
        marca = marcaRepository.save(marca);
        return convertToDTO(marca);
    }
    public MarcaDTO editar(Long id , MarcaDTO marcaDTO){
        if(marcaRepository.existsById(id)){
            Marca marca = new Marca();
            BeanUtils.copyProperties(marcaDTO , marca);
            marca.setId(id);
            marca = marcaRepository. save(marca);
            return convertToDTO(marca);
        }
        return null;
    }
    public void deletar(Long id) {
        marcaRepository.deleteById(id);
    }

    private  MarcaDTO convertToDTO(Marca marca){
        MarcaDTO marcaDTO = new MarcaDTO();
        BeanUtils.copyProperties(marca,marcaDTO);
        return marcaDTO;
    }
}
