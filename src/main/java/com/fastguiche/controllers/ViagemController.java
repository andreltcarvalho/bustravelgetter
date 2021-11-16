package com.fastguiche.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastguiche.operacoes.OperacoesHttp;
import com.fastguiche.operacoes.OperacoesJSON;

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
        setarCamposValidos(origem, destino, partida);
        try
        {
            return ResponseEntity.ok().body(buscarViagem());
        }
        catch (final Exception e)
        {
            return ResponseEntity.badRequest().body("Erro");
        }
    }

    private void setarCamposValidos(String origem, String destino, String partida)
    {
        this.rodoviariaOrigem = origem;
        this.rodoviariaDestino = destino;
        this.partida = partida;
    }

    private String buscarViagem() throws Exception
    {

        final String response = OperacoesHttp.enviarRequest(rodoviariaOrigem, rodoviariaDestino, partida);

        final ObjectMapper mapper = new ObjectMapper();
        final List<HashMap<String, Object>> list = mapper.readValue(response, List.class);

        return OperacoesJSON.pegarJsonFormatado(list);
    }

}
