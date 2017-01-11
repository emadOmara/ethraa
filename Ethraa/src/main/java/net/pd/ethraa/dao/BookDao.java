package net.pd.ethraa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.Book;
import net.pd.ethraa.common.model.Group;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {

    @Query("select b from Book b where :group member of  b.groups")
    List<Book> findByGroup(@Param("group") Group group);

}
