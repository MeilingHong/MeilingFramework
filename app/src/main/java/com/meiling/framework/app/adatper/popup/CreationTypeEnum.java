package com.meiling.framework.app.adatper.popup;

/**
 * Created by marisareimu@126.com on 2020-12-18  13:43
 */
public enum CreationTypeEnum {
    ALL("all", "全部类型"),
    AVI("avi", "视频作品"),
    TEXT("text", "文字作品"),
    PNG("png", "图片作品"),
    AUDIO("audio", "音频作品"),
    OTHER("other", "其他作品");

    private String name;
    private String desc;

    CreationTypeEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
