package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Commandes} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommandesDTO implements Serializable {

    private String id;

    private Long quantity;

    private String customerRef;

    private String productRef;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(String customerRef) {
        this.customerRef = customerRef;
    }

    public String getProductRef() {
        return productRef;
    }

    public void setProductRef(String productRef) {
        this.productRef = productRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandesDTO)) {
            return false;
        }

        CommandesDTO commandesDTO = (CommandesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commandesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandesDTO{" +
            "id='" + getId() + "'" +
            ", quantity=" + getQuantity() +
            ", customerRef='" + getCustomerRef() + "'" +
            ", productRef='" + getProductRef() + "'" +
            "}";
    }
}
