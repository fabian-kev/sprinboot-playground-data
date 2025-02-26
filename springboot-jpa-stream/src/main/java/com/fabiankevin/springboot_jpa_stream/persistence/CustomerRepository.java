package com.fabiankevin.springboot_jpa_stream.persistence;

import com.fabiankevin.springboot_jpa_stream.models.CustomerStatus;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.HibernateHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    @QueryHints(value = {
            @QueryHint(name = HibernateHints.HINT_FETCH_SIZE, value = "2000")
    })
    @Query("SELECT c FROM customers c LEFT JOIN FETCH c.cards WHERE c.status='INACTIVE'")
    Stream<CustomerEntity> findAllInactiveInStream();

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE customers SET status=:status WHERE id IN(:ids)")
    void updateStatus(@Param("status") CustomerStatus status, @Param("ids") Set<UUID> ids);
}

