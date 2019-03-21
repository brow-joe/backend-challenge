package br.com.jonathan.application.resource;

import br.com.jonathan.domain.entity.AbstractedEntity;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.function.Supplier;

public class ResourceDataSupport<E extends AbstractedEntity> extends ResourceSupport {

    private ResourceContent content;

    public ResourceDataSupport(E entity) {
        super();
        this.content = ResourceContent.of(entity);
    }

    public ResourceContent getContent() {
        return content;
    }

    public void setContent(ResourceContent content) {
        this.content = content;
    }

    public static <E extends AbstractedEntity> ResourceDataSupport of(E entity, Supplier<Link> supplier) {
        ResourceDataSupport resource = new ResourceDataSupport(entity);
        resource.add(supplier.get());
        return resource;
    }
}
