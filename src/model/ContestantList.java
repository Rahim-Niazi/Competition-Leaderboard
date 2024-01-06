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
        Contestant contestant = new Contestant(ContestantNumber, name, level, category, scores, age, gender, country);contestants.add(contestant);
    }
