package com.roca.api.resource;

import com.roca.api.model.Lucro;
import com.roca.api.repository.LucroRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lucros")
public class LucroResource {

    @Autowired
    private LucroRepository lucroRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lucro post(@RequestBody Lucro lucro) {
        Double total = lucro.getQuantidade() * lucro.getValorUnitario();
        lucro.setTotal(total);

        return lucroRepository.save(lucro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lucro> put(@PathVariable UUID id, @RequestBody Lucro lucro) {
        Double total = lucro.getQuantidade() * lucro.getValorUnitario();
        lucro.setTotal(total);

        Lucro lucroAtual = lucroRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(lucro, lucroAtual, "id");
        lucroRepository.save(lucroAtual);

        return ResponseEntity.ok(lucroAtual);
    }

    @GetMapping("/roca/{rocaId}")
    public List<Lucro> get(@PathVariable UUID rocaId) {
        return lucroRepository.findAllByRocaId(rocaId);
    }

    @GetMapping("/{id}")
    public Lucro getById(@PathVariable UUID id) {
        return lucroRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        lucroRepository.deleteById(id);
    }
}
