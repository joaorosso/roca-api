package com.roca.api.resource;

import com.roca.api.model.Despesa;
import com.roca.api.repository.DespesaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/despesas")
public class DespesaResource {

    @Autowired
    private DespesaRepository despesaRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Despesa post(@RequestBody Despesa despesa) {
        Double total = despesa.getQuantidade() * despesa.getValorUnitario();
        despesa.setTotal(total);

        return despesaRepository.save(despesa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Despesa> put(@PathVariable UUID id, @RequestBody Despesa despesa) {
        Double total = despesa.getQuantidade() * despesa.getValorUnitario();
        despesa.setTotal(total);

        Despesa despesaAtual = despesaRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(despesa, despesaAtual, "id");
        despesaRepository.save(despesaAtual);

        return ResponseEntity.ok(despesaAtual);
    }

    @GetMapping
    public List<Despesa> get() {
        return despesaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Despesa getById(@PathVariable UUID id) {
        return despesaRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        despesaRepository.deleteById(id);
    }
}
