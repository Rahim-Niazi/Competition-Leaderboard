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
}

public Contestant(int ContestantNumber, String name, int[] scores, int age, String gender, String country)
{
    this.ContestantNumber = ContestantNumber;
    this.name = name;
    this.scores = scores;
    this.age = age;
    this.gender = gender;
    this.country = country;
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
public double getOverallScore() {return 5;}


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