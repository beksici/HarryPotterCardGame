package com.card.harrypotter;


public class SongSettings {
   public static boolean mediaPlayerMusicOnOff;
    public static boolean mediaPlayerSoundOnOff;

    public static boolean isMediaPlayerMusicOnOff() {
        return mediaPlayerMusicOnOff;
    }

    public static void setMediaPlayerMusicOnOff(boolean mediaPlayerMusicOnOff) {
        SongSettings.mediaPlayerMusicOnOff = mediaPlayerMusicOnOff;
    }

    public static boolean isMediaPlayerSoundOnOff() {
        return mediaPlayerSoundOnOff;
    }

    public static void setMediaPlayerSoundOnOff(boolean mediaPlayerSoundOnOff) {
        SongSettings.mediaPlayerSoundOnOff = mediaPlayerSoundOnOff;
    }



}
