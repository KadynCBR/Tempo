import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
// TODO: Redesign and structuring of server for Prototype B.
/*
 *  Prototype B: Server will act in two areas. 
 *  			 1: passing back and forth
 *  			 	lists of TempoTracks/Directory information. This will be
 *  			 	how the app will select and navigate the remote file system
 *  			 	and look for songs.
 *  
 *  			 2: an HTTP File server which will serve the song and artwork 
 *  				requested by the app. The URL's for these requests as per 
 *  				song will be embedded in the TempoTrack class.
 */

public class TempoServer {
	byte[] currentSong;
	PrintWriter writer;
	public static void main(String[] args) {
		TempoServer ts = new TempoServer();
		ts.go();
	}
	public void go() {
		try {
			ServerSocket serverSock = new ServerSocket(5000);
			while(true) {
				Socket clientSocket = serverSock.accept();
				writer = new PrintWriter(clientSocket.getOutputStream());
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	} // close go
	public class ClientHandler implements Runnable {
		BufferedReader reader;
		Socket sock;
		public ClientHandler(Socket clientSocket) {
			DirectoryHelper dir = new DirectoryHelper();
			UtilityHelper ut = new UtilityHelper();
			while(true) {
				dir.displayMenu();
				int index = Integer.parseInt(ut.getUserInput("Please Select a file by inputing the number."));
				if (dir.select(index)) {
					// if this returns true, user selected a music file that's staged.
					// and we can begin to send file to user.
					//currentSong = dir.getSelectedSongArray();
					
					//sendSong();
				}
			}
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}
}
