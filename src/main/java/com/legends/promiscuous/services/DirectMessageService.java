package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.requests.DirectMessageRequest;
import com.legends.promiscuous.dtos.response.DirectMessageResponse;
import com.legends.promiscuous.dtos.response.FindAllMessagesResponse;

public interface DirectMessageService {

    DirectMessageResponse send(DirectMessageRequest request, Long senderId, Long receiverId);

    FindAllMessagesResponse findMessagesByIds(long sender, long receiver);
}
