package uz.kuvondikov.service;

import uz.kuvondikov.entity.Fish;
import uz.kuvondikov.enums.Gender;

public interface AquariumService {
    Fish createNewFish(Gender gender);

    String reproduce(Fish fish1, Fish fish2);

    boolean hasCollidedBefore(Fish fish1, Fish fish2);

    void markCollided(Fish fish1, Fish fish2);

    void clearCollisions();

    void moveFish();
}
