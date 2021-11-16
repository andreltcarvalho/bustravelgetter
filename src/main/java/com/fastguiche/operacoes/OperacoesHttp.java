package com.fastguiche.operacoes;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OperacoesHttp
{
    private static String BRAZIL_BUS_TRAVEL_URL = "https://brazilbustravel.com/bus/";
    private static String FIM_TAG_JSON_BRAZIL_BUS_TRAVEL = " <script>(function (w, d, s, l, i)";
    private static String BRAZIL_BUS_TRAVEL = "bbt";

    private static String QUERO_PASSAGEM_URL = "https://queropassagem.com.br/onibus/";
    private static String FIM_TAG_JSON_QUERO_PASSAGEM = "</script></head><body class=\"main-body-busca layout-2021\" >";
    private static String QUERO_PASSAGEM = "qp";

    private static String INICIO_TAG_JSON = "/ld+json'>";
    private static String SCRIPT_TAG = "</script>";
    private static String PARTIDA_QUERY_PARAMETER = "?partida=";

    public static String enviarRequest(String origem, String destino, String partida) throws IOException, InterruptedException
    {
        final HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        String usedServer = QUERO_PASSAGEM;

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(QUERO_PASSAGEM_URL + OperacoesHttp.montarUriDeViagem(usedServer, origem, destino, partida)))
                .setHeader("User-Agent", "Fast Guiche")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200)
        {
            usedServer = BRAZIL_BUS_TRAVEL;
            request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(BRAZIL_BUS_TRAVEL_URL + OperacoesHttp.montarUriDeViagem(usedServer, origem, destino, partida)))
                    .setHeader("User-Agent", "Fast Guiche")
                    .build();
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        return parseBody(response.body(), usedServer);
    }

    public static String parseBody(String body, String usedServer)
    {

        if (usedServer.equals(QUERO_PASSAGEM))
        {
            final int start = body.indexOf(INICIO_TAG_JSON);
            final int end = body.indexOf(FIM_TAG_JSON_QUERO_PASSAGEM);
            body = body.substring(start, end).replace(INICIO_TAG_JSON, "");
            return body;
        }

        if (usedServer.equals(BRAZIL_BUS_TRAVEL))
        {
            final int start = body.indexOf(INICIO_TAG_JSON);
            final int end = body.indexOf(FIM_TAG_JSON_BRAZIL_BUS_TRAVEL);
            return body.substring(start, end)
                    .replace(INICIO_TAG_JSON, "")
                    .replace(SCRIPT_TAG, "");
        }
        return "";
    }

    public static String montarUriDeViagem(String usedServer, String origem, String destino, String partida)
    {
        if (usedServer.equals(QUERO_PASSAGEM))
        {
            return origem + "-para-" + destino + PARTIDA_QUERY_PARAMETER + partida;
        }
        if (usedServer.equals(BRAZIL_BUS_TRAVEL))
        {
            return origem + "-to-" + destino + PARTIDA_QUERY_PARAMETER + partida;
        }
        return "";
    }
}
