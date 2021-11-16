package com.fastguiche.operacoes;

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
}
