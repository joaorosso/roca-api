package com.roca.api.service;

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

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

@Service
public class DespesaService {

    @Autowired
    private RocaRepository rocaRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    public byte[] despesasReport(UUID rocaId) throws Exception {
        Roca roca = rocaRepository.findById(rocaId).orElse(null);
        List<Despesa> despesas = despesaRepository.findByRocaIdOrderByDataAsc(rocaId);

        Map<String, Object> parameters = new HashMap<>();

        BigDecimal totalDespesa = roca.getDespesa();

        parameters.put("TOTAL_DESPESAS", totalDespesa);
        parameters.put("ROCA_DESCRICAO", roca.getDescricao());
        parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

        InputStream inputStream = this.getClass().getResourceAsStream(
                "/reports/despesas.jasper");

        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters, new JRBeanCollectionDataSource(despesas, false));

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
