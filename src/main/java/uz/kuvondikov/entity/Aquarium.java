package uz.kuvondikov.entity;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@ToString

public class Aquarium {
    private final Random random = new Random();
    private final List<Fish> fishes = new CopyOnWriteArrayList<>();
    private int capacity;
    private int width;
    private int height;

    public Aquarium() {
    }

    public Aquarium(int capacity, int width, int height) {
        this.capacity = (capacity < 0) ? -capacity : capacity;
        this.width = (width < 0) ? -width : width;
        this.height = (height < 0) ? -width : width;
    }
}
