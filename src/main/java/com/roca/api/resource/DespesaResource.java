package com.roca.api.resource;

import com.roca.api.model.Despesa;
import com.roca.api.repository.DespesaRepository;
import com.roca.api.service.DespesaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return despesaRepository.findAllByRocaId(rocaId);
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
                .body(report);
    }
}
