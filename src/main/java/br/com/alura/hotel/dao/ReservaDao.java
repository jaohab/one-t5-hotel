package main.java.br.com.alura.hotel.dao;

import java.util.List;

import javax.persistence.EntityManager;

import main.java.br.com.alura.hotel.modelo.Reserva;

public class ReservaDao {

    private EntityManager em;

    public ReservaDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Reserva reserva) {
        this.em.persist(reserva);
    }

    public void atualizar(Reserva reserva) {
        this.em.merge(reserva);
    }

    public void remover(Reserva reserva) {
        reserva = em.merge(reserva);
        this.em.remove(reserva);
    }

    public List<Reserva> buscarTodos() {
        String jpql = "SELECT r FROM Reserva r ";
        return em.createQuery(jpql, Reserva.class)
                .getResultList();
    }

    public Reserva buscarPorNumeroDaReserva(Long id) {
        String jpql = "SELECT r FROM Reserva r WHERE r.id = ?1";
        return em.createQuery(jpql, Reserva.class)
                .setParameter(1, id)
                .getSingleResult();
    }
    
}
