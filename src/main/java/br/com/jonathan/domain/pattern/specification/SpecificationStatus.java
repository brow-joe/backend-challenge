package br.com.jonathan.domain.pattern.specification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpecificationStatus implements Serializable {

    private boolean satisfied = true;
    private List<Error> errors = new ArrayList<>();

    public List<Error> getErrors() {
        return errors;
    }

    public void addErrors(Error error) {
        errors.add(error);
    }

    public void dissatisfy() {
        this.satisfied = false;
    }

    public boolean isSatisfied() {
        return satisfied;
    }

    public String getJsonErrors() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(errors);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}