package com.roca.api.resource;

import com.roca.api.model.Despesa;
import com.roca.api.model.Lucro;
import com.roca.api.repository.LucroRepository;
import com.roca.api.service.VendasService;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lucros")
public class LucroResource {

    @Autowired
    private LucroRepository lucroRepository;

    @Autowired
    private VendasService vendasService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lucro post(@RequestBody Lucro lucro) {
        return lucroRepository.save(lucro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lucro> put(@PathVariable UUID id, @RequestBody Lucro lucro) {
        Lucro lucroAtual = lucroRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(lucro, lucroAtual, "id");
        lucroRepository.save(lucroAtual);

        return ResponseEntity.ok(lucroAtual);
    }

    @GetMapping("/roca/{rocaId}")
    public List<Lucro> get(@PathVariable UUID rocaId) {
        return lucroRepository.findByRocaIdOrderByDataAsc(rocaId);
    }

    @GetMapping("/{id}")
    public Lucro getById(@PathVariable UUID id) {
        return lucroRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        lucroRepository.deleteById(id);
    }

    @GetMapping("/report/{rocaId}")
    public ResponseEntity<byte[]> vendasReport(@PathVariable UUID rocaId) throws Exception {
        byte[] report = vendasService.vendasReport(rocaId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio-vendas.pdf")
                .body(report);
    }

    @GetMapping("/excel/{rocaId}")
    public ResponseEntity<InputStreamResource> despesasExcel(@PathVariable UUID rocaId) throws Exception {
        List<Lucro> lucros = lucroRepository.findByRocaIdOrderByDataAsc(rocaId);

        ByteArrayInputStream in = ExcelGenerator.customersToExcel(lucros, "lucros");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=despesas.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }
}
