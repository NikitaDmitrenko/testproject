package main.model;

import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class Result {

    private String value;

    private BigInteger count;

    public Result() {
    }

    public Result(String value, BigInteger bigInteger) {
        this.value = value;
        this.count = bigInteger;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BigInteger getBigInteger() {
        return count;
    }

    public void setBigInteger(BigInteger bigInteger) {
        this.count = bigInteger;
    }
}
