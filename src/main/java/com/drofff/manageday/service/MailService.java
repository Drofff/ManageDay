package com.drofff.manageday.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.drofff.manageday.exception.MDException;

@Service
public class MailService {

	private static final String HTML_CONTENT_TYPE = "text/html";

	private final JavaMailSender javaMailSender;

	@Value("${mail.sender}")
	private String senderEmailAddress;

	@Autowired
	public MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void send(String topic, String text, String ... receivers) {
		validateReceiversList(receivers);
		MimeMessage message = buildMessage(topic, text, receivers);
		javaMailSender.send(message);
	}

	private void validateReceiversList(String [] receivers) {
		if(receivers.length == 0) {
			throw new MDException("At least one receiver is required");
		}
	}

	private MimeMessage buildMessage(String topic, String text, String [] receivers) {
		try {
			return buildMimeMessage(topic, text, receivers);
		} catch(MessagingException e) {
			throw new MDException("Mail message error");
		}
	}

	private MimeMessage buildMimeMessage(String topic, String text, String [] receivers) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		mimeMessage.setContent(text, HTML_CONTENT_TYPE);
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		mimeMessageHelper.setFrom(senderEmailAddress);
		mimeMessageHelper.setTo(receivers);
		mimeMessageHelper.setSubject(topic);
		return mimeMessageHelper.getMimeMessage();
	}

}
