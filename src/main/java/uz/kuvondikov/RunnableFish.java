package uz.kuvondikov;

import uz.kuvondikov.entity.Aquarium;
import uz.kuvondikov.entity.Fish;

public class RunnableFish implements Runnable {
    private final Aquarium aquarium;
    private final Fish fish;

    public RunnableFish(Aquarium aquarium, Fish fish) {
        this.aquarium = aquarium;
        this.fish = fish;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(fish.getLifeTime());
            aquarium.getFishes().remove(fish);
            System.out.println("Fish o'ldi! =>" + fish);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
