package com.fastguiche.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "rodoviarias")
public class Rodoviaria implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome_display;

    private String nome_pesquisa;

    private String tipo;

    @ManyToOne
    @JoinColumn (name = "cidade_id")
    private Cidade cidade;

    public Rodoviaria()
    {
        super();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * @return the nome_display
     */
    public String getNome_display()
    {
        return nome_display;
    }

    /**
     * @param nome_display the nome_display to set
     */
    public void setNome_display(String nome_display)
    {
        this.nome_display = nome_display;
    }

    /**
     * @return the nome_pesquisa
     */
    public String getNome_pesquisa()
    {
        return nome_pesquisa;
    }

    /**
     * @param nome_pesquisa the nome_pesquisa to set
     */
    public void setNome_pesquisa(String nome_pesquisa)
    {
        this.nome_pesquisa = nome_pesquisa;
    }

    /**
     * @return the tipo
     */
    public String getTipo()
    {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    /**
     * @return the cidade
     */
    public Cidade getCidade()
    {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(Cidade cidade)
    {
        this.cidade = cidade;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
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
        final Rodoviaria other = (Rodoviaria) obj;
        if (id == null)
        {
            if (other.id != null) return false;
        }
        else if (!id.equals(other.id)) return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Rodoviaria [id=" + id + ", nome_display=" + nome_display + ", nome_pesquisa=" + nome_pesquisa + ", tipo=" + tipo
                + ", cidade=" + cidade + "]";
    }

    public Rodoviaria(Long id, String nome_display, String nome_pesquisa, String tipo, Cidade cidade)
    {

        this.id = id;
        this.nome_display = nome_display;
        this.nome_pesquisa = nome_pesquisa;
        this.tipo = tipo;
        this.cidade = cidade;
    }

}
