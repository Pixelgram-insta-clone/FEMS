package com.cognizant.FEMS;

import com.cognizant.FEMS.model.Comment;
import com.cognizant.FEMS.model.PageOfItems;
import com.cognizant.FEMS.proxy.CommentProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentProxyTest {

    public static WireMockServer wireMockServer;

    private final Comment comment = new Comment(
            1,
            1,
            "Paul",
            "The Bene Gesserit is an ancient order",
            LocalDate.now()
    );

    private String commentJSON;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public CommentProxy proxy;

    @BeforeAll
    void setup() {
        wireMockServer = new WireMockServer(options().port(8110));
        wireMockServer.start();
    }

    @BeforeEach
    void init() throws JsonProcessingException {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        PageOfItems<Comment> commentsPage = new PageOfItems<>(new PageImpl<>(comments));
        commentJSON = objectMapper.writeValueAsString(commentsPage);
        System.out.println(commentJSON);
        // {"items":[{"id":1,"postId":1,"username":"Paul","body":"The Bene Gesserit is an ancient order","createdOn":{"year":2022,"month":"JUNE","monthValue":6,"dayOfMonth":10,"chronology":{"id":"ISO","calendarType":"iso8601"},"dayOfWeek":"FRIDAY","leapYear":false,"dayOfYear":161,"era":"CE"}}],"hasNext":false,"totalElements":1}
    }

    @Test
    void test_commentProxyReturnsComments_positive(){

        wireMockServer.stubFor(get(urlPathEqualTo("/comments"))
                .withQueryParam("postId", equalTo("1"))
                .withQueryParam("pageNumber", equalTo("0"))
                .withQueryParam("pageSize", equalTo("5"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(commentJSON)));

        String response = "";

        try {
            response = objectMapper.writeValueAsString(proxy.getComments(1, 0, 5));

        } catch (JsonProcessingException e){
            fail();
        }
        System.out.println(response);

        assertThat(response, is(commentJSON));
    }
}

