package poov.modelo;

import java.time.LocalDate;

public class Pessoa {
    private Long codigo;
    private String nome;
    private String cpf;
    private LocalDate data;
    private Situacao situacao;
    private String dataBrasil;


    public String getDataBrasil(){
        return dataBrasil;
    }
    public void setDataBrasil(String dataBrasil){
        this.dataBrasil = dataBrasil;
    }
    public Long getCodigo() {
        return codigo;
    }
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public Situacao getSituacao() {
        return situacao;
    }
    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }


    @Override
    public String toString() {
        return "Pessoa [codigo=" + codigo + ", nome=" + nome + ", cpf=" + cpf + ", data=" + data + ", situacao="
        + situacao +", dataBrasil= "+dataBrasil+ "]";
    }
    

    public Pessoa() {
    }
    public Pessoa(Long codigo, String nome, String cpf, LocalDate data, Situacao situacao) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.data = data;
        this.situacao = situacao;
    }

    
    
}
