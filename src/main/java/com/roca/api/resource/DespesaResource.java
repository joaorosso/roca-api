package com.roca.api.resource;

import com.roca.api.model.Despesa;
import com.roca.api.repository.DespesaRepository;
import com.roca.api.service.DespesaService;
import com.roca.api.util.ExcelGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/despesas")
public class DespesaResource {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private DespesaService despesaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Despesa post(@RequestBody Despesa despesa) {
        return despesaRepository.save(despesa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Despesa> put(@PathVariable UUID id, @RequestBody Despesa despesa) {
        Despesa despesaAtual = despesaRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(despesa, despesaAtual, "id");
        despesaRepository.save(despesaAtual);

        return ResponseEntity.ok(despesaAtual);
    }

    @GetMapping("/roca/{rocaId}")
    public List<Despesa> get(@PathVariable UUID rocaId) {
        return despesaRepository.findByRocaIdOrderByDataAsc(rocaId);
    }

    @GetMapping("/{id}")
    public Despesa getById(@PathVariable UUID id) {
        return despesaRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        despesaRepository.deleteById(id);
    }

    @GetMapping("/report/{rocaId}")
    public ResponseEntity<byte[]> despesasReport(@PathVariable UUID rocaId) throws Exception {
        byte[] report = despesaService.despesasReport(rocaId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio-despesas.pdf")
                .body(report);
    }

    @GetMapping("/excel/{rocaId}")
    public ResponseEntity<InputStreamResource> despesasExcel(@PathVariable UUID rocaId) throws Exception {
        List<Despesa> despesas = despesaRepository.findByRocaIdOrderByDataAsc(rocaId);

        ByteArrayInputStream in = ExcelGenerator.customersToExcel(despesas, "despesas");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=despesas.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }
}
