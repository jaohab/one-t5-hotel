package main.java.br.com.alura.hotel.modelo;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;
    private String nome;
    private String sobrenome;
    private String email;
    private Date dateEHoraDoCadastro = new Date();
    private LocalDate ultimoAcesso;

    public Usuario() {
    }

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public Usuario(String login, String senha, String nome, String sobrenome, String email) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataEHoraDoCadastro() {
        return dateEHoraDoCadastro;
    }

    public LocalDate getUltimoAcesso() {
        return ultimoAcesso;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", login=" + login + ", senha=" + senha.replaceAll(".", "*") + ", nome=" + nome + ", sobrenome="
                + sobrenome + ", email=" + email + ", horaDoCadastro=" + dateEHoraDoCadastro + ", ultimoAcesso=" + ultimoAcesso + "]";
    }

}
