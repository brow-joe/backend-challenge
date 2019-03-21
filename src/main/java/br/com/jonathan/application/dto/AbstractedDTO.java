package br.com.jonathan.application.dto;

import br.com.jonathan.domain.entity.AbstractedEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public abstract class AbstractedDTO implements Serializable {

    @JsonIgnore
    public abstract <D extends AbstractedEntity> D toEntity();

    @JsonIgnore
    public abstract <D extends AbstractedEntity> D toEntity(String id);

}
