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