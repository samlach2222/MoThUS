package com.siesth.mothus.dataManagementService;

import com.siesth.mothus.model.Game;
import com.siesth.mothus.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;

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
     * @throws IOException if the file doesn't exist
     */
    private static String GetRandomLine(File fullDictionary) throws IOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(fullDictionary, "r")) {
            // - 1 to not seek at the end of the file, and another - 1 to not seek at the last \n
            randomAccessFile.seek((long) (Math.random() * (randomAccessFile.length() - 2)));

            // Either \r\n or \n, so we can just check for \n
            while (randomAccessFile.read() != '\n' && randomAccessFile.getFilePointer() > 1) {
                randomAccessFile.seek(randomAccessFile.getFilePointer() - 2);
            }

            return randomAccessFile.readLine();
        }
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
                File frDictionary = ResourceUtils.getFile("classpath:static/assets/dictionaries/fr/full_dictionary.txt");
                File enDictionary = ResourceUtils.getFile("classpath:static/assets/dictionaries/en/full_dictionary.txt");

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
}
