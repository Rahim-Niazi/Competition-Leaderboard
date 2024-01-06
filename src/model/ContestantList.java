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

public class ContestantList
{
    private List<Contestant> contestants;
    public ContestantList() {
        contestants = new ArrayList<>();
    }

    public void addContestant(int ContestantNumber, String name, Contestant.Category category, Contestant.Level level, int[] scores, int age, String gender, String country)
    {
        Contestant contestant = new Contestant(ContestantNumber, name, scores, age, gender, country, level, category);
        contestants.add(contestant);
    }
    public Contestant getContestantByNumber(int ContestantNumber)
    {
        for (Contestant contestant : contestants)
        {
            if (contestant.getContestantNumber() == ContestantNumber)
            {
                return contestant;
            }
        }
        return null;
    }

    public void removeContestant(int ContestantNumber)
    {
        Contestant contestantToRemove = null;
        for (Contestant contestant : contestants)
        {
            if (contestant.getContestantNumber() == ContestantNumber)
            {
                contestantToRemove = contestant;
                break;
            }
        }
        if (contestantToRemove != null)
        {
            contestants.remove(contestantToRemove);
        }
    }

    public List<Contestant> getAllContestants()
    {
        return contestants;
    }

    public Contestant getWinner()
    {
        return contestants.stream()
                .max((c1, c2) -> Double.compare(c1.getOverallScore(), c2.getOverallScore()))
                .orElse(null);
    }

    public void writeContestantsToCSV(String filePath)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)))
        {
            for (Contestant contestant : contestants)
            {
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
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void readContestantsFromCSV(String filePath)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] data = line.split(",");
                if (data.length < 8)
                {
                    // Skip incomplete or invalid data
                    System.err.println("Skipping invalid data: " + Arrays.toString(data));
                    continue;
                }
                try
                {
                    int ContestantNumber = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    String categoryStr = data[2].trim();
                    String levelStr = data[3].trim();

                    int age;
                    if (!data[4].trim().isEmpty())
                    {
                        age = Integer.parseInt(data[4].trim());
                    } else
                    {
                        continue;
                    }

                    String gender = data[5].trim();
                    String country = data[6].trim();

                    Contestant.Category category = Contestant.Category.valueOf(categoryStr);
                    Contestant.Level level = Contestant.Level.valueOf(levelStr);

                    int[] scores = Arrays.stream(data, 7, data.length)
                            .mapToInt(Integer::parseInt)
                            .toArray();

                    Contestant contestant = new Contestant(ContestantNumber, name, scores, age, gender, country, level, category);
                    contestants.add(contestant);
                } catch (NumberFormatException e)
                {
                    System.err.println("Error parsing data: " + Arrays.toString(data));
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Error reading data from CSV file");
        }
    }

    public void produceFinalReport(String filePath)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)))
        {
            writer.write("Contestants:\n");
            writer.write("Contestant Number | Name | Category | Level | Age | Gender | Country | Scores\n");
            for (Contestant contestant : contestants) {
                writer.write(String.format("%d | %s | %s | %s | %d | %s | %s | %s%n",
                        contestant.getContestantNumber(),
                        contestant.getName(),
                        contestant.getCategory(),
                        contestant.getLevel(),
                        contestant.getAge(),
                        contestant.getGender(),
                        contestant.getCountry(),
                        Arrays.toString(contestant.getScores())));
            }
            writer.write("\n");

            Contestant winner = getWinner();
            if (winner != null)
            {
                writer.write("Contestant with the highest overall score:\n");
                writer.write(String.format("Contestant Number: %d, Name: %s, Overall Score: %.2f%n",
                        winner.getContestantNumber(), winner.getName(), winner.getOverallScore()));
                writer.write("\n");
            }

            writer.write("Summary Statistics:\n");
            writer.write("Total Contestants: " + contestants.size() + "\n");
            writer.write("Average Overall Score: " + calculateAverageOverallScore() + "\n");
            writer.write("Maximum Overall Score: " + calculateMaxOverallScore() + "\n");
            writer.write("Minimum Overall Score: " + calculateMinOverallScore() + "\n");

            writer.write("Frequency Report:\n");
            Map<Integer, Integer> scoreFrequency = calculateScoreFrequency();
            for (Map.Entry<Integer, Integer> entry : scoreFrequency.entrySet())
            {
                writer.write("Score " + entry.getKey() + ": " + entry.getValue() + " times\n");
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean isValidContestantNumber(int ContestantNumber)
    {
        return getContestantByNumber(ContestantNumber) != null;
    }

    private double calculateAverageOverallScore()
    {
        return contestants.stream().mapToDouble(Contestant::getOverallScore).average().orElse(0);
    }

    private double calculateMaxOverallScore()
    {
        return contestants.stream().mapToDouble(Contestant::getOverallScore).max().orElse(0);
    }

    private double calculateMinOverallScore()
    {
        return contestants.stream().mapToDouble(Contestant::getOverallScore).min().orElse(0);
    }

    private Map<Integer, Integer> calculateScoreFrequency()
    {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Contestant contestant : contestants) {
            for (int score : contestant.getScores()) {
                frequencyMap.put(score, frequencyMap.getOrDefault(score, 0) + 1);
            }
        }
        return frequencyMap;
    }
}
