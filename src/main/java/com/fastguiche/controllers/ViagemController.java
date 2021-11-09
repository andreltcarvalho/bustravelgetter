package com.fastguiche.controllers;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastguiche.services.Operacoes;
import com.mysql.cj.util.StringUtils;

@RestController
@RequestMapping ("/viagens")
public class ViagemController
{

    private String rodoviariaOrigem;
    private String rodoviariaDestino;
    private String partida;

    private String getArrivalDate(String date)
    {
        final String newDate = date.substring(date.lastIndexOf("T"));
        final String dates[] = newDate.split(":");
        final String horaChegada = dates[0].replace("T", "");
        Integer horaSoma = Integer.parseInt(horaChegada);
        String horaResponse = "";
        if (horaChegada.equals("00"))
        {
            horaResponse = "21";
        }
        if (horaChegada.equals("01"))
        {
            horaResponse = "22";
        }
        if (horaChegada.equals("02"))
        {
            horaResponse = "23";
        }

        if (StringUtils.isNullOrEmpty(horaResponse))
        {
            horaSoma = horaSoma - 3;
            if (horaSoma < 10)
            {
                horaResponse = "0" + horaSoma;
            }
            else
            {
                horaResponse = horaSoma + "";
            }
        }

        final char[] chegadas = horaResponse.toCharArray();

        final StringBuilder builder = new StringBuilder();
        builder.append(date.substring(date.indexOf("T")).replace("T", "").substring(0, 5));
        builder.setCharAt(0, chegadas[0]);
        builder.setCharAt(1, chegadas[1]);
        return builder.toString();
    }

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
        final JSONObject json = new JSONObject();
        list.forEach(mapa ->
        {
            if (!mapa.equals(list.get(0)))
            {
                final String horaChegada = mapa.get("arrivalTime").toString();
                mapa.put("arrivalTime", getArrivalDate(horaChegada));
                System.out.println(mapa.get(("arrivalTime")));
                json.append("itinerario", mapa);
            }
            else
            {
                json.append("dadosViagem", mapa);
            }
        });

        return json.toString();
    }

}
