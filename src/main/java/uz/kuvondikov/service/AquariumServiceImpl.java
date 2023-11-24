package uz.kuvondikov.service;

import uz.kuvondikov.RunnableFish;
import uz.kuvondikov.entity.Aquarium;
import uz.kuvondikov.entity.Fish;
import uz.kuvondikov.entity.Location;
import uz.kuvondikov.enums.Gender;
import uz.kuvondikov.exceptions.AquariumOverFlowException;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public class AquariumServiceImpl implements AquariumService {
    static final Random RANDOM = new Random();
    private final Aquarium aquarium;
    private final ExecutorService executorService;
    private final Map<Fish, Fish> collidedFishesMap = new ConcurrentHashMap<>();

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

        if (aquarium.getFishes().size() >= aquarium.getCapacity())
            throw new AquariumOverFlowException();

        Fish newFish = new Fish(gender, RANDOM.nextInt(1000, 5000), generateLocation());
        aquarium.getFishes().add(newFish);
        return newFish;
    }

    @Override
    public synchronized String procreation() {
        if (aquarium.getFishes().size() >= aquarium.getCapacity())
            throw new AquariumOverFlowException();

        Fish kind = new Fish(Gender.randomGender(), RANDOM.nextInt(1000, 5000), generateLocation());
        aquarium.getFishes().add(kind);
        executorService.execute(new RunnableFish(aquarium, kind));
        return ("Fish born -> " + kind);
    }

    @Override
    public boolean hasCollidedBefore(Fish fish1, Fish fish2) {
        return fish2.equals(collidedFishesMap.get(fish1)) || fish1.equals(collidedFishesMap.get(fish2));

    }

    @Override
    public void markCollided(Fish fish1, Fish fish2) {
        collidedFishesMap.put(fish1, fish2);
    }

    @Override
    public void clearCollisions() {
        collidedFishesMap.clear();
    }

    @Override
    public synchronized void move() {
        List<Fish> fishes = aquarium.getFishes();
        for (Fish fish : fishes) {
            fish.setLocation(generateLocation());
        }
    }
}