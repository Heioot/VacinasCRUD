package poov.modelo;

public class Vacina {
    
        private Long codigo;
        private String nome;
        private String descricao;
    
        public Vacina() {
            codigo = -1L;
            nome = "Sem nome";
            descricao = "Sem descrição";
        }
        public Vacina(Long codigo, String nome, String descricao) {
            this.codigo = codigo;
            this.nome = nome;
            this.descricao = descricao;
        }
    
        public Long getCodigo() {
            return codigo;
        }
    
        public String getNome() {
            return nome;
        }
    
        public String getDescricao() {
            return descricao;
        }
    
        public void setCodigo(Long codigo) {
            this.codigo = codigo;
        }
    
        public void setNome(String nome) {
            this.nome = nome;
        }
    
        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }
        @Override
        public String toString() {
            return "Vacina [codigo=" + codigo + ", nome=" + nome + ", descricao=" + descricao + "]";
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
            result = prime * result + ((nome == null) ? 0 : nome.hashCode());
            result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
            return result;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Vacina other = (Vacina) obj;
            if (codigo == null) {
                if (other.codigo != null)
                    return false;
            } else if (!codigo.equals(other.codigo))
                return false;
            if (nome == null) {
                if (other.nome != null)
                    return false;
            } else if (!nome.equals(other.nome))
                return false;
            if (descricao == null) {
                if (other.descricao != null)
                    return false;
            } else if (!descricao.equals(other.descricao))
                return false;
            return true;
        }

        

    
}
