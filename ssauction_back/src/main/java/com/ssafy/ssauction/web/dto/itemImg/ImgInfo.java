package com.ssafy.ssauction.web.dto.itemImg;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImgInfo {
    private byte[] img;
    private Long imgNo;

    @Builder
    public ImgInfo(Long imgNo,byte[] img){
        this.imgNo=imgNo;
        this.img=img;
    }
}
