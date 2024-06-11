package uniamerica.centralDeAjuda.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uniamerica.centralDeAjuda.Entity.Auditoria;
import uniamerica.centralDeAjuda.Repository.AuditoriaRepository;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private AuditoriaRepository auditoriaRepository;

    public List<Auditoria> listar() {
        return auditoriaRepository.findAll();
    }
}