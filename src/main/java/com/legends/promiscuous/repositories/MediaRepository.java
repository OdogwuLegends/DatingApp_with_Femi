package com.legends.promiscuous.repositories;

import com.legends.promiscuous.models.Media;
import com.legends.promiscuous.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media,Long> {
    boolean existsByUser(User user);

    @Query("SELECT m FROM Media m WHERE m.user = :user AND m.isLike = true")
    Optional<Media> findMediaByUserAndIsLikeIsTrue(User user);
    Optional<Media> findByUser(User user);
}
