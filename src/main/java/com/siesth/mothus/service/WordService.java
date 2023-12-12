package com.siesth.mothus.service;

import com.siesth.mothus.model.Game;
import com.siesth.mothus.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

@Service
public class WordService {

    private static final String filePathFr = "path/to/your/word/file.txt"; // TODO
    private static final String filePathEn = "path/to/your/word/file.txt"; // TODO

    @Autowired
    private GameRepository gameRepository; // Assuming you have a repository for Game entity

    public String getRandomWordFr() {
        try {
            List<String> words = Files.readAllLines(Paths.get(filePathFr));
            return getRandomElement(words);
        } catch (IOException e) {
            // Handle file reading exception
            e.printStackTrace();
            return null;
        }
    }

    public String getRandomWordEn() {
        try {
            List<String> words = Files.readAllLines(Paths.get(filePathEn));
            return getRandomElement(words);
        } catch (IOException e) {
            // Handle file reading exception
            e.printStackTrace();
            return null;
        }
    }

    private String getRandomElement(List<String> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public void updateGameWordsAtMidnight(Game game) {
        String newFrenchWord = getRandomWordFr();
        String newEnglishWord = getRandomWordEn();

        game.setFrenchWord(newFrenchWord);
        game.setEnglishWord(newEnglishWord);

        gameRepository.save(game);
    }
}
