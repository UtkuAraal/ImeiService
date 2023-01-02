package com.hub.imei.repo;

import com.hub.imei.entity.AppUser;
import com.hub.imei.entity.ImeiNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImeiNumberRepo extends JpaRepository<ImeiNumber, Long> {
    ImeiNumber findByImei(String imei);
    List<ImeiNumber> findAllByUser(AppUser user);

    @Modifying
    @Query("SELECT count(u) FROM ImeiNumber u")
    Integer countAll();
    List<ImeiNumber> findAllByBanned(boolean statue);

    @Modifying
    @Query("UPDATE ImeiNumber SET banned = true WHERE imei = ?1")
    void blockImei(String imei);

    @Modifying
    @Query("UPDATE ImeiNumber SET searchCount = searchCount + 1 WHERE imei = ?1")
    void increaseCount(String imei);

    Long countAllByBanned(boolean statue);
}
