package model;

import java.util.Arrays;

public class Contestant
{
    private int ContestantNumber;
    private String name;
    private int age;
    private String gender;
    private String country;
    private int[] scores;
    private Category category;
    private Level level;

    public Contestant(int ContestantNumber, String name, int[] scores, int age, String gender, String country, Level level, Category category)
    {
        this.ContestantNumber = ContestantNumber;
        this.name = name;
        this.scores = scores;
        this.age = age;
        this.gender = gender;
        this.country = country;
        this.level = level;
        this.category = category;
    }

    public enum Level
    {
        BEGINNER,ADVANCED,PROFESSIONAL
    }
    public enum Category
    {
        MARATHON,JUMPS,JAVELIN
    }

    public int getContestantNumber() {
        return ContestantNumber;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getGender() {
        return gender;
    }
    public String getCountry() {
        return country;
    }
    public int[] getScores() {
        return scores;
    }
    public Category getCategory() {
        return category;
    }
    public Level getLevel() {
        return level;
    }
    public double getOverallScore()
    {
        return Arrays.stream(scores).average().orElse(0);
    }

    public String getFullDetails()
    {
        return String.format("Contestant number %d, name %s, category %s, level %s, age %d, gender %s, country %s. %s%n", ContestantNumber, name, category, level, age, gender, country, getShortDetails());
    }
    public String getShortDetails()
    {
        return String.format("CN %d (%s) has overall score %.2f.%n", ContestantNumber, getInitials(), getOverallScore());
    }

    private String getInitials()
    {
        String[] nameParts = name.split(" ");
        return nameParts.length >= 2 ? Character.toString(nameParts[0].charAt(0)) + nameParts[1].charAt(0) : "";
    }

    public void setName(String newName) {
        this.name = newName;
    }
    public void setAge(int newAge) {
        this.age = newAge;
    }
    public void setGender(String newGender) {
        this.gender = newGender;
    }
    public void setCountry(String newCountry) {
        this.country = newCountry;
    }
    public void setScores(int[] newScores) {
        this.scores = newScores;
    }
    public void setCategory(Category newCategory) {
        this.category = newCategory;
    }
    public void setLevel(Level newLevel) {
        this.level = newLevel;
    }
}
