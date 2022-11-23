package org.mail.cache.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail.global.clients.online.ClientDTO;
import com.mail.global.search.input.SearchGlobalFilterDTO;
import com.mail.global.search.input.SearchLocalFilterDTO;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.mail.cache.domain.Envelope;
import org.mail.cache.repository.CachedMessagesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class CachedService {
    @Autowired
    private ObjectMapper objectMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CachedMessagesRepository repository;

    private final Logger logger = LoggerFactory.getLogger(CachedService.class);


    public Map<String,Integer> count(ClientDTO clientDTO){
        return null;
    }

    @Transactional
    public List<Envelope> globalSearch(SearchGlobalFilterDTO searchGlobalFilterDTO, ClientDTO clientDTO){
        if(searchGlobalFilterDTO == null||clientDTO == null){
            throw new NullPointerException(String.format("searchGlobalFilterDTO is null or clientDTO is null in %s",this.getClass().getName()));
        }
        CriteriaBuilder criteriaBuilder =
                entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Envelope.class);
        return null;
    }
    @Transactional
    public List<Envelope> localSearch(SearchLocalFilterDTO localFilterDTO,ClientDTO clientDTO){
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Envelope.class)
                .get();
        entityManager.getCriteriaBuilder();
        org.apache.lucene.search.Query luceneQuery = queryBuilder
                .bool()
                .should(
                        queryBuilder
                            .keyword()
                                .wildcard()
                                     .onFields("subject","bodyText")
                                          .matching(localFilterDTO.getInput())
                                             .createQuery()
                )
                .should(
                        queryBuilder
                                .keyword()
                                .onField("marked")
                                .matching(localFilterDTO.isMarked())
                                .createQuery()
                        )
                .should(
                        queryBuilder.keyword()
                                .onField("hasAttachmet")
                                .matching(localFilterDTO.isContainsAttachment())
                                .createQuery()
                ).createQuery();

        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Envelope.class);
        var envelopesFound = jpaQuery.getResultList();
        logger.info("Found lucene list(local search) in {} are {}",this.getClass().getName(),envelopesFound);
        return envelopesFound;
    }

   public void fillCache(List<com.mail.global.dto.Envelope> envelopes,String folder,String email) throws IOException {
       List<Envelope> envelopeList = objectMapper.readValue( // weak place todo
               objectMapper.writeValueAsBytes(envelopes),
               List.class);
       clearCache(email);
     //  envelopeList.forEach(); todo fill
   }
    private void fillCache(Envelope envelopes){

    }
    private void clearCache(String email){

    }
}
