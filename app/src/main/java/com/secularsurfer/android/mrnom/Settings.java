package com.secularsurfer.android.mrnom;

import android.util.Log;

import com.secularsurfer.android.framework.FileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Settings {

    private static final String TAG = Settings.class.getSimpleName();

    public static boolean soundEnabled = true;
    public static int[] highscores = new int[] {100, 80, 50, 30, 10};

    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(files.readFile(".mrnom")));
            soundEnabled = Boolean.parseBoolean(in.readLine());
            for (int i = 0; i < 5; i++) {
                highscores[i] = Integer.parseInt(in.readLine());
            }
        } catch (IOException e) {
            Log.e(TAG, "<load> :( It's ok we have defaults");
        } catch (NumberFormatException e) {
            Log.e(TAG, "<load> :( It's ok, defaults save our day");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "<load> :( It's ok, defaults save our day");
            }
        }
    }

    public static void save(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(files.writeFile(".mrnom")));
            out.write(Boolean.toString(soundEnabled));
            for (int i = 0; i < 5; i++) {
                out.write(Integer.toString(highscores[i]));
            }
        } catch (IOException e) {
            Log.e(TAG, "<save> :( It's ok, defaults save our day");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "<save> :( It's ok, defaults save our day");
            }
        }
    }

    public static void addScore(int score) {
        for (int i = 0; i < 5; i++) {
//            System.arraycopy(highscores, 0, highscores, i - 1,highscores.length);
            if (highscores[i] < score) {
                for (int j = 4; j > i; j--) {
                    highscores[j] = highscores[j-1];
                }
                highscores[i] = score;
                break;
            }
        }
    }
}
