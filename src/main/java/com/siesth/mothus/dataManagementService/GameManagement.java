package com.siesth.mothus.dataManagementService;

import com.siesth.mothus.model.Game;
import com.siesth.mothus.model.UserLanguage;
import com.siesth.mothus.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is used to manage the game.
 */
@Service
public class GameManagement implements IGameManagement {
    /**
     * This field is used to get the game repository.
     */
    @Autowired
    GameRepository gameRepository;

    /**
     * Returns a random line from the given file
     * @param fullDictionary The full_dictionary.txt file to read from
     * @return a random line from the given file
     * @throws IOException if the resource cannot be resolved
     */
    private static String GetRandomLine(Resource fullDictionary) throws IOException {
        long fileSize = fullDictionary.contentLength();
        byte[] fullDictionaryBytes = fullDictionary.getContentAsByteArray();

        // 0 <= currentPosition < (fileSize - 1)
        // - 1 to not seek at the last \n
        int currentPosition = ThreadLocalRandom.current().nextInt((int) (fileSize - 1));

        // Either \r\n or \n, so we can just check for \n
        while (fullDictionaryBytes[currentPosition] != '\n' && currentPosition > 0) {
            currentPosition--;
        }

        // Build the word from the current position to the next \n or \r
        StringBuilder word = new StringBuilder();
        currentPosition++;
        while (fullDictionaryBytes[currentPosition] != '\n' && fullDictionaryBytes[currentPosition] != '\r' && currentPosition < fileSize) {
            word.append((char) fullDictionaryBytes[currentPosition]);
            currentPosition++;
        }
        return word.toString();
    }

    /**
     * Returns the game for today by creating it if it doesn't exist
     * @return game of today
     */
    @Override
    public Game getTodayGame() {
        Game lastGame = gameRepository.findTopByOrderByDateOfTheGameDesc();
        final LocalDate today = LocalDate.now();

        // Create and return a new game if there is no game at all, or the last game is not today
        if (lastGame == null || !lastGame.getDateOfTheGame().equals(today)) {
            String frenchWord;
            String englishWord;

            // For each language, get the resource file and then read a random line
            try {
                Resource frDictionary = loadFullDictionary(UserLanguage.fr);
                Resource enDictionary = loadFullDictionary(UserLanguage.en);

                frenchWord = GetRandomLine(frDictionary);
                englishWord = GetRandomLine(enDictionary);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            lastGame = new Game(today, frenchWord, englishWord);
            gameRepository.save(lastGame);
        }

        return lastGame;
    }

    @Override
    public String getRandomFrench() {
        String frenchWord;
        try {
            Resource frDictionary = loadFullDictionary(UserLanguage.fr);
            frenchWord = GetRandomLine(frDictionary);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return frenchWord;
    }

    @Override
    public String getRandomEnglish() {
        String englishWord;
        try {
            Resource enDictionary = loadFullDictionary(UserLanguage.en);
            englishWord = GetRandomLine(enDictionary);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return englishWord;
    }

    /**
     * Returns the full dictionary for the given language
     * @param language the language to get the dictionary from
     * @return the full dictionary for the given language
     */
    private static Resource loadFullDictionary(UserLanguage language) {
        return new ClassPathResource("dictionaries/" + language.name() + "/full_dictionary.txt");
    }
}
