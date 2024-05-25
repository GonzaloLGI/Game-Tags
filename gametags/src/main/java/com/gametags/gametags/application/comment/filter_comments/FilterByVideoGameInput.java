package com.gametags.gametags.application.comment.filter_comments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FilterByVideoGameInput {
    private String videogame;
}
