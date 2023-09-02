package main.java.br.com.alura.hotel.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Formas_Pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pagamento;

    public Pagamento() {
    }

    public Pagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public Long getId() {
        return id;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    @Override
    public String toString() {
        return "Forma de pagamento cadastrado: [" + id + ", " + pagamento + "]";
    }
    
}
