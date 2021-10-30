package com.fastguiche.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fastguiche.entities.Cidade;
import com.fastguiche.entities.Rodoviaria;
import com.fastguiche.services.CidadeService;
import com.fastguiche.services.RodoviariaService;

@Controller
@RequestMapping ("/teste")
public class TesteController
{

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private RodoviariaService rodoviariaService;

    @PostMapping ("/cidade")
    public ResponseEntity<Cidade> cidades(@RequestBody List<Cidade> obj)
    {
        obj.forEach(cidade ->
        {
            cidadeService.insert(cidade);

        });
        System.out.println(obj.toString());
        return ResponseEntity.noContent().build();
    }

    @PostMapping ("/rodoviaria")
    public ResponseEntity<Rodoviaria> rodoviariass(@RequestBody Rodoviaria obj)
    {
        rodoviariaService.insert(obj);
        System.out.println(obj.toString());
        return ResponseEntity.noContent().build();
    }
}
