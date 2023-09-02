package main.java.br.com.alura.hotel.testes;

import javax.persistence.EntityManager;

import main.java.br.com.alura.hotel.util.JPAUtil;

public class TesteDeConexao {
    
    public static void main(String[] args) {

        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();
        
    }
    
}
