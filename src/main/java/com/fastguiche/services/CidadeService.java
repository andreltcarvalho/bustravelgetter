package com.fastguiche.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.fastguiche.dao.CidadeDAO;
import com.fastguiche.entities.Cidade;
import com.fastguiche.services.exceptions.DatabaseException;
import com.fastguiche.services.exceptions.ResourceNotFoundException;

@Service
public class CidadeService
{

    @Autowired
    private CidadeDAO cidadeDAO;

    public List<Cidade> findAll()
    {
        return cidadeDAO.findAll();
    }

    public Cidade findById(Long id)
    {
        final Optional<Cidade> object = cidadeDAO.findById(id);
        return object.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public void SaveAll(List<Cidade> cidades)
    {
        cidadeDAO.saveAll(cidades);
    }

    public Cidade insert(Cidade obj)
    {
        return cidadeDAO.save(obj);
    }

    public void delete(Long id)
    {
        try
        {
            cidadeDAO.deleteById(id);
        }
        catch (final EmptyResultDataAccessException e)
        {
            throw new ResourceNotFoundException(id);
        }
        catch (final DataIntegrityViolationException e)
        {
            throw new DatabaseException(e.getMessage());

        }
    }
    //
    //	public Cidade update(Long id, Cidade obj) {
    //		try {
    //			Csidade entity = cidadeDAO.getOne(id);
    //			updateData(entity, obj);
    //			return cidadeDAO.save(entity);
    //		} catch (EntityNotFoundException e) {
    //			throw new ResourceNotFoundException(id);
    //		}
    //	}

    //	private void updateData(Cidade entity, Cidade obj) {
    //		entity.setNome(obj.getName());
    //		entity.setEstado(obj.getEmail());
    //		entity.setPhone(obj.getPhone());
    //
    //	}
}
