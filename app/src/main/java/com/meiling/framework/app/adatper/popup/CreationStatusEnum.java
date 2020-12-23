package com.meiling.framework.app.adatper.popup;

/**
 * Created by marisareimu@126.com on 2020-12-18  13:43
 */
public enum CreationStatusEnum {
    ALL("all", "全部状态"),
    DEFAULT("d", "存证中"),
    SUCCESS("s", "已存证"),
    FAILURE("f", "存证失败");

    private String name;
    private String desc;

    CreationStatusEnum(String name, String desc) {
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
