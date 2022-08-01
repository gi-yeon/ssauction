package com.ssafy.ssauction.domain.board;


import com.ssafy.ssauction.domain.users.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface BoardRepository extends JpaRepository<Board,Long> {


}
