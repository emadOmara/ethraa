package net.pd.ethraa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.Message;

@Repository
public interface MessageDao extends CrudRepository<Message, Long> {

    @Query("SELECT m,r.newMessage FROM Message m left outer join m.recipients r where r.recipient.id=:id or m.sender=:id) order by m.creationDate desc")
    Object[] getUserMessages(@Param("id") Long id);

    @Query("SELECT m FROM Message m left outer join m.recipients r where r.newMessage=true and ( r.recipient.id=:id or m.sender=:id ) order by m.creationDate desc")
    List<Message> getNewUserMessages(@Param("id") Long id);

    List<Message> findByToAdminTrueOrSenderIdOrderByCreationDateDesc(Long senderId);

    List<Message> findByToAdminTrueAndNewAdminMessageTrueOrderByCreationDateDesc();

    @Modifying
    @Query("update MessageRecipients r set r.newMessage=:flag where r.msg.id=:msgID")
    void updateMessageReadFlag(@Param("flag") boolean newMessage, @Param("msgID") Long msgID);
}
