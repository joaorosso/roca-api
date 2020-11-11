package com.roca.api.resource;

import com.roca.api.model.Usuario;
import com.roca.api.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Usuario> get() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> getById(@PathVariable UUID id) {
        return usuarioRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario post(@RequestBody Usuario usuario) {
        String password = usuario.getPassword();
        usuario.setPassword(passwordEncoder.encode(password));

        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> put(@PathVariable UUID id, @RequestBody Usuario usuario) {
        Usuario usuarioAtual = usuarioRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(usuario, usuarioAtual, "id");

        String password = usuarioAtual.getPassword();
        usuarioAtual.setPassword(passwordEncoder.encode(password));

        usuarioRepository.save(usuarioAtual);

        return ResponseEntity.ok(usuarioAtual);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        usuarioRepository.deleteById(id);
    }
}
