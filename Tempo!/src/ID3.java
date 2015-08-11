import java.io.*;

/*
 * Class used to read and rip ID3 from mp3 data. Yea, Re-inventing the wheel, I know. 
 */
public class ID3 {
	private String title;
	private String artist;
	private String album;
	private int year;
	/*
	 * ID3(File song)
	 * @param File song - the song file which the ID3 object will represent.
	 * 
	 * Pass in a song file to construct a class of ID3 info based around 
	 * the song presented.
	 */
	public ID3(File song) {
		try {
			FileInputStream fis = new FileInputStream(song); 
	        int size = (int)song.length(); 
	        fis.skip(size - 128); 
	        byte[] last128 = new byte[128]; 
	        fis.read(last128); 
	        String id3 = new String(last128); 
	        String tag = id3.substring(0, 3); 
	        if (tag.equals("TAG")) { 
	           title = id3.substring(3, 32); 
	           artist = id3.substring(33, 62); 
	           album = id3.substring(63, 91); 
	           year = Integer.parseInt(id3.substring(93, 97)); 
	        } else {
	        	System.out.println("No ID3 TAG found.");
	        }
	        fis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public String getTitle() {
		return title;
	}
	public String getArtist() {
		return artist;
	}
	public String getAlbum() {
		return album;
	}
	public int getYear() {
		return year;
	}	
}