package uz.kuvondikov.entity;

import lombok.Getter;
import lombok.Setter;
import uz.kuvondikov.enums.Gender;

@Getter
@Setter
public class Fish {

    private Gender gender;

    private long lifeTime;

    private Location location;

    public Fish() {
    }

    public Fish(Gender gender, long lifeTime, Location location) {
        this.gender = gender;
        this.lifeTime = (lifeTime < 0) ? -lifeTime : lifeTime;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Fish{" + "gender=" + gender + ", location(" + location.getX() + ":" + location.getY() + ")" + "lifetime=>" + lifeTime + '}';
    }
}