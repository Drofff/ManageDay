package com.drofff.manageday.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.drofff.manageday.configuration.properties.MailServerProperties;

@Configuration
@EnableConfigurationProperties
public class BeansConfiguration {

	@Autowired
	private MailServerProperties mailServerProperties;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(mailServerProperties.getHost());
		javaMailSender.setPort(mailServerProperties.getPort());
		javaMailSender.setUsername(mailServerProperties.getUsername());
		javaMailSender.setPassword(mailServerProperties.getPassword());
		return javaMailSender;
	}

}
