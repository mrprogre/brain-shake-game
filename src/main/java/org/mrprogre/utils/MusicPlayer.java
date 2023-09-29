package org.mrprogre.utils;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.FileInputStream;
import java.util.Objects;

public class MusicPlayer {
    private int pausedOnFrame = 0;

    public void play(String path) {
        try {
            FileInputStream fis = new FileInputStream(Objects.requireNonNull(MusicPlayer.class.getResource(path)).getPath());

            AdvancedPlayer player = new AdvancedPlayer(fis);
            player.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent event) {
                    pausedOnFrame = event.getFrame();
                }
            });
            player.play(pausedOnFrame, Integer.MAX_VALUE);
        } catch (Exception ignored) {
        }
    }

}