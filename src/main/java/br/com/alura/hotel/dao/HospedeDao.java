package main.java.br.com.alura.hotel.dao;

import java.util.List;

import javax.persistence.EntityManager;

import main.java.br.com.alura.hotel.modelo.Hospede;

public class HospedeDao {

    private EntityManager em;

    public HospedeDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Hospede hospede) {
        this.em.persist(hospede);
    }

    public void atualizar(Hospede hospede) {
        this.em.merge(hospede);
    }

    public void remover(Hospede hospede) {
        hospede = em.merge(hospede);
        this.em.remove(hospede);
    }

    public List<Hospede> buscarTodos() {
        String jpql = "SELECT h FROM Hospede h ";
        return em.createQuery(jpql, Hospede.class)
                .getResultList();
    }

    public List<Hospede> buscarPorNome(String nome) {
        String jpql = "SELECT h FROM Hospede h WHERE h.nome = ?1";
        return em.createQuery(jpql, Hospede.class)
                .setParameter(1, nome)
                .getResultList();
    }

    public List<Hospede> buscarPorSobrenome(String sobrenome) {
        String jpql = "SELECT h FROM Hospede h WHERE h.sobrenome = ?1";
        return em.createQuery(jpql, Hospede.class)
                .setParameter(1, sobrenome)
                .getResultList();
    }

    public Hospede buscarPorId(Long id) {
        String jpql = "SELECT h FROM Hospede h WHERE h.id = ?1";
        return em.createQuery(jpql, Hospede.class)
                .setParameter(1, id)
                .getSingleResult();
    }

    public Hospede buscarPorNumeroDaReserva(Long id) {
        String jpql = "SELECT h FROM Hospede h WHERE h.reserva.id = ?1";
        return em.createQuery(jpql, Hospede.class)
                .setParameter(1, id)
                .getSingleResult();
    }
    
}
