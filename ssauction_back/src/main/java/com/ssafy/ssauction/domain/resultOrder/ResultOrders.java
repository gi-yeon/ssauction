package com.ssafy.ssauction.domain.resultOrder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.ssauction.domain.houses.Houses;
import com.ssafy.ssauction.domain.users.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class ResultOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "order_price")
    private int orderPrice;

    @Column(name="order_reg_time")
    private Timestamp orderRegTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house")
    private Houses house;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidder")
    private Users bidder;

    @Builder
    public ResultOrders(int orderPrice, Houses house, Users bidder) {
        this.orderPrice = orderPrice;
        this.orderRegTime = new Timestamp(System.currentTimeMillis());
        this.house = house;
        this.bidder = bidder;
    }
}
