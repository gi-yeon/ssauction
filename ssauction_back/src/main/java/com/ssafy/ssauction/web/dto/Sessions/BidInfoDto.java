package com.ssafy.ssauction.web.dto.Sessions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BidInfoDto implements Comparable<BidInfoDto>{
    String bidder;
    String currentPrice;

    @Override
    public int compareTo(@NotNull BidInfoDto o) {
        return Integer.parseInt(o.currentPrice) - Integer.parseInt(this.currentPrice);
    }
}
