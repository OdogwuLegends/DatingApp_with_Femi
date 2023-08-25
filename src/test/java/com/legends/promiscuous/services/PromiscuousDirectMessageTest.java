package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.requests.DirectMessageRequest;
import com.legends.promiscuous.dtos.response.DirectMessageResponse;
import com.legends.promiscuous.dtos.response.FindAllMessagesResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
@Sql(scripts = {"/db/insert.sql"})
class PromiscuousDirectMessageTest {
    @Autowired
    DirectMessageService directMessageService;

    @Test
    public void testThatAUserOfTheAppCanSendAndReceiveMessages(){
        DirectMessageRequest request = new DirectMessageRequest();
        request.setMessage("How are you doing sis");
        DirectMessageResponse response = directMessageService.send(request, 507L, 503L);

        DirectMessageRequest request2 = new DirectMessageRequest();
        request2.setMessage("Doing well, thank you... Have you met with the group?");
        DirectMessageResponse response2 = directMessageService.send(request2, 503L, 507L);

        assertThat(response).isNotNull();
        assertThat(response2).isNotNull();
        assertNotNull(response.getMessage());
    }

    @Test
    public void testThatAtRuntimeAllExchangesBetweenUsersAreFetched(){
        FindAllMessagesResponse foundMessages = directMessageService.findMessagesByIds(503L, 507L);

        log.info("Messages found for both users--->{}", foundMessages);
        assertThat(foundMessages).isNotNull();

    }
}