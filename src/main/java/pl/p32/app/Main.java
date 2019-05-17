package pl.p32.app;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("This program requires two arguments");
        }

        Path directory = Paths.get(args[0]);
        Path rulesFile = Paths.get(args[1]);

        try {
            Cleaner cleaner = Cleaner.of(rulesFile);
            DeletionDetails details = cleaner.cleanDirectory(directory);
            System.out.println(details);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
