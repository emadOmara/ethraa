package net.pd.ethraa.common;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

import net.pd.ethraa.common.model.Account;

public class CommonUtil {

    public static String generateToken(Account account) {

	String key = account.getEmail() + new Date().toString() + UUID.randomUUID().toString();
	String token = DigestUtils.sha256Hex(key);
	return token;

    }

    public static boolean isEmpty(Long id) {
	return id == null ? true : id > 0;
    }
}
