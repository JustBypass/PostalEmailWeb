package com.mail.cache.database.query;




import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mail.cache.CacheMessages;
import org.mail.cache.domain.Envelope;
import org.mail.cache.repository.CachedMessagesRepository;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import javax.naming.directory.SearchResult;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
@SpringBootTest(classes = CacheMessages.class)
@ActiveProfiles("test")
public class QuerySearchTest {

    @Autowired
    private CachedMessagesRepository cachedMessagesRepository;
    private final static String TEXT1 = "java";
    private final static String TEXT2 = " below is an example Java program to save an arraylist of Employee objects. Employee class implement Serializable interface";

    private static final String TEST_PHRASE = "Java program is perfect";

    @PersistenceContext
    private EntityManager entityManager;


    private final static String HOST = "email";
    @BeforeEach
    public void fill_table() throws InterruptedException {
        List<Envelope> envelopes = new ArrayList<>();

        envelopes.add( Envelope.builder()
                .bodyText(TEXT1)
                .host(HOST)
                .subject("edard.stark@winterfell.com")
                .build());
        envelopes.add(Envelope.builder()
                .bodyText(TEXT2)
                .host(HOST)
                .subject("edard.stark@winterfell.com")
                .build());

        cachedMessagesRepository.saveAll(envelopes);
    }

    @Test
    @Transactional
    public void check_envelope_global_search_criteria() throws Exception {

        var l = cachedMessagesRepository.findAll();
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Envelope.class)
                .get();
        entityManager.getCriteriaBuilder();
        org.apache.lucene.search.Query luceneQuery = queryBuilder
                .keyword()
                .onField("subject")
                .matching("edard.stark@winterfell.com")
                .createQuery();
        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Envelope.class);
        var SIZE = jpaQuery.getResultSize();
        System.out.println(SIZE);
        System.out.println("Good");
    }

}
