package poov.modelo;

import java.time.LocalDate;

public class Aplicacao {
    private Long codigo;
    private LocalDate data;
    private Long codigo_pessoa;
    private Long codigo_vacina;
    private Situacao situacao;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Long getCodigo_pessoa() {
        return codigo_pessoa;
    }

    public void setCodigo_pessoa(Long codigo_pessoa) {
        this.codigo_pessoa = codigo_pessoa;
    }

    public Long getCodigo_vacina() {
        return codigo_vacina;
    }

    public void setCodigo_vacina(Long codigo_vacina) {
        this.codigo_vacina = codigo_vacina;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Aplicacao() {
    }

    public Aplicacao(Long codigo, LocalDate data, Long codigo_pessoa, Long codigo_vacina, Situacao situacao) {
        this.codigo = codigo;
        this.data = data;
        this.codigo_pessoa = codigo_pessoa;
        this.codigo_vacina = codigo_vacina;
        this.situacao = situacao;
    }

    @Override
    public String toString() {
        return "Aplicacao [codigo=" + codigo + ", data=" + data + ", codigo_pessoa=" + codigo_pessoa
                + ", codigo_vacina=" + codigo_vacina + ", situacao=" + situacao + "]";
    }

}
