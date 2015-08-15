package com.kadynmartinez.tempo;
import java.net.*;
import java.io.*;
import java.util.*;
// TODO: Return error codes and service errors to requester.
// TODO: Test. Test. Test.
// TODO: Logging class to keep track of things.
// TODO: See if you need to start a new thread for this class or for initializeServer().
public class FileServe {
	int port;
	String accessDir;
	ServerSocket sock;
	/*
	 * FileServe(int p, String a)
	 * @param int p - port number to initialize the FileServer ServerSocket on.
	 * @param String a - directory that you want the fileServer to access.
	 */
	public FileServe(int p, String a) {
		port = p;
		accessDir = a;
	}
	/*
	 * initializeServer()
	 * Will start the server and begin handling requests.
	 */
	public void initializeSever() {
		try {
			sock = new ServerSocket(port);
			System.out.println("Server started and accepting file requests on port " +
					port + ".");
		} catch (Exception ex) {
			System.out.println("Failed to start server.");
			ex.printStackTrace();
		}
		handleRequests();
	}
	private void handleRequests() {
		// Request handler loop
		while (true) {
			Socket connection = null;
			try {
				// wait for request
				connection = sock.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				OutputStream out = new BufferedOutputStream(connection.getOutputStream());
				PrintStream ps = new PrintStream(out);
				// read first line of request
				String request = in.readLine();
				if (request == null) 
					continue;
				//log(connection, request);
				while (true) {
					String misc = in.readLine();
					if (misc == null || misc.length() == 0)
						break;
				}
				// parse request, if it returns true, serve file.
				if (parseRequest(request)) {
					try {
						// Pass in directory location to get new file.
						String pathf = accessDir + "/" + request.substring(4, request.length()-9).trim();
						File f = new File(pathf);
						InputStream is = new FileInputStream(f);
						ps.print("HTTP/1.0 200 OK\r\n" + 
								 "Content-Type: " + getContentType(pathf) + "\r\n" +
								 "Date: " + new Date() + "\r\n" +
								 "Server: FileServer 1.0\r\n\r\n");
						sendFile(is, out); // Raw file
					} catch (Exception ex) {
						System.out.println("unable to locatefile to send.");
						ex.printStackTrace();
					}
				}
				out.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (connection != null) 
					connection.close();
			} catch (Exception ex) {
				System.out.println("Unable to close Connection");
				ex.printStackTrace();
			}
		}
	}
	private boolean parseRequest(String request) {
		// If request is able to get through checks, it's probably safe. (Probably).
		if (!request.startsWith("GET") || request.length() < 14 ||
				!(request.endsWith("HTTP/1.0") || request.endsWith("HTTP/1.1"))) {
			// Bad requests handle appropriately
			System.out.println("Bad request: " + request);
			return false;
		} else {
			String req = request.substring(4, request.length()-9).trim();
			if (req.indexOf("..") != -1 || 
					req.indexOf(".") != -1 || req.endsWith("~")) {
				// Malicious attempt at nonaccessable files.
				System.out.println("Malicious Attempt: " + request);
				return false;
			} else {
				String path = accessDir + "/" + req;
				File f = new File(path);
				if (f.isDirectory())
					return false;
			}
		}
		return true;
	}
	private void sendFile(InputStream file, OutputStream out) {
		try {
			byte[] buffer = new byte[1024];
			while (file.available() > 0) {
				out.write(buffer, 0, file.read(buffer));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	private String getContentType(String path) {
		// Since this should ONLY service music/artwork related items,
		// only checks for such should occur. If something else has made it this far,
		// a different class failed to do its job.
		if (path.endsWith(".mp3"))
			return "audio/mpeg3";
		if (path.endsWith(".flac"))
			return "audio/flac";
		if (path.endsWith(".jpg") || path.endsWith(".jpeg"))
			return "image/jpeg";
		if (path.endsWith(".gif"))
			return "image/gif";
		else
			return "text/plain"; // It really should never get to this point though.
	}
}
