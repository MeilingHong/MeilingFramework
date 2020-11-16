package com.meiling.framework.app.mvp.environment;

/**
 * Created by marisareimu@126.com on 2020-11-16  10:48
 * project MeilingFramework
 */

public enum EVN {
    PROD(0, "PROD"), DEV(1, "DEV"), TEST(2, "TEST"), PRE(3, "PRE");

    private int value;
    private String name;

    EVN(int i, String prod) {
        this.value = i;
        this.name = prod;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
