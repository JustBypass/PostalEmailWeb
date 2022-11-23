package com.postal.scheduled.repository;

import com.postal.scheduled.entity.ScheduledPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledRepository extends JpaRepository<ScheduledPost,Long> {
}
