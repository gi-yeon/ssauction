package com.ssafy.ssauction.domain.comments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private long commentNo;

    @Column(name = "comment_content", nullable = false)
    private String commentContent;

    @Column(name = "comment_regdate", nullable = false)
    private String commentRegDate;

    @Column(name = "board_no", nullable = false)
    private Long boardNo;

    @Column(name = "user_no", nullable = false)
    private Long userNo;
}
