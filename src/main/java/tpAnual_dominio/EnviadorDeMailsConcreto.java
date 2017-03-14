package tpAnual_dominio;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class EnviadorDeMailsConcreto extends EnviadorDeMails {
		
	@Column
	String adminEmail;
	@Transient
	private final Properties properties = new Properties();
	@Transient
	private Session session;
	@Transient
	String EMAILEMISOR = "emisor@gmail.com";

	

	public EnviadorDeMailsConcreto(String adminEmail) {
		super();
		this.adminEmail = adminEmail;
		init();
	}


	public EnviadorDeMailsConcreto() {
		init();
	}
	
 
	private void init() {
		//modificar en caso de que el mail no sea gmail
		properties.put("mail.smtp.host", "mail.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port",25);
		properties.put("mail.smtp.mail.sender",EMAILEMISOR);
		properties.put("mail.smtp.user", "usuario");
		properties.put("mail.smtp.auth", "true");
 
		session = Session.getDefaultInstance(properties);
	}
 
	public void sendEmail(String asunto, String mensaje){
 
		init();
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress((String)properties.get("mail.smtp.mail.sender")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(adminEmail));
			message.setSubject(asunto);
			message.setText(mensaje);
			Transport t = session.getTransport("smtp");
			t.connect((String)properties.get("mail.smtp.user"), "password");
			t.sendMessage(message, message.getAllRecipients());
			t.close();
			return;
		}catch (MessagingException me){
			throw(new RuntimeException("No se ha podido enviar el email de notificacion"));
		}
	}
 	
	public void informarBusquedaLenta(String nombreTerminal, String criterioDeBusqueda,
			long tiempoMaximoBusquedaPermitido, long tiempoBusqueda) {
		String asunto = "Busqueda Lenta";
		String mensaje = new StringBuilder().append("Se ha producido una busqueda lenta en le terminal: ")
							.append(nombreTerminal.toUpperCase())
							.append(" bajo el criterio: ")
							.append(criterioDeBusqueda.toUpperCase())
							.append(".  El tiempo maximo permitido es: ")
							.append(tiempoMaximoBusquedaPermitido)
							.append(" y se demoro: ")
							.append(tiempoBusqueda)
							.toString();
		
		sendEmail(asunto, mensaje);
				
	}

	
	public void informarFallaEnProcesoAsincronico(ProcesoAsincronico procesoFallido) {
		String asunto = "Falla en proceso asincronico";
		String mensaje = new StringBuilder().append("Se ha producido una falla en la ejecucion de un proceso asincronico.")
							.append("\n\n")
							.append(procesoFallido.toString())
							.toString();
		
		sendEmail(asunto, mensaje);
		
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	
	
}