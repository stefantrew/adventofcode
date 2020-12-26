package trew.stefan.aoc2019.day14;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class Substitute {

    String id;
    int quantity;

    List<Element> requirements = new ArrayList<>();

    Substitute(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public void addRequirement(Element ele2) {
        requirements.add(ele2);
    }
}
