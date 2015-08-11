import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
// TODO: Redesign class with Prototype B in mind.
public class DirectoryHelper {
	ArrayList<File> currentDirectoryFiles;
	File currentDirectory;
	byte[] selectedMusicFile;
	boolean songStaged;
	public DirectoryHelper() {
		currentDirectory = new File("/home/kadyn/Music");
		currentDirectoryFiles = new ArrayList<File>();
		System.out.println(currentDirectory.getAbsolutePath());
		for (File file:currentDirectory.listFiles()) {
			System.out.println(file.getName());
			currentDirectoryFiles.add(file);
		}
	}
	public boolean select(int index) {
		if (currentDirectoryFiles.get(index).isDirectory()) {
			changeDirectory(index);
			return false;
		} else {
			return true;
		}
	}
/*	This is no longer the plan to transport files. Being kept for reference as needed.
 * 	OBSOLETE
	public void stageFileForStream(int index) {
		// This is one way
		try {
			FileInputStream fis = new FileInputStream(currentDirectoryFiles.get(index));
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			try {
				for (int readNum; (readNum = fis.read(buf)) != -1;) {
					bos.write(buf, 0, readNum);
					System.out.println("read " + readNum + " bytes");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			selectedMusicFile = bos.toByteArray();
			fis.close();
			bos.close();
			songStaged = true;
			System.out.println("File: " + currentDirectoryFiles.get(index).getName() + " Staged for stream");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		 *
		 * Apparently this is another easier way. try both.
		 * TODO: see which way works better.
		 * import java.nio.Files
		 * import java.nio.Paths
		 * import java.nio.Path
		 * 
		 * Path path = Paths.get(currentDirectoryFiles.get(index).getAbsolutePath());
		 * byte[] data = Files.readAllBytes(path);
		 *
	}
	*/
	public byte[] getSelectedSongArray() {
		if (songStaged) {
			// Song is in byte array and ready to go!
			return selectedMusicFile;
		} else {
			// No song selected
			System.out.println("there is no song staged for transport");
			return null;
		}
	}
	public void changeDirectory(int index) {
		currentDirectory = currentDirectoryFiles.get(index);
		currentDirectoryFiles.clear();
		for (File file:currentDirectory.listFiles()) {
			currentDirectoryFiles.add(file);
		}
		System.out.println("Directory Changed to: " + currentDirectory.getAbsolutePath());
	}
	public void displayMenu() {
		int i = 0;
		for(File files:currentDirectoryFiles) {
			if (files.isDirectory()) {
				System.out.println("\u001B[32m" + i + ": " + files.getName() + "\u001B[0m");
			} else {
				System.out.println(i + ": " + files.getName());
			}
			i++;
		}
	}
}
