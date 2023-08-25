package com.legends.promiscuous.dtos.requests;

import com.legends.promiscuous.enums.Reaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MediaReactionRequest {
    private Reaction reaction;
    private Long mediaId;
    private Long userId;
}
