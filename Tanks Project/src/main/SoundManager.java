package main;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

public class SoundManager {
	private static AudioClip explosion;
	private static AudioClip gunshot;
	public static Media getBackgroundSong(){
		return new Media(ClassLoader.getSystemResource("Two Steps from Hell - Black Blade.mp3").toString());
	}
	public static void playExplosionSound(){
		explosion = new AudioClip(ClassLoader.getSystemResource("explosion-01.wav").toString());
		explosion.play();
	}
	public static void playGunSound(){
		gunshot = new AudioClip(ClassLoader.getSystemResource("gun-fire-near.wav").toString());
		gunshot.play();
	}
}
