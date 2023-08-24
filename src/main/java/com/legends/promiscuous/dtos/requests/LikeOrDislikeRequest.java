package com.legends.promiscuous.dtos.requests;

import com.legends.promiscuous.enums.Reaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeOrDislikeRequest {
    private Reaction reaction;
}
