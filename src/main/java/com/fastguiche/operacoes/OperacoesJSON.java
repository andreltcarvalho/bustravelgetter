package com.fastguiche.operacoes;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;

public class OperacoesJSON
{

    public static String pegarJsonFormatado(List<HashMap<String, Object>> list)
    {
        final JSONArray json = new JSONArray();
        list.forEach(mapa ->
        {

            mapa.remove("@type");
            mapa.remove("@context");
            if (!mapa.equals(list.get(0)))
            {
                final String horaIda = mapa.get("departureTime").toString();
                mapa.remove("departureTime");

                final String horaChegada = mapa.get("arrivalTime").toString();
                mapa.remove("arrivalTime");
                mapa.put("data", OperacoesData.prepararDataDaViagem(horaIda));
                mapa.put("ida", OperacoesData.prepararHoraDeIda(horaIda));
                mapa.put("chegada", OperacoesData.prepararHoraDeVolta(horaChegada));

                mapa.remove("arrivalBusStop");
                mapa.remove("departureBusStop");
                final LinkedHashMap<String, String> provider = (LinkedHashMap<String, String>) mapa.get("provider");
                mapa.remove("provider");
                mapa.put("empresa", provider.get("name"));
                json.put(mapa);
            }
            System.out.println("Map from response: " + mapa);
        });

        return json.toString();
    }
}
