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

	@Query("SELECT m,r.newMessage FROM Message m left outer join m.recipients r where r.recipient.id=:id or m.sender=:id order by m.creationDate asc")
	Object[] getUserMessages(@Param("id") Long id);

	@Query("SELECT m FROM Message m left outer join m.recipients r where r.newMessage=true and ( r.recipient.id=:id or m.sender=:id ) order by m.creationDate asc")
	List<Message> getNewUserMessages(@Param("id") Long id);

	List<Message> findByToAdminTrueOrderByCreationDateAsc();

	@Modifying
	@Query("update MessageRecipients r set r.newMessage=:flag where r.msg.id=:msgID")
	void updateMessageRecipientReadFlag(@Param("flag") boolean newMessage, @Param("msgID") Long msgID);

	@Query("SELECT m FROM Message m  where m.toAdmin=true and m.newAdminMessage=true and m.sender.id=:userId order by m.creationDate asc")
	List<Message> getNewAdminMessagesForUser(@Param("userId") Long userId);

	@Query("SELECT m FROM Message m left outer join m.recipients r where (r.recipient.id=:userId and  m.sender.id=:adminId) or (m.sender.id=:userId and m.toAdmin=true) order by m.creationDate asc")
	List<Message> getAllMessagesBetweenAdminAndUser(@Param("adminId") Long adminId, @Param("userId") Long userId);

	@Query("SELECT count(m) FROM Message m  where m.toAdmin=true and m.newAdminMessage=true ")
	Long countNewAdminMessages();

	@Query("SELECT count (m) FROM Message m left outer join m.recipients r where r.newMessage=true and ( r.recipient.id=:id or m.sender=:id ) order by m.creationDate asc")
	Long countNewUserMessages(@Param("id") Long id);
}
