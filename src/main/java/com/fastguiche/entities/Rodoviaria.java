package com.fastguiche.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "rodoviarias")
public class Rodoviaria implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    private String tag;

    public Rodoviaria(Long id, String value, String tag)
    {
        this.id = id;
        this.value = value;
        this.tag = tag;
    }

    public Rodoviaria()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

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
        return "Rodoviaria [id=" + id + ", nome_display=" + value + ", nome_pesquisa=" + tag;
    }
}
