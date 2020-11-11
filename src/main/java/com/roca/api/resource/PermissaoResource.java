package com.roca.api.resource;

import com.roca.api.model.Permissao;
import com.roca.api.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/permissao")
public class PermissaoResource {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @GetMapping()
    public List<Permissao> getPermissoes() {
        return permissaoRepository.findAll();
    }
}
