package uniamerica.centralDeAjuda.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniamerica.centralDeAjuda.Entity.Auditoria;
import uniamerica.centralDeAjuda.Services.ReportService;

import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/lista")
    public List<Auditoria> lista(){
        return reportService.listar();
    }

}