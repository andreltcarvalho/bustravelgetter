package com.fastguiche.controllers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/viagens")
public class ViagemController
{
    private static String QUERO_PASSAGEM_URL = "https://queropassagem.com.br/onibus/";
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @GetMapping
    public ResponseEntity<String> getViagens(@RequestParam ("origem") String rodoviariaOrigem,
                                             @RequestParam ("destino") String rodoviariaDestino) throws Exception
    {
        final String uri = montarUri(rodoviariaOrigem, rodoviariaDestino);
        return ResponseEntity.ok().body(buscarViagem(uri));
    }

    private String buscarViagem(String uri) throws Exception
    {
        final HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(QUERO_PASSAGEM_URL + uri))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200)
        {
            return parseResponseBody(response.body());
        }
        return "something went wrong";
    }

    private String parseResponseBody(String body)
    {
        System.out.println(body.indexOf("/ld+json'>"));
        System.out.println(body.lastIndexOf("/ld+json'>"));
        return body.substring(body.lastIndexOf("/ld+json'>"),
                body.indexOf("</script></head><body class=\"main-body-busca layout-2021\" >")).replace("/ld+json'>", "");
    }

    private String montarUri(String origem, String destino)
    {
        return origem + "-para-" + destino;
    }
}
