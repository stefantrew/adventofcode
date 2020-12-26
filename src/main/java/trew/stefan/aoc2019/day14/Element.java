package trew.stefan.aoc2019.day14;

import lombok.Data;

@Data
class Element {

    String id;
    int quantity;

    Element(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return quantity + id;
    }
}
