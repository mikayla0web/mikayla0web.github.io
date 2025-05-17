package edu.miracosta.cs112.finalproject.finalproject;

import java.util.*;


public class ActionsAndEvents {
        private final Random random = new Random();
        private String currentEvent;


        private final Map<String, List<String>> eventCollections = Map.of(
                "worker", Arrays.asList(
                        "There is a pretty flower in the window, would you like to get a closer look?",
                        "The nectar looks very tempting, would you like to sneak a little taste?",
                        "The queen has requested all worker bees to the royal room, would you like to attend?",
                        "A bear paw is attacking the hive! Would you like to attack?",
                        "The sky is looking a bit cloudy... do you still want to go to work?",
                        "The larvae are looking a little hungry, would you like to feed them royal jelly?",
                        "Winter is approaching and food is scarce, would you like to kick the drones out of the hive?"
                ),
                "drone", Arrays.asList(
                        "The nectar looks very tempting, would you like to sneak a little taste?",
                        "The queen has been working very hard today, would you like to offer the queen a snack?",
                        "The queen is only mating with five drones today, would you like to volunteer?",
                        "The queen declared no work today, would you like to explore the outdoors?",
                        "The queen seems to be checking you out from across the room, would you like to wink at her?",
                        "The worker bees seems to be surrounding the drone room, would you like to sneak out for a minute?",
                        "The worker bees left pollen all over the floor! Would you like to sweep it up?"
                ),
                "queen", Arrays.asList(
                        "Laying eggs is a lot of work, would you like to ask your servant for a drink?",
                        "The nectar looks very tempting, would you like to sneak a little taste?",
                        "The weather is beautiful today! Would you like to announce a day off to the bees?",
                        "A drone bee asked for extra food rations this week, would you like to punish him?",
                        "A bear is attacking the hive! Do you want to assemble the worker bees to attack?",
                        "Winter is approaching and food is scarce, would you like to kick the drones out of the hive?",
                        "The bees are getting worried for the queen, would you like to make a public appearance to reassure your subjects?"
                )
        );

        // Tracks available events for each bee type
        private final Map<String, List<String>> availableEvents = new HashMap<>();

    public ActionsAndEvents() {
        // Initialize available events for each bee type
        for (String beeType : eventCollections.keySet()) {
            availableEvents.put(beeType, new ArrayList<>(eventCollections.get(beeType)));
            Collections.shuffle(availableEvents.get(beeType));
        }
    }

    public String generateEvent(String beeType) {
        beeType = beeType.toLowerCase();

        // Validate bee type
        if (!availableEvents.containsKey(beeType)) {
            return "Unknown Bee Type.";
        }

        // Refill events if none left
        if (availableEvents.get(beeType).isEmpty()) {
            availableEvents.put(beeType, new ArrayList<>(eventCollections.get(beeType)));
            Collections.shuffle(availableEvents.get(beeType));
        }

        // Remove and return a random event
        currentEvent = availableEvents.get(beeType).remove(0);
        return currentEvent;
    }

    public String handleEvent(String beeType, boolean userChoice, String currentEvent) {
        return switch (beeType.toLowerCase()) {
            case "worker" -> handleWorkerBeeEvent(userChoice, currentEvent);
            case "drone" -> handleDroneBeeEvent(userChoice, currentEvent);
            case "queen" -> handleQueenBeeEvent(userChoice, currentEvent);
            default -> "Unknown Bee Type.";
        };
    }

