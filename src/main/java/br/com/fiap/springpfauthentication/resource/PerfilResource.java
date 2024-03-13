package br.com.fiap.springpfauthentication.resource;

import br.com.fiap.springpfauthentication.entity.Perfil;
import br.com.fiap.springpfauthentication.entity.Permissao;
import br.com.fiap.springpfauthentication.repository.PerfilRepository;
import br.com.fiap.springpfauthentication.repository.PermissaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(value = "/perfil")
public class PerfilResource {

    @Autowired
    private PerfilRepository repoPerfil;

    @Autowired
    private PermissaoRepository repoPermissao;

    @GetMapping
    public List<Perfil> findAll() {

        return repoPerfil.findAll();
    }

    @GetMapping(value = "/{id}")
    public Perfil findById(@PathVariable Long id) {

        return repoPerfil.findById(id).orElseThrow();
    }
    @Transactional
    @PostMapping
    public Perfil save(@RequestBody Perfil perfil) {

        return repoPerfil.save(perfil);
    }

    @GetMapping(value = "/{id}/permissao")
    public Set<Permissao> findPermissao(@PathVariable Long id) {
        Perfil perfil = repoPerfil.findById(id).orElseThrow();
        return perfil.getPermissoes();
    }

    @Transactional
    @PostMapping(value = "/{id}/permissao")
    public Perfil addPermissao(@PathVariable Long id, @RequestBody Permissao perm) {
        Perfil perfil = repoPerfil.findById(id).orElseThrow();
        if (Objects.isNull(perm)) return null;

        if (Objects.nonNull(perm.getId())) {
            Permissao permissao = repoPermissao.findById(perm.getId()).orElseThrow();
            perfil.getPermissoes().add(permissao);
            return perfil;
        }
        perfil.getPermissoes().add(perm);
        return perfil;
    }

}