package com.fastguiche.operacoes;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OperacoesHttp
{
    private static String BRAZIL_BUS_TRAVEL_URL = "https://brazilbustravel.com/bus/";
    private static String INICIO_TAG_JSON_BRAZIL_BUS_TRAVEL = "/ld+json\">";
    private static String FIM_TAG_JSON_BRAZIL_BUS_TRAVEL = " <script>(function (w, d, s, l, i)";
    private static String BRAZIL_BUS_TRAVEL = "bbt";

    private static String QUERO_PASSAGEM_URL = "https://queropassagem.com.br/onibus/";
    private static String INICIO_TAG_JSON_QUERO_PASSAGEM = "/ld+json'>";
    private static String FIM_TAG_JSON_QUERO_PASSAGEM = "</script></head><body class=\"main-body-busca layout-2021\" >";
    private static String QUERO_PASSAGEM = "qp";

    private static String SCRIPT_TAG = "</script>";
    private static String PARTIDA_QUERY_PARAMETER = "?partida=";

    private static HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public static String enviarRequest(String origem, String destino, String partida) throws IOException, InterruptedException
    {

        HttpResponse<String> response = prepararRequest(QUERO_PASSAGEM, origem, destino, partida);

        if (response.statusCode() != 200)
        {
            response = prepararRequest(BRAZIL_BUS_TRAVEL, origem, destino, partida);
            return parseBody(response.body(), BRAZIL_BUS_TRAVEL);
        }
        System.out.println("Response headers: " + response.headers() + "\n");
        return parseBody(response.body(), QUERO_PASSAGEM);
    }

    private static HttpResponse<String>
            prepararRequest(String usedServer, String origem, String destino, String partida) throws IOException, InterruptedException
    {
        final HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(OperacoesHttp.montarUriDeViagem(usedServer, origem, destino, partida)))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static String parseBody(String body, String usedServer)
    {

        if (usedServer.equals(QUERO_PASSAGEM))
        {
            final int start = body.indexOf(INICIO_TAG_JSON_QUERO_PASSAGEM);
            final int end = body.indexOf(FIM_TAG_JSON_QUERO_PASSAGEM);
            body = body.substring(start, end).replace(INICIO_TAG_JSON_QUERO_PASSAGEM, "");
            return body;
        }

        if (usedServer.equals(BRAZIL_BUS_TRAVEL))
        {
            final int start = body.lastIndexOf(INICIO_TAG_JSON_BRAZIL_BUS_TRAVEL);
            final int end = body.indexOf(FIM_TAG_JSON_BRAZIL_BUS_TRAVEL);
            return body.substring(start, end)
                    .replace(INICIO_TAG_JSON_BRAZIL_BUS_TRAVEL, "")
                    .replace(SCRIPT_TAG, "");
        }
        return "";
    }

    public static String montarUriDeViagem(String usedServer, String origem, String destino, String partida)
    {
        if (usedServer.equals(QUERO_PASSAGEM))
        {
            System.out.println(
                    "\n" + "Request: " + QUERO_PASSAGEM_URL + origem + "-para-" + destino + PARTIDA_QUERY_PARAMETER + partida + "\n");
            return QUERO_PASSAGEM_URL + origem + "-para-" + destino + PARTIDA_QUERY_PARAMETER + partida;
        }
        if (usedServer.equals(BRAZIL_BUS_TRAVEL))
        {
            System.out.println("Request: " + BRAZIL_BUS_TRAVEL_URL + origem + "-to-" + destino + PARTIDA_QUERY_PARAMETER + partida + "\n");
            return BRAZIL_BUS_TRAVEL_URL + origem + "-to-" + destino + PARTIDA_QUERY_PARAMETER + partida;
        }
        return "";
    }
}
