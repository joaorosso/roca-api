package com.roca.api.service;

import com.roca.api.dto.DespesaDto;
import com.roca.api.model.Despesa;
import com.roca.api.model.Roca;
import com.roca.api.repository.DespesaRepository;
import com.roca.api.repository.RocaRepository;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.InputStream;
import java.sql.Date;
import java.util.*;

@Service
public class DespesaService {

    @Autowired
    private RocaRepository rocaRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    public byte[] despesasReport(UUID rocaId) throws Exception {
        Roca roca = rocaRepository.findById(rocaId).orElse(null);
        List<Despesa> despesas = despesaRepository.findAllByRocaId(rocaId);
//        List<DespesaDto> despesasReport = new ArrayList<>();
//
//        for (Despesa despesa : despesas) {
//            DespesaDto despesaDto = new DespesaDto();
//            despesaDto.setDescricao(despesa.getDescricao());
//            despesasReport.add(despesaDto);
//        }

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("ROCA_DESCRICAO", roca.getDescricao());
        parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

        InputStream inputStream = this.getClass().getResourceAsStream(
                "/reports/despesas.jasper");

        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters, new JRBeanCollectionDataSource(despesas, false));

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
