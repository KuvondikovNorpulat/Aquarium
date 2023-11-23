package uz.kuvondikov.service;

import uz.kuvondikov.RunnableFish;
import uz.kuvondikov.entity.Aquarium;
import uz.kuvondikov.entity.Fish;
import uz.kuvondikov.entity.Location;
import uz.kuvondikov.enums.Gender;
import uz.kuvondikov.exceptions.AquariumOverFlowException;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class AquariumServiceImpl implements AquariumService {
    static final Random RANDOM = new Random();
    private final Aquarium aquarium;
    private final ExecutorService executorService;
    private final Set<Fish> collidedFishes = new HashSet<>();

    public AquariumServiceImpl(Aquarium aquarium, ExecutorService executorService) {
        this.aquarium = aquarium;
        this.executorService = executorService;
    }

    private Location generateLocation() {
        int x = RANDOM.nextInt(aquarium.getWidth());
        int y = RANDOM.nextInt(aquarium.getHeight());
        return new Location(x, y);
    }

    @Override
    public synchronized Fish createNewFish(Gender gender) {
        Fish newFish = new Fish(gender, RANDOM.nextInt(1000, 5000), generateLocation());
        aquarium.getFishes().add(newFish);
        return newFish;
    }

    @Override
    public synchronized String reproduce(Fish fish1, Fish fish2) {
        if (aquarium.getFishes().size() >= aquarium.getCapacity())
            throw new AquariumOverFlowException();

        Fish kind = new Fish(Gender.randomGender(), RANDOM.nextInt(1000, 5000), generateLocation());
        aquarium.getFishes().add(kind);
        executorService.execute(new RunnableFish(aquarium, kind));
        return ("Fish reproduced! => " + kind);
    }

    @Override
    public boolean hasCollidedBefore(Fish fish1, Fish fish2) {
        return collidedFishes.contains(fish1) && collidedFishes.contains(fish2);
    }

    @Override
    public void markCollided(Fish fish1, Fish fish2) {
        collidedFishes.add(fish1);
        collidedFishes.add(fish2);
    }

    @Override
    public void clearCollisions() {
        collidedFishes.clear();
    }

    @Override
    public synchronized void moveFish() {
        List<Fish> fishes = aquarium.getFishes();
        for (Fish fish : fishes) {
            fish.setLocation(generateLocation());
        }
    }
}