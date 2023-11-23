package uz.kuvondikov;

import uz.kuvondikov.entity.Aquarium;
import uz.kuvondikov.entity.Fish;
import uz.kuvondikov.enums.Gender;
import uz.kuvondikov.exceptions.AquariumOverFlowException;
import uz.kuvondikov.service.AquariumServiceImpl;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Main {
    private static final Random RANDOM = new Random();
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        run();
    }

    private static void run() {

        Aquarium aquarium = buildAquarium();
        AquariumServiceImpl aquariumServiceImpl = new AquariumServiceImpl(aquarium, executorService);

        int maleFishNumber = RANDOM.nextInt(aquarium.getCapacity() / 3);
        int femaleFishNumber = RANDOM.nextInt(aquarium.getCapacity() / 3);

        IntStream.range(0, maleFishNumber).mapToObj(i -> aquariumServiceImpl.createNewFish(Gender.MALE)).forEach(newFish -> {
            System.out.println("New fish added ->" + newFish);
            executorService.execute(new RunnableFish(aquarium, newFish));
        });

        IntStream.range(0, femaleFishNumber).mapToObj(i -> aquariumServiceImpl.createNewFish(Gender.FEMALE)).forEach(newFish -> {
            System.out.println("New fish added ->" + newFish);
            executorService.execute(new RunnableFish(aquarium, newFish));
        });

        System.out.println("\nMale : " + maleFishNumber + " Female : " + femaleFishNumber);

        List<Fish> fishList = aquarium.getFishes();
        try {
            while (!fishList.isEmpty()) {

                fishList.forEach(fish1 -> fishList.stream().filter(fish2 -> !aquariumServiceImpl.hasCollidedBefore(fish1, fish2) && !fish1.getGender().equals(fish2.getGender()) && fish1.getLocation().equals(fish2.getLocation())).forEach(fish2 -> {
                    System.out.println("-*-*- -*-*- -*-*- -*-*- -*-*- -*-*- -*-*- -*-*-");
                    System.out.println(fish1 + " and " + fish2 + " met");
                    System.out.println(aquariumServiceImpl.reproduce(fish1, fish2));
                    aquariumServiceImpl.markCollided(fish1, fish2);
                }));
                aquariumServiceImpl.clearCollisions();

                Thread.sleep(1000);
                aquariumServiceImpl.moveFish();

                System.out.println("\n-*-*- -*-*- The fish changed their place -*-*- -*-*-");
                System.out.println("Number of fish in the aquarium : " + aquarium.getFishes().size());
            }
            logger.info("There are no live fish left in the aquarium!!!");
        } catch (AquariumOverFlowException | InterruptedException e) {
            logger.info(e.getMessage());
        } finally {
            executorService.shutdown();
        }
    }

    private static Aquarium buildAquarium() {
        System.out.print("Enter the width of the aquarium:");
        int width = SCANNER.nextInt();

        System.out.print("Enter the height of the aquarium:");
        int height = SCANNER.nextInt();

        System.out.print("Enter the capacity of the aquarium:");
        int capacity = SCANNER.nextInt();
        return new Aquarium(capacity, width, height);
    }
}