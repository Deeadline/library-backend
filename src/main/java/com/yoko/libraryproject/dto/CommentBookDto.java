package com.yoko.libraryproject.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class CommentBookDto {

    @NonNull
    private String comment;
}
