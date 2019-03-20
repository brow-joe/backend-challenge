package br.com.jonathan.application.resource;

import br.com.jonathan.domain.entity.AbstractedEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ResourceListSupport<E extends AbstractedEntity> extends ResourceSupport {

    private List<ResourceContent> content;

    public ResourceListSupport() {
        this(new ArrayList<>());
    }

    public ResourceListSupport(List<E> entity) {
        super();
        this.content = entity.stream()
                .map(ResourceContent::of)
                .collect(Collectors.toList());
    }

    public List<ResourceContent> getContent() {
        return content;
    }

    public void setContent(List<ResourceContent> content) {
        this.content = content;
    }

    public static <E extends AbstractedEntity> ResourceListSupport of(List<E> entity, Supplier<Link> supplier) {
        ResourceListSupport resource;
        if (CollectionUtils.isNotEmpty(entity)) {
            resource = new ResourceListSupport(entity);
        } else {
            resource = new ResourceListSupport();
        }
        resource.add(supplier.get());
        return resource;
    }

}
