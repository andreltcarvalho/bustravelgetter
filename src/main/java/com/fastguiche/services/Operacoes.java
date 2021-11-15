//**********************************************************************
// Copyright (c) 2021 Telefonaktiebolaget LM Ericsson, Sweden.
// All rights reserved.
// The Copyright to the computer program(s) herein is the property of
// Telefonaktiebolaget LM Ericsson, Sweden.
// The program(s) may be used and/or copied with the written permission
// from Telefonaktiebolaget LM Ericsson or in accordance with the terms
// and conditions stipulated in the agreement/contract under which the
// program(s) have been supplied.
// **********************************************************************
package com.fastguiche.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Operacoes
{
    private static String BRAZIL_BUS_TRAVEL_URL = "https://brazilbustravel.com/bus/";
    private static String QUERO_PASSAGEM_URL = "https://queropassagem.com.br/onibus/";
    private static String PARTIDA_QUERY_PARAMETER = "?partida=";
    private final static HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public static String parseBody(String body, String usedServer)
    {
        if (usedServer.equals("qp"))
        {
            final int start = body.lastIndexOf("/ld+json'>");
            final int end = body.indexOf("</script></head><body class=\"main-body-busca layout-2021\" >");

            return body.substring(start, end).replace("/ld+json'>", "");

        }
        else if (usedServer.equals("bbt"))
        {
            final int start = body.lastIndexOf("/ld+json\">");
            final int end = body.indexOf(" <script>(function (w, d, s, l, i)");
            return body.substring(start, end)
                    .replace("/ld+json\">", "")
                    .replace("</script>", "");
        }
        return "";
    }

    public static String montarUriDeViagem(String usedServer, String origem, String destino, String partida)
    {
        if (usedServer.equals("qp"))
        {
            return origem + "-para-" + destino + PARTIDA_QUERY_PARAMETER + partida;
        }
        if (usedServer.equals("bbt"))
        {
            return origem + "-to-" + destino + PARTIDA_QUERY_PARAMETER + partida;
        }
        return "";
    }

    public static String enviarRequest(String origem, String destino, String partida) throws IOException,
                                                                                      InterruptedException
    {
        String usedServer = "qp";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(QUERO_PASSAGEM_URL + Operacoes.montarUriDeViagem(usedServer, origem, destino, partida)))
                .setHeader("User-Agent", "Fast Guiche")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200)
        {
            usedServer = "bbt";
            request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(BRAZIL_BUS_TRAVEL_URL + Operacoes.montarUriDeViagem(usedServer, origem, destino, partida)))
                    .setHeader("User-Agent", "Fast Guiche")
                    .build();
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        return parseBody(response.body(), usedServer);
    }
}
