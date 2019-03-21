package br.com.jonathan.domain.pattern.specification;

import java.io.Serializable;

public class Error implements Serializable {

    private Integer code;
    private String error;

    public Error() {
        this(null, null);
    }

    public Error(Integer code, String error) {
        super();
        this.code = code;
        this.error = error;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static Error of(Integer code, String error) {
        return new Error(code, error);
    }

}
