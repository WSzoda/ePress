package com.example.projekt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Authors {
    List<Author> authors;

    public List<Author> getAuthors() {
        return authors;
    }

    public Authors() throws FileNotFoundException {
        this.authors = loadAuthorsFromFile();
    }

    private List<Author> loadAuthorsFromFile() throws FileNotFoundException {
        List<Author> authors = new ArrayList<Author>();
        File authorsFile = new File("src/main/resources/database/authors.txt");
        Scanner scanner = new Scanner(authorsFile);
        scanner.nextLine();
        while(scanner.hasNextLine()){
            String authorString = scanner.nextLine();
            String[] authorElements = authorString.split(";");
            authors.add(new Author(Integer.parseInt(authorElements[0]), authorElements[1], authorElements[2], authorElements[3]));
        }
        scanner.close();
        return authors;
    }

    private int getIndexOfAuthor(int id){
        for(int i = 0; i < authors.size(); i++){
            if(authors.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    private int getNextId() throws IOException {
        File authorsFile = new File("src/main/resources/database/authors.txt");
        File authorsFileTemp = new File("src/main/resources/database/authorsTemp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(authorsFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(authorsFileTemp));
        int id = Integer.parseInt(reader.readLine());
        writer.write(String.valueOf(id+1));
        String currentLine;
        while((currentLine = reader.readLine()) != null){
            writer.newLine();
            writer.write(currentLine);
        }
        writer.close();
        reader.close();

        authorsFile.delete();
        authorsFileTemp.renameTo(authorsFile); //authorsFileTemp.renameTo(authorsFile);
        return id;
    }


    public void deleteAuthor(int id) throws IOException {
        int index = getIndexOfAuthor(id);
        if(index == -1){
            return;
        }

        File authorsFile = new File("src/main/resources/database/authors.txt");
        File authorsFileTemp = new File("src/main/resources/database/authorsTemp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(authorsFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(authorsFileTemp));

        String currentLine;
        while((currentLine = reader.readLine()) != null){
            if(Integer.parseInt(currentLine.split(";")[0]) == id){
                continue;
            }
            writer.write(currentLine + "\n");
        }
        writer.close();
        reader.close();

        authorsFile.delete();
        authorsFileTemp.renameTo(authorsFile); //authorsFileTemp.renameTo(authorsFile);
    }

    public void addAuthor (String name, String surname, String birthdate) throws IOException {
        int id = getNextId();
        File authorsFile = new File("src/main/resources/database/authors.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(authorsFile, true));
        Author newAuthor = new Author(id, name, surname, birthdate);
        authors.add(newAuthor);
        writer.newLine();
        writer.append(newAuthor.prepareToSaveToFile());
        writer.close();
    }
}

