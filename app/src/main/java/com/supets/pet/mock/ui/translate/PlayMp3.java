package com.supets.pet.mock.ui.translate;

import android.media.MediaPlayer;

import java.io.IOException;

public class PlayMp3 {

    private MediaPlayer mediaPlayer = new MediaPlayer();

    public void play(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url); //为播放器设置mp3文件的路径
            mediaPlayer.prepare(); //做好准备
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }


    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }


    public void destroy() {
        if (mediaPlayer != null) {
            stop();
            mediaPlayer.release();
        }
    }

}
