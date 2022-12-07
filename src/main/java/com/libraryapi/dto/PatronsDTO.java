package com.libraryapi.dto;

import com.libraryapi.domain.Patrons;

import java.util.Objects;

public class PatronsDTO {
    public Integer patron_id;
    public String name;
    public Double fines;

    public PatronsDTO() {}

    public PatronsDTO(Patrons entity) {
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

        if (other instanceof PatronsDTO dto) {
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
