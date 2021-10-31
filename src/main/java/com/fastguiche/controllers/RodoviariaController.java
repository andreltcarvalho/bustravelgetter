package com.fastguiche.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastguiche.entities.Rodoviaria;
import com.fastguiche.services.RodoviariaService;

@RestController
@RequestMapping ("/rodoviarias")
public class RodoviariaController
{

    @Autowired
    private RodoviariaService rodoviariaService;

    @PostMapping ()
    public ResponseEntity<Rodoviaria> insertRodoviarias(@RequestBody List<Rodoviaria> obj)
    {
        obj.forEach(rodoviaria ->
        {
            rodoviariaService.insert(rodoviaria);
        });
        return ResponseEntity.ok().build();
    }

    @GetMapping ()
    public ResponseEntity<List<Rodoviaria>> findRodovs()
    {
        final List<Rodoviaria> listaRodoviarias = rodoviariaService.findAll();
        return ResponseEntity.ok().body(listaRodoviarias);
    }
}
