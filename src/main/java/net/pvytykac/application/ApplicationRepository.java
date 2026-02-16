package net.pvytykac.application;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {

    Page<Application> findAllByOwnerId(String userId, Pageable pageable);

    @Query("SELECT a FROM Application a WHERE a.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    Optional<Application> findByIdForUpdate(String id);

}
