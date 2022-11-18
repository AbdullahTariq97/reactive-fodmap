package uk.sky.fodmap.functional.dto;

import org.apache.http.Header;

import java.util.List;

public class Response {

    private int status;
    private String body;
    private List<Header> headers;

    public Response(int status, String body, List<Header> headers) {
        this.status = status;
        this.body = body;
        this.headers = headers;
    }

    public Response() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }
}
