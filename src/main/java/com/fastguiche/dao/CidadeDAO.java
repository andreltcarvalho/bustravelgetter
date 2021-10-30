package com.fastguiche.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastguiche.entities.Cidade;

public interface CidadeDAO extends JpaRepository<Cidade, Long>
{

}
