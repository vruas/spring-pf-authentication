package br.com.fiap.springpfauthentication.resource;

import br.com.fiap.springpfauthentication.entity.Sistema;
import br.com.fiap.springpfauthentication.entity.Usuario;
import br.com.fiap.springpfauthentication.repository.SistemaRepository;
import br.com.fiap.springpfauthentication.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;


@RestController
@RequestMapping(value = "/sistema")
public class SistemaResource {
    @Autowired
    private SistemaRepository repoSistema;

    @Autowired
    private UsuarioRepository repoUsuario;

    @GetMapping
    public List<Sistema> findAll() {

        return repoSistema.findAll();
    }

    @GetMapping(value = "/{id}")
    public Sistema findById(@PathVariable Long id) {

        return repoSistema.findById(id).orElseThrow();
    }

    @Transactional
    @PostMapping
    public Sistema save(@RequestBody Sistema sistema) {

        return repoSistema.save(sistema);
    }

    @GetMapping(value = "/{id}/responsaveis")
    public Set<Usuario> findResponsaveis(@PathVariable Long id) {
        Sistema sistema = repoSistema.findById(id).orElseThrow();
        return sistema.getResponsaveis();
    }

    @GetMapping(value = "/{id}/responsaveis")
    public Sistema addResponsavel(@PathVariable Long id, @RequestBody Usuario user) {
        Sistema sistema = repoSistema.findById(id).orElseThrow();

        if (Objects.isNull(user)) return null;
        if (Objects.nonNull(user.getId())) {
            Usuario usuario = repoUsuario.findById(user.getId()).orElseThrow();
            sistema.getResponsaveis().add(usuario);
            return sistema;
        }
        sistema.getResponsaveis().add(user);
        return sistema;
    }



}
