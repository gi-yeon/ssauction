package com.ssafy.ssauction.domain.posts;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @Test
    public void searchBoard() {
        String title = "테스트 게시글";
        String content = "테스트 본문";

        //save
        postsRepository.save(Posts.builder().title(title).content(content).author("giyeon").build());
        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get(0);

        //check
        Assertions.assertThat(posts.getTitle()).isEqualTo(title);
        Assertions.assertThat(posts.getContent()).isEqualTo(content);

        //delete all
        postsRepository.deleteAll();
    }
}
