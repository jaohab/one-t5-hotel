package main.java.br.com.alura.hotel.testes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import main.java.br.com.alura.hotel.dao.HospedeDao;
import main.java.br.com.alura.hotel.dao.PagamentoDao;
import main.java.br.com.alura.hotel.dao.ReservaDao;
import main.java.br.com.alura.hotel.modelo.Hospede;
import main.java.br.com.alura.hotel.modelo.Pagamento;
import main.java.br.com.alura.hotel.modelo.Reserva;
import main.java.br.com.alura.hotel.util.JPAUtil;

public class TesteDeCadastro {

    public static void main(String[] args) {

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        //Cadastro Forma de Pagamento
        PagamentoDao pagamentoDao = new PagamentoDao(em);
        pagamentoDao.cadastrar(new Pagamento("Cartão crédito"));
        pagamentoDao.cadastrar(new Pagamento("Cartão de débito"));
        pagamentoDao.cadastrar(new Pagamento("Boleto"));
        pagamentoDao.cadastrar(new Pagamento("Dinheiro"));
        pagamentoDao.cadastrar(new Pagamento("PIX"));

        //Recuperando lista de Forma de Pagamento
        List<Pagamento> listaPagamento = pagamentoDao.buscarTodos();
        listaPagamento.get(0).setPagamento("Cartão de crédito"); //Atualização
        listaPagamento.stream().forEach(lista -> System.out.println(lista.toString()));
        
        //Cadastro Reserva
        ReservaDao reservaDao = new ReservaDao(em);
        reservaDao.cadastrar(new Reserva(LocalDate.of(2023, 9, 1), LocalDate.of(2023, 9, 7), new BigDecimal(700), listaPagamento.get(0)));
        reservaDao.cadastrar(new Reserva(LocalDate.of(2023, 9, 3), LocalDate.of(2023, 9, 9), new BigDecimal(800), listaPagamento.get(1)));
        reservaDao.cadastrar(new Reserva(LocalDate.of(2023, 9, 5), LocalDate.of(2023, 9, 11), new BigDecimal(900), listaPagamento.get(2)));

        //Recuperando lista de Reservas
        List<Reserva> listaReserva = reservaDao.buscarTodos();
        listaReserva.get(0).setValor(new BigDecimal(500)); //Atualização
        listaReserva.stream().forEach(lista -> System.out.println(lista.toString()));
        
        //Cadastro Hóspede
        Hospede hospede = new Hospede("Paulo", "Martins", LocalDate.of(1988, 10, 15), "Brasileiro", "(19) 98425.0203", listaReserva.get(0));
        HospedeDao hospedeDao = new HospedeDao(em);
        hospedeDao.cadastrar(hospede);

        //Recuperando lista de Hospedes
        List<Hospede> listaHospede = hospedeDao.buscarTodos();
        listaHospede.stream().forEach(lista -> System.out.println(lista.toString()));

        //Atualização
        listaHospede.get(0).setNome("Paula");
        listaHospede.get(0).setDataNascimento(LocalDate.of(1978, 10, 15)); 
        listaHospede.get(0).setReserva(listaReserva.get(2));

        //Busca por sobrenome
        List<Hospede> listaBuscaPorSobrenome = hospedeDao.buscarPorSobrenome("Martins");
        listaBuscaPorSobrenome.stream().forEach(lista -> System.out.println(lista.toString()));
        
        //Busca por numero da reserva 
        Hospede hospedeBuscaPorReserva = hospedeDao.buscarPorNumeroDaReserva(3l);
        System.out.println(hospedeBuscaPorReserva);

        em.getTransaction().commit();
        em.close();
        
    }
    
}
