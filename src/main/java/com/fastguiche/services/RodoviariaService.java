package com.fastguiche.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.fastguiche.dao.RodoviariaDAO;
import com.fastguiche.entities.Rodoviaria;
import com.fastguiche.services.exceptions.DatabaseException;
import com.fastguiche.services.exceptions.ResourceNotFoundException;

@Service
public class RodoviariaService
{

    @Autowired
    private RodoviariaDAO rodoviariaDAO;

    public List<Rodoviaria> findAll()
    {
        return rodoviariaDAO.findAll();
    }

    public Rodoviaria findById(Long id)
    {
        final Optional<Rodoviaria> object = rodoviariaDAO.findById(id);
        return object.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public void SaveAll(List<Rodoviaria> rodoviarias)
    {
        rodoviariaDAO.saveAll(rodoviarias);
    }

    public Rodoviaria insert(Rodoviaria obj)
    {
        return rodoviariaDAO.save(obj);
    }

    public void delete(Long id)
    {
        try
        {
            rodoviariaDAO.deleteById(id);
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
}
