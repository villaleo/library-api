package com.libraryapi.dto;

import com.libraryapi.domain.Patron;

import java.util.Objects;

public class PatronDTO {
    public Integer patron_id;
    public String name;
    public Double fines;

    public PatronDTO(Patron entity) {
        patron_id = entity.getPatronId();
        name = entity.getName();
        fines = entity.getFines();
    }

    @Override
    public String toString() {
        var result = "%s with ID #%d".formatted(name, patron_id);
        if (fines != 0.0) {
            result += " with fines of $%.2f".formatted(fines);
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof PatronDTO dto) {
            if (name == null) {
                return false;
            }

            return (
                Objects.equals(patron_id, dto.patron_id) &&
                name.equals(dto.name) && Objects.equals(fines, dto.fines)
            );
        }

        return false;
    }
}
