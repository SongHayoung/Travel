package com.Travel.biz.Feed.Dto;

import com.Travel.biz.Feed.VO.Feed;
import com.Travel.biz.Feed.VO.FeedImages;
import lombok.Builder;

@Builder
public class FeedImagesDto extends FeedImages {
    private byte[] images;
}
