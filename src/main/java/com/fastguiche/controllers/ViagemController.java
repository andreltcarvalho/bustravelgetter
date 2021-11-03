package com.fastguiche.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping ("/viagens")
public class ViagemController
{

    private static String BRAZIL_BUS_TRAVEL_URL = "https://brazilbustravel.com/bus/";
    private static String QUERO_PASSAGEM_URL = "https://queropassagem.com.br/onibus/";
    private String rodoviariaOrigem;
    private String rodoviariaDestino;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @GetMapping
    public ResponseEntity<String> getViagens(@RequestParam ("origem") String rodoviariaOrigem,
                                             @RequestParam ("destino") String rodoviariaDestino) throws Exception
    {
        this.rodoviariaOrigem = rodoviariaOrigem;
        this.rodoviariaDestino = rodoviariaDestino;
        return ResponseEntity.ok().body(buscarViagem());
    }

    private String buscarViagem() throws Exception
    {
        final HttpResponse<String> response = enviarRequest();

        final String parsedBody = pegarBodyParseado(response.body());
        final ObjectMapper mapper = new ObjectMapper();
        final List<HashMap<String, Object>> map = mapper.readValue(parsedBody, List.class);
        System.out.println(map);
        return parsedBody;
    }

    private HttpResponse<String> enviarRequest() throws IOException, InterruptedException
    {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(QUERO_PASSAGEM_URL + montarUriDeViagem("qp")))
                .setHeader("User-Agent", "Fast Guiche")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200)
        {
            request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(BRAZIL_BUS_TRAVEL_URL + montarUriDeViagem("bbt")))
                    .setHeader("User-Agent", "Fast Guiche")
                    .build();
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String pegarBodyParseado(String body)
    {
        return body.substring(body.lastIndexOf("/ld+json'>"),
                body.indexOf("</script></head><body class=\"main-body-busca layout-2021\" >")).replace("/ld+json'>", "");
    }

    private String montarUriDeViagem(String empresa)
    {
        if (empresa.equals("qp"))
        {
            return this.rodoviariaOrigem + "-para-" + this.rodoviariaDestino;
        }
        return this.rodoviariaOrigem + "-to-" + this.rodoviariaDestino;
    }
}
