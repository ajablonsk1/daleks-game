package com.example.sr1615shrek.game;

import com.example.sr1615shrek.entity.position.Vector2d;

import java.io.*;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class LevelsMapsReader {

    private final String fileName;

    private int noDaleks;

    private BufferedReader reader;

    private LinkedList<String> fileLines;

    private final LinkedList<Vector2d> daleksPositions = new LinkedList<>();

    private Vector2d doctorPosition;

    private static final String resourcesPath = "src/main/resources/levelsMaps/";

    public LevelsMapsReader(int levelID) {
        this.fileName = levelID + ".txt";

        openAndReadFile();

        readNoDaleks();
        readDaleksPositions();
        readDoctorPosition();
    }

    private void openAndReadFile(){
        try{
            reader = new BufferedReader(new FileReader(resourcesPath + this.fileName));
            fileLines = reader.lines().collect(Collectors.toCollection(LinkedList::new));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readNoDaleks(){
        this.noDaleks = Integer.parseInt(fileLines.get(0));
    }

    private Vector2d getPositionOnIndex(int positionIndex) {
        String[] positions = fileLines.get(positionIndex).split(",");

        return new Vector2d(Integer.parseInt(positions[0]), Integer.parseInt(positions[1]));
    }

    private void readDaleksPositions() {
        for(int i = 1; i <= noDaleks; i++){
            daleksPositions.add(getPositionOnIndex(i));
        }
    }

    private void readDoctorPosition() {
        doctorPosition = getPositionOnIndex(noDaleks + 1);
    }

    public LinkedList<Vector2d> getDaleksPositions(){
        return daleksPositions;
    }

    public Vector2d getDoctorPosition(){
        return this.doctorPosition;
    }
}
