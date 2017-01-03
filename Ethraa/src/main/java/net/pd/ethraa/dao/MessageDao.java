package net.pd.ethraa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.Message;

@Repository
public interface MessageDao extends CrudRepository<Message, Long> {

    void findBytoAdminTrue();

    // @Query("SELECT m FROM Message m ,Account a where m.sender.id=:userID or
    // (a member of m.recipients and a.id=:userID)")
    // List<Message> getUserMessages(@Param("userID") Long userID);

    @Query("SELECT m FROM Message m ,MessageRecipients r ,Account acc where (m.id=r.recipient and acc.id=r.recipient ) and acc.id=:id or  m.sender=:id")
    List<Message> getUserMessages(@Param("id") Long id);

    List<Message> findByToAdminTrue();
}
