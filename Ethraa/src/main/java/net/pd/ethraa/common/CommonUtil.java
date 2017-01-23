package net.pd.ethraa.common;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Email;

public class CommonUtil {

    public static String generateToken(Account account) {

	String key = account.getEmail() + new Date().toString() + UUID.randomUUID().toString();
	String token = DigestUtils.sha256Hex(key);
	return token;

    }

    public static String generatePassword(int length) {
	return RandomStringUtils.randomNumeric(length);
    }

    public static Email createEmail(String recipient, String subject, String body) {

	Email email = new Email();
	email.setSubject(subject);
	email.setTo(recipient);
	email.setBody(body);
	return email;
    }

    public static boolean isEmpty(Long id) {
	return id == null ? true : id.equals(0l);
    }

    public static boolean isEmpty(List<?> items) {
	return items == null ? true : items.size() == 0;
    }

    public static boolean isEmpty(Object obj) {
	return obj == null;
    }

}
