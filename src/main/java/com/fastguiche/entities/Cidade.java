package com.fastguiche.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table (name = "cidades")
public class Cidade implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome_display;

    private String nome_pesquisa;

    private Integer total;

    private String tipo;

    @JsonIgnore
    @OneToMany (mappedBy = "cidade")
    private List<Rodoviaria> filhos;

    public Cidade()
    {

    }

    @Override
    public String toString()
    {
        return "Cidade [id=" + id + ", nome_display=" + nome_display + ", nome_pesquisa=" + nome_pesquisa + ", total=" + total + ", tipo="
                + tipo + ", filhos=" + filhos + "]";
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getNome_display()
    {
        return nome_display;
    }

    public void setNome_display(String nome_display)
    {
        this.nome_display = nome_display;
    }

    public String getNome_pesquisa()
    {
        return nome_pesquisa;
    }

    public void setNome_pesquisa(String nome_pesquisa)
    {
        this.nome_pesquisa = nome_pesquisa;
    }

    public Integer getTotal()
    {
        return total;
    }

    public void setTotal(Integer total)
    {
        this.total = total;
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    public List<Rodoviaria> getFilhos()
    {
        return filhos;
    }

    public void setFilhos(List<Rodoviaria> filhos)
    {
        this.filhos = filhos;
    }

    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

    public Cidade(Long id, String nome_display, String nome_pesquisa, Integer total, String tipo, List<Rodoviaria> filhos)
    {
        super();
        this.id = id;
        this.nome_display = nome_display;
        this.nome_pesquisa = nome_pesquisa;
        this.total = total;
        this.tipo = tipo;
        this.filhos = filhos;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Cidade other = (Cidade) obj;
        if (id == null)
        {
            if (other.id != null) return false;
        }
        else if (!id.equals(other.id)) return false;
        return true;
    }

}