        private String handleWorkerBeeEvent(boolean userChoice, String currentEvent) {
            return switch (currentEvent) {
                case "There is a pretty flower in the window, would you like to get a closer look?" ->
                        userChoice ? "Game over. The flower was plastic and you were attacked by a fly swatter." :
                                "Another bee approached and was swiftly smooshed with a fly swatter.";
                case "The nectar looks very tempting, would you like to sneak a little taste?" ->
                        userChoice ? "Totally worth the risk.": "Maybe one day";
                case "The queen has requested all worker bees to the royal room, would you like to attend?" ->
                        "She gave everybody in attendance an extra ration of food!";
                case "A bear paw is attacking the hive! Would you like to attack?" ->
                        userChoice ? "Game over. You stung the bear and saved the hive! Unfortunately, your stinger has been ripped from your body." :
                                "The bear was defeated but we lost a lot of friends during the fight.";
                case "The sky is looking a bit cloudy... do you still want to go to work?" ->
                        userChoice ? "Game over. A raindrop shot you down!" :
                                "Good choice! The rain took down some bees today.";
                case "The larvae are looking a little hungry, would you like to feed them royal jelly?" ->
                        "I wonder what royal jelly tastes like.";
                case "Winter is approaching and food is scarce, would you like to kick the drones out of the hive?" ->
                        "It was for the better.";
                default -> "Unknown Worker Event.";
            };
        }

        private String handleDroneBeeEvent(boolean userChoice, String currentEvent) {
            return switch (currentEvent) {
                case "The nectar looks very tempting, would you like to sneak a little taste?" ->
                        userChoice ? "Game over. You were caught by the guards and publicly executed for treason!" :
                                "Your self-control might have saved your life.";
                case "The queen has been working very hard today, would you like to offer the queen a snack?" ->
                        userChoice ? "Game over. The queen was displeased and had you buried alive in honey" :
                                "Never bother the queen.";
                case "The queen is only mating with five drones today, would you like to volunteer?" ->
                        userChoice ? "The hive salutes you for your loyalty.": "Time to relax.";
                case "The queen declared no work today, would you like to explore the outdoors?" ->
                        userChoice ? "Game over. You only lasted 3 minutes in the wild before getting run over by a tricycle." :
                                "You stayed inside the hive where it is safe";
                case "The queen seems to be checking you out from across the room, would you like to wink at her?" ->
                        userChoice ? "Game over. The queen has you executed for humiliating her." :
                                "Good thing you didn't respond, the queen was looking at the bee behind you.";
                case "The worker bees seems to be surrounding the drone room, would you like to sneak out for a minute?" ->
                        userChoice ? "Good timing! It seems the other drones were evicted due to limited food. You can stay hidden and feed on the jellies you've stashed.":
                            "Game over. It seems the queen did her annual winter eviction. You will be missed";
                case "The worker bees left pollen all over the floor! Would you like to sweep it up?" ->
                    userChoice ? "The worker bees returned and thanked you for collecting it for them." :
                            "Game over. The hive executed you for not being a productive member of society";
                default -> "Unknown Drone Event.";
            };
        }

        private String handleQueenBeeEvent(boolean userChoice, String currentEvent) {
            return switch (currentEvent) {
                case "Laying eggs is a lot of work, would you like to ask your servant for a drink?" ->
                        userChoice  ? "Game over. The servant poisoned you!" : "They probably poisoned it anyways.";
                case "The nectar looks very tempting, would you like to sneak a little taste?" ->
                        "Silly bee! You're queen, you never have to 'sneak a taste'.";
                case "The weather is beautiful today! Would you like to announce a day off to the bees?" ->
                        userChoice ? "The bees are thankful for your kindness.":
                        "This honey is not gonna make itself!";
                case "A drone bee asked for extra food rations this week, would you like to punish him?" ->
                        "Being a queen is all about the tough decisions.";
                case "A bear is attacking the hive! Do you want to assemble the worker bees to attack?" ->
                        userChoice ? "We lost many workers along the way, but at least the hive is safe.":
                        "Game over. Everyone perished.";
                case "Winter is approaching and food is scarce, would you like to kick the drones out of the hive?" ->
                        userChoice ? "They will be missed." : "Game over. The hive starved to death.";
                case "The bees are getting worried for the queen, would you like to make a public appearance to reassure your subjects?" ->
                        userChoice ? " Your subjects were pleased to see you.": "Game over! Your subjects revolted.";
                default -> "Unknown Queen Event.";
            };
        }
    }