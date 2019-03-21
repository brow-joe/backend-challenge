package br.com.jonathan.domain.entity;

import br.com.jonathan.application.dto.StoreDTO;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity(name = "store")
public class StoreEntity extends AbstractedEntity {

    public StoreEntity() {
        this(null);
    }

    public StoreEntity(UUID id) {
        super(id);
    }

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    public String getName() {
        return name;
    }

    public StoreEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public StoreEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public boolean isValid() {
        return StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(address);
    }

    @Override
    public StoreDTO toDTO() {
        return StoreDTO.of(name, address);
    }

    public static StoreEntity of(String name, String address) {
        return of(null, name, address);
    }

    public static StoreEntity of(UUID id, String name, String address) {
        return new StoreEntity(id)
                .setName(name)
                .setAddress(address);
    }
}
