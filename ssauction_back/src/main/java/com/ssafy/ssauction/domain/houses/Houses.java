package com.ssafy.ssauction.domain.houses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.ssauction.domain.items.Items;
import com.ssafy.ssauction.domain.resultOrder.ResultOrders;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Houses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_no")
    private Long houseNo;

    @Column(name = "house_title", length = 50, nullable = false)
    private String houseTitle;

    @Column(name = "house_auc_time", nullable = false)
    private Timestamp houseAucTime;

    @Column(name="house_desc",length = 200)
    private String houseDesc;

    @Column(name="house_reg_time")
    private Timestamp houseRegTime;

    @Column(name="house_update_time")
    private Timestamp houseUpdateTime;

    @Column(name = "house_status",nullable = false)
    private int houseStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(name = "house_item_no")
    private Items item;

    @Setter
    @JsonIgnore
    @OneToMany(mappedBy = "house",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ResultOrders>  results=new ArrayList<>();

    @Builder
    public Houses(String houseTitle, Timestamp houseAucTime, String houseDesc, int houseStatus, Items item) {
        this.houseTitle = houseTitle;
        this.houseAucTime = houseAucTime;
        this.houseDesc = houseDesc;
        this.houseRegTime = new Timestamp(System.currentTimeMillis());
        this.houseStatus = houseStatus;
        this.item = item;
    }
}
