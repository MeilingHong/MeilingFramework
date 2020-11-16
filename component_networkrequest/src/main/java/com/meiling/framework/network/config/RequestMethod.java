package com.meiling.framework.network.config;

public enum RequestMethod {
    GET(0, "GET"), POST(1, "POST"), PUT(2, "PUT"), DELETE(3, "DELETE");

    private int index;
    private String method;

    RequestMethod(int index, String get) {
        this.method = get;
        this.index = index;
    }

    public String getMethod() {
        return method;
    }
}
