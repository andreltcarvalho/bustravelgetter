package com.fastguiche.controllers;

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
    private String partida;

    @GetMapping
    public ResponseEntity<String> getViagens(@RequestParam ("origem") String origem,
                                             @RequestParam ("destino") String destino,
                                             @RequestParam ("partida") String partida) throws Exception
    {
        this.rodoviariaOrigem = origem;
        this.rodoviariaDestino = destino;
        this.partida = partida;
        return ResponseEntity.ok().body(buscarViagem());
    }

    private String buscarViagem() throws Exception
    {

        final String response = Operacoes.enviarRequest(rodoviariaOrigem, rodoviariaDestino, partida);

        final ObjectMapper mapper = new ObjectMapper();
        final List<HashMap<String, Object>> list = mapper.readValue(response, List.class);
        list.forEach(mapa ->
        {
            System.out.println(mapa);
        });

        return response;
    }

}
