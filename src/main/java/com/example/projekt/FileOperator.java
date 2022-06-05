package com.example.projekt;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class FileOperator {
    public static void deleteInstanceFromFile(int id, String fileName) throws IOException {
        File file = new File("src/main/resources/database/" + fileName + ".txt");
        File fileTemp = new File("src/main/resources/database/" + fileName + "temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileTemp));

        String currentLine;
        while((currentLine = reader.readLine()) != null){
            if(Integer.parseInt(currentLine.split(";")[0]) == id){
                continue;
            }
            writer.write(currentLine + "\n");
        }
        writer.close();
        reader.close();
        file.delete();
        fileTemp.renameTo(file); //fileTemp.renameTo(File);
    }
    public static void addNewInstanceToFile(String data, String fileName) throws IOException {
        File file = new File("src/main/resources/database/" + fileName + ".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        writer.newLine();
        writer.append(data);
        writer.close();
    }
    public static ArrayList<String> getFileDataAsArray(String fileName) throws FileNotFoundException {
        File file = new File("src/main/resources/database/" + fileName + ".txt");
        Scanner scanner = new Scanner(file);
        scanner.nextLine();
        ArrayList<String> result = new ArrayList<>();
        while(scanner.hasNextLine()){
            String authorString = scanner.nextLine();
            result.add(authorString);
        }
        scanner.close();
        return result;
    }
    public static int getNextId(String fileName) throws IOException {
        File file = new File("src/main/resources/database/" + fileName + ".txt");
        File tempFile = new File("src/main/resources/database/" + fileName + "Temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        int id = Integer.parseInt(reader.readLine());
        writer.write(String.valueOf(id+1));
        String currentLine;
        while((currentLine = reader.readLine()) != null){
            writer.newLine();
            writer.write(currentLine);
        }
        writer.close();
        reader.close();

        file.delete();
        tempFile.renameTo(file); //tempFile.renameTo(file);
        return id;
    }
}
