package com.example.sr1615shrek.game;

import com.example.sr1615shrek.entity.position.Vector2d;

import java.io.*;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LevelsMapsReader {

    private final String filename;
    private int noDaleks;
    private BufferedReader reader;
    private LinkedList<String> fileLines;
    private final LinkedList<Vector2d> daleksPositions = new LinkedList<>();
    private Vector2d doctorPosition;

    public LevelsMapsReader(String filename) {
        this.filename = filename;

        openAndReadFile();

        readNoDaleks();
        readDaleksPositions();
        readDoctorPosition();
    }

    private void openAndReadFile(){
        try{
            String filePath = "levelsMaps/" + this.filename;
            reader = new BufferedReader(new FileReader("src/main/resources/" + filePath));
            fileLines = reader.lines().collect(Collectors.toCollection(LinkedList::new));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readNoDaleks(){
        this.noDaleks = Integer.parseInt(fileLines.get(0));
    }

    private void readDaleksPositions(){
        for(int i = 1; i <= noDaleks; i++){
            String[] XAndY = fileLines.get(i).split(",");

            daleksPositions.add(new Vector2d(Integer.parseInt(XAndY[0]), Integer.parseInt(XAndY[1])));
        }
    }

    private void readDoctorPosition(){
        String[] doctorPos = fileLines.get(noDaleks + 1).split(",");
        doctorPosition = new Vector2d(Integer.parseInt(doctorPos[0]), Integer.parseInt(doctorPos[1]));
    }

    public LinkedList<Vector2d> getDaleksPositions(){
        return daleksPositions;
    }

    public int getNoDaleks(){
        return this.noDaleks;
    }

    public Vector2d getDoctorPosition(){
        return this.doctorPosition;
    }
}
