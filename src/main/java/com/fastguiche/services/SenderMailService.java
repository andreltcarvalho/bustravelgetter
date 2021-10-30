//package com.fastguiche.services;
//
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.stereotype.Component;
//
//import com.fastguiche.entities.Compra;
//
//@Component
//public class SenderMailService {
//
//	@Autowired
//	private JavaMailSenderImpl mailSender;
//
//	public void enviar(Compra compra) {
//		SimpleMailMessage email = new SimpleMailMessage();
//		email.setTo(compra.getUser().getEmail());
//		email.setSubject(montarSubject(compra));
//		email.setText(montarCorpoEmail(compra));
//
//		//mailSender.send(email);
//		System.out.println(email.toString());
//	}
//
//	public String montarSubject(Compra compra) {
//		return "Pedido de Passagem Fast Guiche";
//	}
//
//	public String montarCorpoEmail(Compra compra) {
//		StringBuilder builder = new StringBuilder();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//		builder.append("Olá " + compra.getUser().getNome() + "!" + "\n" + "\n" + "\n");
//		builder.append("Você concluiu um pedido na data e horário: " + LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).format(formatter) + "\n" + "\n");
//		builder.append("Deatlhes do pedido: " + "\n" + "\n");
//		builder.append("Linha: "+  compra.getViagem().getLinha().getNome()+ "\n" + "\n");
//		builder.append("Origem: " + compra.getViagem().getCidadeOrigem() + "   x   Destino: " + compra.getViagem().getCidadeDestino()+ "\n" + "\n");
//		builder.append("Data da Viagem: " + compra.getViagem().getData() + "\n" + "\n");
//		builder.append("Partida: " + compra.getViagem().getHoraInicial() + "          Chegada: "+ compra.getViagem().getHoraFinal()+ "\n" + "\n");
//		builder.append("Documento do Passageiro: " +compra.getTipoDocumento()+ " " + compra.getDocumentoViajante()+ "\n" + "\n");
//		builder.append("Tipo do Pagamento: " + compra.getTipoPagamento() + "\n" + "\n");
//		builder.append("Valor da Passagem: "+"R$" + compra.getViagem().getValor()+ "\n" + "\n");
//		builder.append("Obrigado pela preferência!");
//		return builder.toString();
//	}
//}
