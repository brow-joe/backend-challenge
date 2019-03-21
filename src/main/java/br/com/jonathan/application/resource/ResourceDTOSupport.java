package br.com.jonathan.application.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.function.Supplier;

public class ResourceDTOSupport<D> extends ResourceSupport {

    private D content;

    public ResourceDTOSupport(D dto) {
        super();
        this.content = dto;
    }

    public D getContent() {
        return content;
    }

    public void setContent(D content) {
        this.content = content;
    }

    public static <D> ResourceDTOSupport of(D dto, Supplier<Link> supplier) {
        ResourceDTOSupport resource = new ResourceDTOSupport(dto);
        resource.add(supplier.get());
        return resource;
    }
}
