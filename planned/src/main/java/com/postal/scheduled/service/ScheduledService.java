package com.postal.scheduled.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail.global.clients.online.ClientDTO;
import com.mail.global.dto.Post;
import com.postal.scheduled.entity.ScheduledPost;
import com.postal.scheduled.repository.ScheduledRepository;
import com.postal.scheduled.resources.ScheduledController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScheduledService {
    @Autowired
    private ScheduledRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    private final Logger log = LoggerFactory.getLogger(ScheduledService.class);
    public List<Map.Entry<ClientDTO, List<Post>>> getPreparedPosts(ClientDTO[] clients) throws IOException {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScheduledPost> query = cb.createQuery(ScheduledPost.class);

        Root<ScheduledPost> post = query.from(ScheduledPost.class);

        Predicate condition = cb.lessThanOrEqualTo(post.get("dateToSend"), LocalDate.now());

        query.where(condition);

        com.mail.global.dto.Post[] postList =  new ObjectMapper()
                .readValue(objectMapper
                                .writeValueAsBytes( entityManager
                                        .createQuery(query)
                                        .getResultStream()
                                        .map(ScheduledPost::getPost)
                                        .collect(Collectors.toList())
                                        .toArray(com.postal.scheduled.entity.Post[]::new)),
                        com.mail.global.dto.Post[].class);

        return  Arrays.stream(clients).map(
                client->{
                    var l = Arrays.stream(postList)
                            .filter(p->p.getFrom().equals(client.getEmail()))
                            .collect(Collectors.toList());
                    return Map.entry(client,l);
                }
        ).collect(Collectors.toList());
    }
}
