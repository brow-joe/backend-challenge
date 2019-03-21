package br.com.jonathan.application.resource;

import br.com.jonathan.application.dto.AbstractedDTO;
import br.com.jonathan.domain.entity.AbstractedEntity;

import java.io.Serializable;
import java.util.Objects;

public class ResourceContent<D extends AbstractedDTO> implements Serializable {
    private String id;
    private D data;

    public ResourceContent() {
        this(null, null);
    }

    public ResourceContent(String id, D data) {
        super();
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public static ResourceContent of(AbstractedEntity entity) {
        if (Objects.isNull(entity)) {
            return new ResourceContent();
        } else {
            return new ResourceContent(entity.getId().toString(), entity.toDTO());
        }
    }
}
