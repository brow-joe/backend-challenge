package br.com.jonathan.userinterface.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

public abstract class AbstractedRestController<C> {
    private final Class<C> instance;
    private final String version;

    public AbstractedRestController(Class<C> instance, String version) {
        this.instance = instance;
        this.version = version;
    }

    protected C linked() {
        return ControllerLinkBuilder.methodOn(this.instance);
    }

    protected Link withSelf(Object reference) {
        Link self = ControllerLinkBuilder.linkTo(reference).withSelfRel();
        return self.withHref(build(self.getHref()));
    }

    private String build(String source) {
        return StringUtils.replace(source, "${spring.application.version}", version);
    }

}
