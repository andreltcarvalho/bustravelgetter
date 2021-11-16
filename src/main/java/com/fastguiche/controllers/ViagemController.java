package com.fastguiche.controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastguiche.operacoes.OperacoesData;
import com.fastguiche.operacoes.OperacoesHttp;

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

        return ResponseEntity.ok().body(buscarViagem());
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
        final JSONObject json = new JSONObject();
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
                mapa.put("chegada", OperacoesData.prepararHoraDeIda(horaIda));
                mapa.put("data", OperacoesData.prepararDataDaViagem(horaIda));
                mapa.put("ida", OperacoesData.prepararHoraDeVolta(horaChegada));
                mapa.remove("arrivalBusStop");
                mapa.remove("departureBusStop");
                final LinkedHashMap<String, String> provider = (LinkedHashMap<String, String>) mapa.get("provider");
                mapa.remove("provider");
                mapa.put("empresa", provider.get("name"));
                json.append("Itinerarios", mapa);

                // System.out.println(mapa.toString());
            }
            else
            {
                mapa.remove("offers");
                mapa.remove("name");
                json.append("dadosDaViagem", mapa);
            }
        });
        return json.toString();
    }

}
