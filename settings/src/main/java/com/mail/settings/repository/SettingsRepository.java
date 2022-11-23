package com.mail.settings.repository;

import com.mail.settings.entity.SettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<SettingsEntity,String> {
}
