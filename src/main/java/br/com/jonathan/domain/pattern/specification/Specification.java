package br.com.jonathan.domain.pattern.specification;

import java.util.function.Predicate;

public interface Specification<D> {

    public Predicate<D> predicate();

    public Error getError();

}