package main.java.br.com.alura.hotel.dao;

import java.util.List;

import javax.persistence.EntityManager;

import main.java.br.com.alura.hotel.modelo.Pagamento;

public class PagamentoDao {

    private EntityManager em;

    public PagamentoDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Pagamento pagamento) {
        this.em.persist(pagamento);
    }

    public void atualizar(Pagamento pagamento) {
        this.em.merge(pagamento);
    }

    public void remover(Pagamento pagamento) {
        pagamento = em.merge(pagamento);
        this.em.remove(pagamento);
    }

    public List<Pagamento> buscarTodos() {
        String jpql = "SELECT p FROM Pagamento p ";
        return em.createQuery(jpql, Pagamento.class)
                .getResultList();
    }
    
}
