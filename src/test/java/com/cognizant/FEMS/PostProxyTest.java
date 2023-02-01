package com.cognizant.FEMS;

import com.cognizant.FEMS.model.Comment;
import com.cognizant.FEMS.model.PageOfItems;
import com.cognizant.FEMS.model.Post;
import com.cognizant.FEMS.model.User;
import com.cognizant.FEMS.proxy.PostProxy;
import com.cognizant.FEMS.proxy.UserProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;


@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostProxyTest {
    public static WireMockServer wireMockServer;

    private Post post = new Post(
            1,
            1,
            "imageUrl",
            "Muaddib the conquerer",
            LocalDate.now()
    );

    private String postJSON;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public PostProxy proxy;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(options().port(8100));
        wireMockServer.start();
    }

    @BeforeEach
    void init() throws JsonProcessingException {
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        PageOfItems<Post> postPage = new PageOfItems<>(new PageImpl<>(posts));
        postJSON = objectMapper.writeValueAsString(postPage);
    }

    @Test
    void test_postProxyReturnsPosts_positive(){
        wireMockServer.stubFor(get(urlPathEqualTo("/posts"))
                .withQueryParam("pageNumber", equalTo("0"))
                .withQueryParam("pageSize", equalTo("5"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(postJSON)));

        String response = "";

        try {
            response = objectMapper.writeValueAsString(proxy.getPosts(0,5));

        } catch (JsonProcessingException e){
            fail();
        }
        System.out.println(postJSON);
        System.out.println(response);
        assertThat(response, is(postJSON));
    }

    @Test
    void test_userProxyNoUserFound_negative(){
        wireMockServer.stubFor(get(urlPathEqualTo("/posts"))
                .withQueryParam("pageNumber", equalTo("-1"))
                .withQueryParam("pageSize", equalTo("5"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(new String())));

        PageOfItems<Post> response = proxy.getPosts(-1, 5);

        assertNull(response);
    }
}
