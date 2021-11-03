package com.fastguiche.controllers;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastguiche.services.Operacoes;

@RestController
@RequestMapping ("/viagens")
public class ViagemController
{

    private String rodoviariaOrigem;
    private String rodoviariaDestino;

    @GetMapping
    public ResponseEntity<String> getViagens(@RequestParam ("origem") String origem,
                                             @RequestParam ("destino") String destino) throws Exception
    {
        this.rodoviariaOrigem = origem;
        this.rodoviariaDestino = destino;
        return ResponseEntity.ok().body(buscarViagem());
    }

    private String buscarViagem() throws Exception
    {

        final HttpResponse<String> response = Operacoes.enviarRequest(rodoviariaOrigem, rodoviariaDestino);
        final String parsedBody = Operacoes.parseBody(response.body());
        final ObjectMapper mapper = new ObjectMapper();
        final List<HashMap<String, Object>> map = mapper.readValue(parsedBody, List.class);
        System.out.println(map);
        return parsedBody;
    }

}
