package org.mail.cache.repository;


import org.mail.cache.domain.Envelope;
import org.mail.cache.domain.PaginationDTO;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CachedMessagesRepository extends JpaRepository<Envelope,Integer> {
    default List<Envelope> pagination(PaginationDTO paginationDTO, String email){
        Pageable pagination = PageRequest.of(paginationDTO.getPage(),paginationDTO.getPerPage());// email todo
        return this.findAll(pagination).toList();
    }
}
