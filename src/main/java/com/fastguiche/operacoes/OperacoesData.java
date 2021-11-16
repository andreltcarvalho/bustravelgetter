package com.fastguiche.operacoes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.mysql.cj.util.StringUtils;

public class OperacoesData
{

    public static String prepararHoraDeVolta(String data)
    {
        //no queroPassagem a hora vem formatada 3 horas a diante por algum motivo
        //entao eu pego essa hora, e partir dela eu subtraio 3.
        final String novaData = data.substring(data.lastIndexOf("T"));
        final String dates[] = novaData.split(":");
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

        //se nao for nenhum desses horarios acima que a soma daria erro
        //entao eu subtraio por 3 esse valor pra pegar a hora valida
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

        //aqui eu pego a data inicial e substituo nela os novos valores
        // com datas reais e formatadas.
        final StringBuilder builder = new StringBuilder();
        builder.append(data.substring(data.indexOf("T")).replace("T", "").substring(0, 5));
        builder.setCharAt(0, chegadas[0]);
        builder.setCharAt(1, chegadas[1]);
        return builder.toString();
    }

    public static String prepararDataDaViagem(String data)
    {
        final String datas[] = data.split("T");
        return datas[0];
    }

    public static String prepararHoraDeIda(String hora)
    {
        return hora.substring(hora.indexOf("T"), hora.indexOf("T") + 6).replace("T", "");
    }

    public static String pegarHoraValida(String partida)
    {
        if (!partida.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})"))
        {
            return "";
        }

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        final LocalDate hoje = LocalDate.now();
        final String hojeFormatado = hoje.format(formatter);

        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            if (sdf.parse(partida).before(sdf.parse(hojeFormatado)))
            {
                return hojeFormatado;
            }
            return partida;
        }
        catch (final ParseException e)
        {
            return "";
        }
    }
}
