package main.java.br.com.alura.hotel.dao;

import java.util.List;

import javax.persistence.EntityManager;

import main.java.br.com.alura.hotel.modelo.Usuario;

public class UsuarioDao {

    private EntityManager em;

    public UsuarioDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Usuario usuario) {
        this.em.persist(usuario);
    }

    public void atualizar(Usuario usuario) {
        this.em.merge(usuario);
    }

    public void remover(Usuario usuario) {
        usuario = em.merge(usuario);
        this.em.remove(usuario);
    }

    public List<Usuario> buscarTodos() {
        String jpql = "SELECT u FROM Usuario u ";
        return em.createQuery(jpql, Usuario.class)
                .getResultList();
    }
    
}
