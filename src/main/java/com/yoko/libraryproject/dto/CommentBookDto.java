package com.yoko.libraryproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentBookDto {

    @NonNull
    private Long bookId;

    private String comment;

    private String username;
}
