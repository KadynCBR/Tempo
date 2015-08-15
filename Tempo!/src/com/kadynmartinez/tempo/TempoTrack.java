package com.kadynmartinez.tempo;
public class TempoTrack {
	private String title;
	private int id;
	private String StreamURL;
	private String ArtworkURL;
	
	/*
	 * TempoTrack(String t, int i, String s, String a)
	 * @param String t - title setter.
	 * @param int i - id setter.
	 * @param String s - StreamURL setter.
	 * @param String a - ArtworkURL setter.
	 * Used to initialize a new track with relevant information.
	 */
	public TempoTrack(String t, int i, String s, String a) {
		title = t;
		id = i;
		StreamURL = s;
		ArtworkURL = a;
	}
	/*
	 * TempoTrack(int i)
	 * @param int i - i used to set ID, ID tells receiver if this 
	 * 				  is a music track or a directory. 0 = directory.
	 * Used to initialize TempoTrack with just and ID. This is intended
	 * to be used only for Directories so you can make sure the ID
	 * is set to 0.
	 */
	public TempoTrack(int i) {
		id = i;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String t) {
		title = t;
	}
	public int getID() {
		return id;
	}
	public void setID(int i) {
		id = i;
	}
	public String getStreamURL() {
		return StreamURL;
	}
	public void setStreamURL(String u) {
		StreamURL = u;
	}
	public String getArtworkURL() {
		return ArtworkURL;
	}
	public void setArtworkURL(String u) {
		ArtworkURL = u;
	}
}
