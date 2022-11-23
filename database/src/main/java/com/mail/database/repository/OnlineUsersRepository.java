package com.mail.database.repository;


import com.mail.database.domain.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineUsersRepository extends JpaRepository<UserDTO,String> {
}
