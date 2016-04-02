package main;

import javafx.scene.media.Media;

public class SoundManager {
	public static Media getBackgroundSong(){
		return new Media(ClassLoader.getSystemResource("Two Steps from Hell - Black Blade.mp3").toString());
	}
}
