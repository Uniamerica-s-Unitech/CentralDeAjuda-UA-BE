package uniamerica.centralDeAjuda.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import uniamerica.centralDeAjuda.DTO.LaptopDTO;
import uniamerica.centralDeAjuda.Entity.Laptop;
import uniamerica.centralDeAjuda.Repository.LaptopRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LaptopService {
    @Autowired
    private LaptopRepository laptopRepository;

    public List<LaptopDTO> listar() {
        List<Laptop> laptops = laptopRepository.findAll();
        return laptops.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LaptopDTO findById(Long id) {
        Laptop laptop = laptopRepository.findById(id).orElse(null);
        return convertToDTO(laptop);
    }

    public LaptopDTO cadastrar(LaptopDTO laptopDTO) {
        Laptop laptop = new Laptop();
        BeanUtils.copyProperties(laptopDTO, laptop);
        Assert.isTrue(laptop.getPatrimonio() != null, "Id patrimonio invalido");
        Assert.isTrue(this.laptopRepository.findByIdPatrimonio(laptop.getPatrimonio()).isEmpty(),"Já existe esse id");
        laptop = laptopRepository.save(laptop);
        return convertToDTO(laptop);
    }

    public LaptopDTO editar(Long id, LaptopDTO laptopDTO) {
        if (laptopRepository.existsById(id)) {
            Laptop laptop = new Laptop();
            BeanUtils.copyProperties(laptopDTO, laptop);
            laptop.setId(id);
            laptop = laptopRepository.save(laptop);
            return convertToDTO(laptop);
        }
        return null;
    }

    public void deletar(Long id) {
        laptopRepository.deleteById(id);
    }

    private LaptopDTO convertToDTO(Laptop laptop) {
        LaptopDTO laptopDTO = new LaptopDTO();
        BeanUtils.copyProperties(laptop, laptopDTO);
        return laptopDTO;
    }
}
