package br.com.jonathan.domain.entity;

import br.com.jonathan.application.dto.AbstractedDTO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractedEntity implements Serializable {

    public AbstractedEntity(UUID id) {
        super();
        this.id = id;
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    public UUID getId() {
        return id;
    }

    public AbstractedEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    @Transient
    public abstract <D extends AbstractedDTO> D toDTO();

}
