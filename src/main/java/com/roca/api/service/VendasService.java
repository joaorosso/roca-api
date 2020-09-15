package com.roca.api.service;

import com.roca.api.model.Lucro;
import com.roca.api.model.Roca;
import com.roca.api.repository.LucroRepository;
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
public class VendasService {

    @Autowired
    private RocaRepository rocaRepository;

    @Autowired
    private LucroRepository lucroRepository;

    public byte[] vendasReport(UUID rocaId) throws Exception {
        Roca roca = rocaRepository.findById(rocaId).orElse(null);
        List<Lucro> vendas = lucroRepository.findAllByRocaId(rocaId);

        Map<String, Object> parameters = new HashMap<>();

        BigDecimal totalVendas = roca.getLucro();
        BigDecimal totalDespesa = roca.getDespesa();
        BigDecimal totalLucro = totalVendas.subtract(totalDespesa);

        parameters.put("TOTAL_VENDAS", totalVendas);
        parameters.put("TOTAL_DESPESAS", totalDespesa);
        parameters.put("TOTAL_LUCRO", totalLucro);
        parameters.put("ROCA_DESCRICAO", roca.getDescricao());
        parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

        InputStream inputStream = this.getClass().getResourceAsStream(
                "/reports/vendas.jasper");

        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters, new JRBeanCollectionDataSource(vendas, false));

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
