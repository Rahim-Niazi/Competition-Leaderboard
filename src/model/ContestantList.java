package model;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContestantList {
    private List<Contestant> contestants;

    public ContestantList() {
        contestants = new ArrayList<>();
    }

    public void addContestant(int ContestantNumber, String name, Contestant.Category category, Contestant.Level level, int[] scores, int age, String gender, String country) {
        Contestant contestant = new Contestant(ContestantNumber, name, level, category, scores, age, gender, country);
        contestants.add(contestant);
    }

    public Contestant getContestantByNumber(int ContestantNumber) {
        for (Contestant contestant : contestants) {
            if (contestant.getContestantNumber() == ContestantNumber) {
                return contestant;
            }
        }
        return null;
    }

    public void removeContestant(int ContestantNumber) {
        Contestant contestantToRemove = null;
        for (Contestant contestant : contestants) {
            if (contestant.getContestantNumber() == ContestantNumber) {
                contestantToRemove = contestant;
                break;
            }
        }
        if (contestantToRemove != null) {
            contestants.remove(contestantToRemove);
        }
    }

    public List<Contestant> getAllContestants() {
        return contestants;
    }

    public Contestant getWinner() {
        return contestants.stream()
                .max((c1, c2) -> Double.compare(c1.getOverallScore(), c2.getOverallScore()))
                .orElse(null);
    }

    public void writeContestantsToCSV(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Contestant contestant : contestants) {
                String line = String.format("%d,%s,%s,%s,%d,%s,%s,%s",
                        contestant.getContestantNumber(),
                        contestant.getName(),
                        contestant.getCategory(),
                        contestant.getLevel(),
                        contestant.getAge(),
                        contestant.getGender(),
                        contestant.getCountry(),
                        String.join(",", Arrays.stream(contestant.getScores()).mapToObj(String::valueOf).toArray(String[]::new)));
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}