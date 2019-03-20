package br.com.jonathan.application.dto;

import br.com.jonathan.domain.entity.StoreEntity;

import java.util.UUID;

public class StoreDTO extends AbstractedDTO {

    private String name;
    private String address;

    public StoreDTO() {
        this(null, null);
    }

    public StoreDTO(String name, String address) {
        super();
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public StoreDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public StoreDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    @Override
    public StoreEntity toEntity() {
        return StoreEntity.of(name, address);
    }

    @Override
    public StoreEntity toEntity(String id) {
        return StoreEntity.of(UUID.fromString(id), name, address);
    }

    public static StoreDTO of(String name, String address) {
        return new StoreDTO(name, address);
    }

}
