package Server;

import Client.GUI;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;

public class FileServerThread extends Thread {

    protected static Socket                 socket      =   null;
    protected static PrintWriter            networkOut  =   null;
    protected static BufferedReader         in          =   null;
    protected static DataInput              dataInput   =   null;
    protected static DataOutput             dataOutput  =   null;
    protected static DataInputStream        DIS         =   null;
    protected static DataOutputStream       DOS         =   null;

    // for sending client a file
    protected static BufferedInputStream    BIS         =   null;
    protected static OutputStream           OS          =   null;
    protected static FileInputStream        FIS         =   null;

    // for getting a file from a client
    protected static BufferedOutputStream   BOS         =   null;
    protected static InputStream            IS          =   null;

    FileServerThread(Socket socket) {
        super();
        this.socket = socket;

        System.out.println("Socket: " + socket);

        try {
            networkOut = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DOS = new DataOutputStream(socket.getOutputStream());
            //System.out.println("in and out successful");

        } catch (IOException e) {
            System.err.println("IOException while opening a read/write connection");
        }

    }

    public void run() {
        //System.out.println("Connected to Server");

        boolean endOfSession = false;

        while (!endOfSession) {
            try {
                endOfSession = processCommand();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            socket.close();
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    protected boolean processCommand() throws IOException {
        String input = null;

        try {
            input = in.readLine();
        } catch (IOException e) {
            System.err.println("Error reading command from socket.");
        }

        if (input == null) {

        } else {
            //System.out.println("input: " + input);

            if (input.equalsIgnoreCase(("DIR"))) {
                processCommand(input, null);
            } else {
                if (input.contains("UPLOAD")) {

                } else if (input.contains("DOWNLOAD")) {

                } else {
                    System.err.println("ERROR PASSING COMMAND TO SERVER / SERVER ERROR READING COMMAND");
                }

            }

        }

        return true;

    }

    protected void processCommand(String command, String args) {

        if (command.equalsIgnoreCase("DIR")) { sendDirectory(); }

        else if (command.equalsIgnoreCase("UPLOAD")) { uploadFile(args); }

        else if (command.equalsIgnoreCase(("DOWNLOAD"))) { downloadFile(args); }

        else { System.err.println("ERROR 404, COMMAND NOT RECOGNIZED"); }


    }

    protected void sendDirectory() {

        //System.out.println("Sending shared directory");

        // get list of files in directory and send to client
        SharedDirectory sharedDirectory = null;

        try { sharedDirectory = new SharedDirectory(); }
        catch (IOException e) { e.printStackTrace(); }

        String[] fileNames = sharedDirectory.getFileNames();
        String[] fileSizes = sharedDirectory.getFileSizes();
        String[] lastUpdates = sharedDirectory.getLastUpdated();
        String[] fileAuthors = sharedDirectory.getFileAuthor();

        try { DOS.writeBytes(String.valueOf(fileNames.length + "\n")); }
        catch (IOException e) { e.printStackTrace(); }

        for (int x = 0; x < fileNames.length; x++) {
            try { DOS.writeBytes(fileNames[x] + "\n"); }
            catch (IOException e) { e.printStackTrace(); }
        }

        for (int x = 0; x < fileNames.length; x++) {
            try { DOS.writeBytes(fileSizes[x] + "\n"); }
            catch (IOException e) { e.printStackTrace(); }
        }

        for (int x = 0; x < fileNames.length; x++) {
            try { DOS.writeBytes(lastUpdates[x] + "\n"); }
            catch (IOException e) { e.printStackTrace(); }
        }

        for (int x = 0; x < fileNames.length; x++) {
            try { DOS.writeBytes(fileAuthors[x] + "\n"); }
            catch (IOException e) { e.printStackTrace(); }
        }
    }

    protected void uploadFile(String args) {

        DataInputStream DIS = null;

        int selectedFileSize = 0;
        int copyIndex = 1;

        String fileName = args;
        String filePath = "./src/Server/resources/shared/" + fileName;

        File output = new File(filePath);

        byte[] byteArray = null;

        try {
            // assigning data input stream
            DIS = new DataInputStream(socket.getInputStream());

            // getting size of file
            selectedFileSize = DIS.readInt();

            // creating byte array for file
            byteArray = new byte[selectedFileSize];

            // writing bytes to byte array
            if (selectedFileSize > 0) { DIS.read(byteArray); }

        } catch (IOException e) { e.printStackTrace(); }


        try {

            // if output already exists, give it a different name by adding index to end
            if (output.exists()) {

                while (output.exists()) {
                    filePath = filePath + String.valueOf(copyIndex);
                    output = new File(filePath);
                    copyIndex++;
                }
            }

            // create new file with output
            output.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // get path of file
        Path path = Path.of(filePath);

        try {
            // write byte
            Files.write(path, byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void downloadFile(String args) {

        // getting file name, path, size and author
        String requestedFileName = args;
        String requestedFilePath = ("./src/Client/resources/local/" + args);

        System.out.println("args: " + args);
        System.out.println("requestedFilePath: " + requestedFilePath);

        File file = new File(requestedFilePath);
        Path filePath = Path.of(requestedFilePath);

        byte[] byteArray = null;


        try {

            // getting path of file in local directory and writing file to byte array
            byteArray = Files.readAllBytes(file.toPath());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("byteArray: ");

        for (int x = 0; x < byteArray.length; x++) {
            System.out.println("byteArray[x]: " + byteArray[x]);
        }

        try {
            // assigning data output stream
            DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());

            // sending byte array to server
            DOS.write(byteArray, 0, byteArray.length);

            // closing DOS
            DOS.flush();
            DOS.close();

        } catch (IOException e) { e.printStackTrace(); }


    }




}
