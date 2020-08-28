package com.roca.api.resource;

import com.roca.api.model.Roca;
import com.roca.api.repository.RocaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rocas")
public class RocaResource {

    @Autowired
    private RocaRepository rocaRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Roca post(@RequestBody Roca roca) {
        return rocaRepository.save(roca);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Roca> put(@PathVariable UUID id, @RequestBody Roca roca) throws Exception {
        Roca rocaAtual = rocaRepository.findById(id).orElse(null);
        if (rocaAtual.isFechado()) {
            throw new Exception("Não é possível alterar uma roça fechada.");
        } else {
            BeanUtils.copyProperties(roca, rocaAtual, "id");
            rocaRepository.save(rocaAtual);
            return ResponseEntity.ok(rocaAtual);
        }
    }

    @GetMapping
    public List<Roca> get() {
        return rocaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Roca> getById(@PathVariable UUID id) {
        Roca roca = rocaRepository.findById(id).orElse(null);
        return ResponseEntity.ok(roca);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        rocaRepository.deleteById(id);
    }
}
