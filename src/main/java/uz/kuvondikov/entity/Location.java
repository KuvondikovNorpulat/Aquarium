package uz.kuvondikov.entity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Location {
    private int x;
    private int y;

    public Location() {
    }

    public Location(int x, int y) {
        this.x = (x < 0) ? -x : x;
        this.y = (y < 0) ? -y : y;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null) return false;

        Location location = (Location) obj;
        return x == location.x && y == location.y;
    }

    public void setX(int x) {
        this.x = (x < 0) ? -x : x;
    }

    public void setY(int y) {
        this.y = (y < 0) ? -y : y;
    }
}
