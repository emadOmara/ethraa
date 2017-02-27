
package net.pd.ethraa.business;

import org.springframework.scheduling.annotation.Async;

import net.pd.ethraa.common.model.Email;

public interface MailService {

    @Async
    public String send(Email email);

}
