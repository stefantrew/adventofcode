package trew.stefan.aoc2015;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class Molecule {

    List<Element> elements = new ArrayList<>();
    Integer hash = 0;
    String str = "";
    int tiCount = 0;
    int caCount = 0;
    boolean stop = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Molecule molecule = (Molecule) o;
        return Objects.equals(elements, molecule.elements);
    }

    @Override
    public String toString() {
        if (str.equals("") && elements.size() > 0) {
            hash();
        }
        return str;
    }

    @Override
    public int hashCode() {
        return hash == null ? Objects.hash(elements) : hash;
    }

    public void hash() {
        hash = Objects.hash(elements);
        StringBuilder sb = new StringBuilder();

        int streak = 0;
        Element lastEle = null;

        for (Element e : elements) {
            if (e.label.equals("Ti")) {
                tiCount++;
            } else if (e.label.equals("Ca")) {
                caCount++;
            }
            sb.append(e.toString());

            if (lastEle == null) {
                streak = 1;
            } else if (lastEle == e) {
                streak++;
            }
            lastEle = e;

            if (streak == 10) {
                stop = true;
                break;
            }

            if (tiCount > 40 || caCount > 70) {
                stop = true;
                break;
            }
        }
        str = sb.toString();
    }

    public Molecule clone() {
        Molecule clone = new Molecule();
        clone.elements.addAll(elements);
        clone.hash();
        return clone;
    }

    public void replaceElement(int i, Molecule output) {
        elements.remove(i);
        elements.addAll(i, output.elements);
        hash();
    }
}
