package main.java.br.com.alura.hotel.testes;

import java.util.List;

import javax.persistence.EntityManager;

import main.java.br.com.alura.hotel.dao.UsuarioDao;
import main.java.br.com.alura.hotel.modelo.Usuario;
import main.java.br.com.alura.hotel.util.JPAUtil;

public class TesteDeCadastroUser {

    public static void main(String[] args) {

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        //Cadastro Usuário do Sistema
        Usuario user = new Usuario("j.batista88","123mudar");
        UsuarioDao usuarioDao = new UsuarioDao(em);
        user.setEmail("joao_hab@hotmail.com");
        usuarioDao.cadastrar(user);
        
        List<Usuario> listUsers = usuarioDao.buscarTodos();
        listUsers.stream().forEach(lista -> System.out.println(lista.toString()));

        em.getTransaction().commit();
        em.close();
        
    }
    
}
