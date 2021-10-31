package com.fastguiche.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastguiche.entities.Cidade;
import com.fastguiche.entities.Rodoviaria;
import com.fastguiche.services.CidadeService;
import com.fastguiche.services.RodoviariaService;

@RestController
@RequestMapping ("/teste")
public class TesteController
{

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private RodoviariaService rodoviariaService;

    @PostMapping ("/cidades")
    public ResponseEntity<Cidade> cidades(@RequestBody List<Cidade> obj)
    {
        obj.forEach(cidade ->
        {
            cidadeService.insert(cidade);
        });
        System.out.println(obj.toString());
        return ResponseEntity.noContent().build();
    }

    @PostMapping ("/rodoviaria/{id}")
    public ResponseEntity<Rodoviaria> rodoviariass(@RequestBody Rodoviaria obj, @PathVariable Long id)
    {
        System.out.println(id);
        final Cidade cidade = cidadeService.findById(id);
        obj.setCidade(cidade);
        rodoviariaService.insert(obj);
        System.out.println(obj.toString());
        return ResponseEntity.noContent().build();
    }

    @GetMapping ("/cidades")
    public ResponseEntity<List<Cidade>> findAll()
    {
        final List<Cidade> listaCidades = cidadeService.findAll();
        return ResponseEntity.ok().body(listaCidades);
    }

    @GetMapping ("/rodoviarias")
    public ResponseEntity<List<Rodoviaria>> findRodovs()
    {
        final List<Rodoviaria> listaRodoviarias = rodoviariaService.findAll();
        return ResponseEntity.ok().body(listaRodoviarias);
    }
}