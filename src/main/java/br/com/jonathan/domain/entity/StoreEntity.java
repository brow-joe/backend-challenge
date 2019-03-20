package br.com.jonathan.domain.entity;

import br.com.jonathan.application.dto.StoreDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Entity(name = "store")
public class StoreEntity extends AbstractedEntity {

    public StoreEntity() {
        this(null);
    }

    public StoreEntity(UUID id) {
        super(id);
    }

    @Column(name = "name")
    private String name;

    @Column(name = "address")
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
