package uniamerica.centralDeAjuda.Services;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uniamerica.centralDeAjuda.DTO.ModeloDTO;
import uniamerica.centralDeAjuda.Entity.Modelo;
import uniamerica.centralDeAjuda.Repository.ModeloRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;

    public List<ModeloDTO> listar(){
        List<Modelo> modelos = modeloRepository.findAll();
        return modelos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ModeloDTO findById(Long id){
        Modelo modelo = modeloRepository.findById(id).orElse(null);
        return convertToDTO(modelo);
    }
    public ModeloDTO cadastrar(ModeloDTO modeloDTO){
        Modelo modelo = new Modelo();
        BeanUtils.copyProperties(modeloDTO, modelo);
        modelo = modeloRepository.save(modelo);
        return convertToDTO(modelo);
    }

    public ModeloDTO editar(Long id , ModeloDTO modeloDTO){
        if(modeloRepository.existsById(id)){
            Modelo modelo = new Modelo();
            BeanUtils.copyProperties(modeloDTO , modelo);
            modelo.setId(id);
            modelo = modeloRepository. save(modelo);
            return convertToDTO(modelo);
        }
        return null;
    }

    public void deletar(Long id) {
        modeloRepository.deleteById(id);
    }


    private ModeloDTO convertToDTO(Modelo modelo){
        ModeloDTO modeloDTO = new ModeloDTO();
        BeanUtils.copyProperties(modelo,modeloDTO);
        return modeloDTO;
    }

}
