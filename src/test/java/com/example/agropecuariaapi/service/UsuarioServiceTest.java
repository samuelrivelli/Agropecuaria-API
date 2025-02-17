package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.exceptions.RegraNegocioException;
import com.example.agropecuariaapi.exceptions.SenhaInvalidaException;
import com.example.agropecuariaapi.model.entity.Usuario;
import com.example.agropecuariaapi.model.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setLogin("usuarioTeste");
        usuario.setSenha("senha123");
    }

    @Test
    public void testSalvarUsuario_Valid() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        Usuario savedUsuario = usuarioService.salvar(usuario);
        assertNotNull(savedUsuario);
        assertEquals("usuarioTeste", savedUsuario.getLogin());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void testSalvarUsuario_InvalidLogin() {
        usuario.setLogin(null);
        assertThrows(RegraNegocioException.class, () -> usuarioService.salvar(usuario));
    }

    @Test
    public void testAutenticar_Valid() {
        UserDetails userDetails = mock(UserDetails.class);
        when(usuarioRepository.findByLogin(usuario.getLogin())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        UserDetails authenticatedUser = usuarioService.autenticar(usuario);
        assertEquals(usuario.getLogin(), authenticatedUser.getUsername());
    }

    @Test
    public void testAutenticar_InvalidPassword() {
        UserDetails userDetails = mock(UserDetails.class);
        when(usuarioRepository.findByLogin(usuario.getLogin())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        assertThrows(SenhaInvalidaException.class, () -> usuarioService.autenticar(usuario));
    }

    @Test
    public void testLoadUserByUsername_Valid() {
        when(usuarioRepository.findByLogin(usuario.getLogin())).thenReturn(Optional.of(usuario));
        UserDetails userDetails = usuarioService.loadUserByUsername(usuario.getLogin());
        assertEquals(usuario.getLogin(), userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsername_UsuarioNotFound() {
        when(usuarioRepository.findByLogin(usuario.getLogin())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> usuarioService.loadUserByUsername(usuario.getLogin()));
    }

    @Test
    public void testExcluirUsuario() {
        doNothing().when(usuarioRepository).delete(any(Usuario.class));
        usuarioService.excluir(usuario);
        verify(usuarioRepository, times(1)).delete(usuario);
    }
}
