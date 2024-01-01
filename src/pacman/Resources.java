package pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Resources {
	
	public static final Color RED = new Color(255, 0, 0);
	public static final Color PINK = new Color(255, 153, 204);
	public static final Color BLUE = new Color(51, 255, 228);
	public static final Color ORANGE = new Color(255, 204, 51);
	public static final Color YELLOW = new Color(255, 255, 0);
	public static final Color COIN_COLOR = new Color(255, 185, 175);
	
	public static Image PACMAN;
	public static Image PACMAN_UP_1;
	public static Image PACMAN_UP_2;
	public static Image PACMAN_RIGHT_1;
	public static Image PACMAN_RIGHT_2;
	public static Image PACMAN_DOWN_1;
	public static Image PACMAN_DOWN_2;
	public static Image PACMAN_LEFT_1;
	public static Image PACMAN_LEFT_2;
	
	public static Image PACMAN_DEATH_1;
	public static Image PACMAN_DEATH_2;
	public static Image PACMAN_DEATH_3;
	public static Image PACMAN_DEATH_4;
	public static Image PACMAN_DEATH_5;
	public static Image PACMAN_DEATH_6;
	public static Image PACMAN_DEATH_7;
	public static Image PACMAN_DEATH_8;
	public static Image PACMAN_DEATH_9;
	public static Image PACMAN_DEATH_10;
	public static Image PACMAN_DEATH_11;
	
	public static Image PACMAN_LIFE;
	
	public static Image GHOST_RED_UP_1;
	public static Image GHOST_RED_UP_2;
	public static Image GHOST_RED_RIGHT_1;
	public static Image GHOST_RED_RIGHT_2;
	public static Image GHOST_RED_DOWN_1;
	public static Image GHOST_RED_DOWN_2;
	public static Image GHOST_RED_LEFT_1;
	public static Image GHOST_RED_LEFT_2;
	
	public static Image GHOST_PINK_UP_1;
	public static Image GHOST_PINK_UP_2;
	public static Image GHOST_PINK_RIGHT_1;
	public static Image GHOST_PINK_RIGHT_2;
	public static Image GHOST_PINK_DOWN_1;
	public static Image GHOST_PINK_DOWN_2;
	public static Image GHOST_PINK_LEFT_1;
	public static Image GHOST_PINK_LEFT_2;
	
	public static Image GHOST_BLUE_UP_1;
	public static Image GHOST_BLUE_UP_2;
	public static Image GHOST_BLUE_RIGHT_1;
	public static Image GHOST_BLUE_RIGHT_2;
	public static Image GHOST_BLUE_DOWN_1;
	public static Image GHOST_BLUE_DOWN_2;
	public static Image GHOST_BLUE_LEFT_1;
	public static Image GHOST_BLUE_LEFT_2;
	
	public static Image GHOST_ORANGE_UP_1;
	public static Image GHOST_ORANGE_UP_2;
	public static Image GHOST_ORANGE_RIGHT_1;
	public static Image GHOST_ORANGE_RIGHT_2;
	public static Image GHOST_ORANGE_DOWN_1;
	public static Image GHOST_ORANGE_DOWN_2;
	public static Image GHOST_ORANGE_LEFT_1;
	public static Image GHOST_ORANGE_LEFT_2;
	
	public static Image GHOST_FRIGHTENED_1;
	public static Image GHOST_FRIGHTENED_2;
	public static Image GHOST_FRIGHTENED_FLASH_1;
	public static Image GHOST_FRIGHTENED_FLASH_2;
	public static Image GHOST_EYES_UP;
	public static Image GHOST_EYES_RIGHT;
	public static Image GHOST_EYES_DOWN;
	public static Image GHOST_EYES_LEFT;
	
	public static Image COIN;
	public static Image COIN_POWER;
	
	public static Image CHERRY;
	public static Image STRAWBERRY;
	public static Image ORANGE_FRUIT;
	public static Image APPLE;
	public static Image MELON;
	public static Image GALAXIAN_STARSHIP;
	public static Image BELL;
	public static Image KEY;
	
	public static Image SCORE_200;
	public static Image SCORE_400;
	public static Image SCORE_800;
	public static Image SCORE_1600;

	public static Image MAP;
	public static Image MAP_FLASH_1;
	public static Image MAP_FLASH_2;
	
	public static Image TITLE;
	public static Image MARKER;
	public static Image PODIUM;
	public static Image BACKSPACE;
	public static Image RETURN;
	
	public static Font FONT_ARCADE;
	public static Font FONT_SPACEBIT;
	
	public static Clip START;
	public static Clip EATING;
	public static Clip DEATH;
	public static Clip GHOST_MOVE;
	public static Clip GHOST_FRIGHTENED;
	public static Clip GHOST_DEATH;
	public static Clip GHOST_RETURN;
	
	public void load() {
		try {
			PACMAN = ImageIO.read(getClass().getResource("/images/pacman_0.png"));
			PACMAN_UP_1 = ImageIO.read(getClass().getResource("/images/pacman_up_1.png"));
			PACMAN_UP_2 = ImageIO.read(getClass().getResource("/images/pacman_up_2.png"));
			PACMAN_RIGHT_1 = ImageIO.read(getClass().getResource("/images/pacman_right_1.png"));
			PACMAN_RIGHT_2 = ImageIO.read(getClass().getResource("/images/pacman_right_2.png"));
			PACMAN_DOWN_1 = ImageIO.read(getClass().getResource("/images/pacman_down_1.png"));
			PACMAN_DOWN_2 = ImageIO.read(getClass().getResource("/images/pacman_down_2.png"));
			PACMAN_LEFT_1 = ImageIO.read(getClass().getResource("/images/pacman_left_1.png"));
			PACMAN_LEFT_2 = ImageIO.read(getClass().getResource("/images/pacman_left_2.png"));
			
			PACMAN_DEATH_1 = ImageIO.read(getClass().getResource("/images/pacman_death_1.png"));
			PACMAN_DEATH_2 = ImageIO.read(getClass().getResource("/images/pacman_death_2.png"));
			PACMAN_DEATH_3 = ImageIO.read(getClass().getResource("/images/pacman_death_3.png"));
			PACMAN_DEATH_4 = ImageIO.read(getClass().getResource("/images/pacman_death_4.png"));
			PACMAN_DEATH_5 = ImageIO.read(getClass().getResource("/images/pacman_death_5.png"));
			PACMAN_DEATH_6 = ImageIO.read(getClass().getResource("/images/pacman_death_6.png"));
			PACMAN_DEATH_7 = ImageIO.read(getClass().getResource("/images/pacman_death_7.png"));
			PACMAN_DEATH_8 = ImageIO.read(getClass().getResource("/images/pacman_death_8.png"));
			PACMAN_DEATH_9 = ImageIO.read(getClass().getResource("/images/pacman_death_9.png"));
			PACMAN_DEATH_10 = ImageIO.read(getClass().getResource("/images/pacman_death_10.png"));
			PACMAN_DEATH_11 = ImageIO.read(getClass().getResource("/images/pacman_death_11.png"));
			
			PACMAN_LIFE = ImageIO.read(getClass().getResource("/images/pacman_life.png"));
			
			GHOST_RED_UP_1 = ImageIO.read(getClass().getResource("/images/ghost_red_up_1.png"));
			GHOST_RED_UP_2 = ImageIO.read(getClass().getResource("/images/ghost_red_up_2.png"));
			GHOST_RED_RIGHT_1 = ImageIO.read(getClass().getResource("/images/ghost_red_right_1.png"));
			GHOST_RED_RIGHT_2 = ImageIO.read(getClass().getResource("/images/ghost_red_right_2.png"));
			GHOST_RED_DOWN_1 = ImageIO.read(getClass().getResource("/images/ghost_red_down_1.png"));
			GHOST_RED_DOWN_2 = ImageIO.read(getClass().getResource("/images/ghost_red_down_2.png"));
			GHOST_RED_LEFT_1 = ImageIO.read(getClass().getResource("/images/ghost_red_left_1.png"));
			GHOST_RED_LEFT_2 = ImageIO.read(getClass().getResource("/images/ghost_red_left_2.png"));
			
			GHOST_PINK_UP_1 = ImageIO.read(getClass().getResource("/images/ghost_pink_up_1.png"));
			GHOST_PINK_UP_2 = ImageIO.read(getClass().getResource("/images/ghost_pink_up_2.png"));
			GHOST_PINK_RIGHT_1 = ImageIO.read(getClass().getResource("/images/ghost_pink_right_1.png"));
			GHOST_PINK_RIGHT_2 = ImageIO.read(getClass().getResource("/images/ghost_pink_right_2.png"));
			GHOST_PINK_DOWN_1 = ImageIO.read(getClass().getResource("/images/ghost_pink_down_1.png"));
			GHOST_PINK_DOWN_2 = ImageIO.read(getClass().getResource("/images/ghost_pink_down_2.png"));
			GHOST_PINK_LEFT_1 = ImageIO.read(getClass().getResource("/images/ghost_pink_left_1.png"));
			GHOST_PINK_LEFT_2 = ImageIO.read(getClass().getResource("/images/ghost_pink_left_2.png"));
			
			GHOST_BLUE_UP_1 = ImageIO.read(getClass().getResource("/images/ghost_blue_up_1.png"));
			GHOST_BLUE_UP_2 = ImageIO.read(getClass().getResource("/images/ghost_blue_up_2.png"));
			GHOST_BLUE_RIGHT_1 = ImageIO.read(getClass().getResource("/images/ghost_blue_right_1.png"));
			GHOST_BLUE_RIGHT_2 = ImageIO.read(getClass().getResource("/images/ghost_blue_right_2.png"));
			GHOST_BLUE_DOWN_1 = ImageIO.read(getClass().getResource("/images/ghost_blue_down_1.png"));
			GHOST_BLUE_DOWN_2 = ImageIO.read(getClass().getResource("/images/ghost_blue_down_2.png"));
			GHOST_BLUE_LEFT_1 = ImageIO.read(getClass().getResource("/images/ghost_blue_left_1.png"));
			GHOST_BLUE_LEFT_2 = ImageIO.read(getClass().getResource("/images/ghost_blue_left_2.png"));
			
			GHOST_ORANGE_UP_1 = ImageIO.read(getClass().getResource("/images/ghost_orange_up_1.png"));
			GHOST_ORANGE_UP_2 = ImageIO.read(getClass().getResource("/images/ghost_orange_up_2.png"));
			GHOST_ORANGE_RIGHT_1 = ImageIO.read(getClass().getResource("/images/ghost_orange_right_1.png"));
			GHOST_ORANGE_RIGHT_2 = ImageIO.read(getClass().getResource("/images/ghost_orange_right_2.png"));
			GHOST_ORANGE_DOWN_1 = ImageIO.read(getClass().getResource("/images/ghost_orange_down_1.png"));
			GHOST_ORANGE_DOWN_2 = ImageIO.read(getClass().getResource("/images/ghost_orange_down_2.png"));
			GHOST_ORANGE_LEFT_1 = ImageIO.read(getClass().getResource("/images/ghost_orange_left_1.png"));
			GHOST_ORANGE_LEFT_2 = ImageIO.read(getClass().getResource("/images/ghost_orange_left_2.png"));
			
			GHOST_FRIGHTENED_1 = ImageIO.read(getClass().getResource("/images/ghost_frightened_1.png"));
			GHOST_FRIGHTENED_2 = ImageIO.read(getClass().getResource("/images/ghost_frightened_2.png"));
			GHOST_FRIGHTENED_FLASH_1 = ImageIO.read(getClass().getResource("/images/ghost_frightened_flash_1.png"));
			GHOST_FRIGHTENED_FLASH_2 = ImageIO.read(getClass().getResource("/images/ghost_frightened_flash_2.png"));
			GHOST_EYES_UP = ImageIO.read(getClass().getResource("/images/ghost_eyes_up.png"));
			GHOST_EYES_RIGHT = ImageIO.read(getClass().getResource("/images/ghost_eyes_right.png"));
			GHOST_EYES_DOWN = ImageIO.read(getClass().getResource("/images/ghost_eyes_down.png"));
			GHOST_EYES_LEFT = ImageIO.read(getClass().getResource("/images/ghost_eyes_left.png"));
			
			COIN = ImageIO.read(getClass().getResource("/images/cookie.png"));
			COIN_POWER = ImageIO.read(getClass().getResource("/images/cookie_power.png"));
			
			CHERRY = ImageIO.read(getClass().getResource("/images/fruit_cherry.png"));
			STRAWBERRY = ImageIO.read(getClass().getResource("/images/fruit_strawberry.png"));
			ORANGE_FRUIT = ImageIO.read(getClass().getResource("/images/fruit_orange.png"));
			APPLE = ImageIO.read(getClass().getResource("/images/fruit_apple.png"));
			MELON = ImageIO.read(getClass().getResource("/images/fruit_melon.png"));
			GALAXIAN_STARSHIP = ImageIO.read(getClass().getResource("/images/fruit_galaxian_starship.png"));
			BELL = ImageIO.read(getClass().getResource("/images/fruit_bell.png"));
			KEY = ImageIO.read(getClass().getResource("/images/fruit_key.png"));
			
			SCORE_200 = ImageIO.read(getClass().getResource("/images/score_200.png"));
			SCORE_400 = ImageIO.read(getClass().getResource("/images/score_400.png"));
			SCORE_800 = ImageIO.read(getClass().getResource("/images/score_800.png"));
			SCORE_1600 = ImageIO.read(getClass().getResource("/images/score_1600.png"));
			
			MAP = ImageIO.read(getClass().getResource("/images/map.png"));
			MAP_FLASH_1 = ImageIO.read(getClass().getResource("/images/map_flash_1.png"));
			MAP_FLASH_2 = ImageIO.read(getClass().getResource("/images/map_flash_2.png"));
			
			TITLE = ImageIO.read(getClass().getResource("/images/title.png"));
			MARKER = ImageIO.read(getClass().getResource("/images/marker.png"));
			PODIUM = ImageIO.read(getClass().getResource("/images/podium.png"));
			BACKSPACE = ImageIO.read(getClass().getResource("/images/backspace.png"));
			RETURN = ImageIO.read(getClass().getResource("/images/return.png"));
			
			START = loadClip("/audio/start.wav");
			EATING = loadClip("/audio/eating.wav");
			DEATH = loadClip("/audio/death.wav");
			GHOST_MOVE = loadClip("/audio/move.wav");
			GHOST_FRIGHTENED = loadClip("/audio/frightened.wav");
			GHOST_DEATH = loadClip("/audio/ghost_death.wav");
			GHOST_RETURN = loadClip("/audio/ghost_return.wav");
			
			InputStream is = getClass().getResourceAsStream("/fonts/ARCADE_N.TTF");
			FONT_ARCADE = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(20f);
			is = getClass().getResourceAsStream("/fonts/SPACEBIT.TTF");
			FONT_SPACEBIT = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(24f);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
	}
	
	public Clip loadClip(String file) {
		Clip in = null;
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(file));
			in = AudioSystem.getClip();
			in.open(audioIn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return in;
	}
	
	public void playAudio(Clip clip) {
		 if(clip.isRunning())
			 clip.stop();
		 clip.setFramePosition(0);
         clip.start();
	}
	
	public void loopAudio(Clip clip) {
		 if(!clip.isRunning()) {
			 clip.setFramePosition(0);
			 clip.start();
		 }
	}
	
	public void loopFrightened() {
		if (!Resources.GHOST_FRIGHTENED.isRunning() || Resources.GHOST_FRIGHTENED.getFramePosition() > 176000)
			loopAudio(Resources.GHOST_FRIGHTENED);
	}
	
	public void loopMove() {
		if (!Resources.GHOST_MOVE.isRunning() || Resources.GHOST_MOVE.getFramePosition() > 250000)
			loopAudio(Resources.GHOST_MOVE);
	}
	
	public void loopReturn() {
		if (!Resources.GHOST_RETURN.isRunning() || Resources.GHOST_RETURN.getFramePosition() > 291000)
			loopAudio(Resources.GHOST_RETURN);
	}
	
	public void createFile() {
		try {
			new File("leaderboard.txt").createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String[][] getLeaderboard() {
		String[][] leaderboard = new String[11][3];
		for (int i = 0; i < leaderboard.length; i++) {
			leaderboard[i][0] = "" + readScore(i);
			leaderboard[i][1] = "" + readStage(i);
			leaderboard[i][2] = readInitials(i);
		}
		return leaderboard;
	}
	
	public void writeScore(int score, int round, String initials, int position) {
		try {
			ArrayList<ArrayList<String>> leaderboardList = new ArrayList<>();
			String[][] leaderboard = getLeaderboard();
			for (int i = 0; i < leaderboard.length; i++) {
				leaderboardList.add(new ArrayList<>());
				for (int j = 0; j < leaderboard[0].length; j++)
					leaderboardList.get(i).add(leaderboard[i][j]);
			}
			leaderboardList.remove(10);
			leaderboardList.add(position, new ArrayList<>(Arrays.asList("" + score, "" + round, initials)));
			BufferedWriter clearer = new BufferedWriter(new FileWriter("leaderboard.txt"));
			clearer.write("");
			clearer.close();
			BufferedWriter writer = new BufferedWriter(new FileWriter("leaderboard.txt", true));
			for (ArrayList<String> a : leaderboardList) {
				writer.append(a.get(0) + ", " + a.get(1) + ", " + a.get(2));
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int indexOfAt(String str, String substr, int n) {
		int index = str.indexOf(substr);
	    while (--n > 0 && index != -1)
	    	index = str.indexOf(substr, index + 1);
	    return index;
	}
	
	public int readScore(int line) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("leaderboard.txt"));
			for (int i = 0; i < line; i++)
				reader.readLine();
			String score = reader.readLine();
			if (score != null && score.length() > 0)
				score = score.substring(0, score.indexOf(","));
			else
				return 0;
			return Integer.parseInt(score);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int readStage(int line) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("leaderboard.txt"));
			for (int i = 0; i < line; i++)
				reader.readLine();
			String stage = reader.readLine();
			if (stage != null && stage.length() > 0)
				stage = stage.substring(stage.indexOf(",") + 2, indexOfAt(stage, ",", 2));
			else
				return 0;
			return Integer.parseInt(stage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public String readInitials(int line) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("leaderboard.txt"));
			for (int i = 0; i < line; i++)
				reader.readLine();
			String initials = reader.readLine();
			if (initials != null && initials.length() > 0)
				initials = initials.substring(indexOfAt(initials, ",", 2) + 2, initials.length());
			else
				return "---";
			return initials;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "---";
	}
	
	public int leaderboardPosition(int score) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("leaderboard.txt"));
			for (int i = 0; i < 11; i++)
				if (score >= readScore(i))
					return i;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
