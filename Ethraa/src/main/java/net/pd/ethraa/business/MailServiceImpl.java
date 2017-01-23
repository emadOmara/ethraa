
package net.pd.ethraa.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;

import net.pd.ethraa.common.model.Email;

@Controller
public class MailServiceImpl implements MailService {

    @Autowired
    private MailSender mailSender;

    static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    private SimpleMailMessage templateMessage;

    @Value("${send.from.email}")
    private String fromEmail;

    @Override

    public String send(Email email) {
	logger.debug("Start sending mail for {}", email.getTo());
	String response = "OK";
	templateMessage = new SimpleMailMessage();

	templateMessage.setSubject(email.getSubject());
	templateMessage.setFrom(fromEmail);
	templateMessage.setTo(email.getTo());

	SimpleMailMessage msg = new SimpleMailMessage(templateMessage);
	msg.setText(email.getBody());

	try {
	    mailSender.send(msg);
	} catch (MailException ex) {
	    response = "NO_OK";
	    logger.error(ex.getMessage(), ex);
	}
	logger.debug("End sending mail for {}", email.getTo());
	return response;
    }

}
