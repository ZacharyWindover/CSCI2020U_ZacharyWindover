package Client;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileServerClient {

    private     static      Socket                  socket          =   null;

    private     static      BufferedReader          in              =   null;
    private     static      PrintWriter             networkOut      =   null;

    public      static      String                  SERVER_ADDRESS  =   "localhost";
    public      static      int                     SERVER_PORT     =   8000;

    public      static      String                  command         =   null;

    private     static      String[]                fileNames       =   null;
    private     static      String[]                fileSizes       =   null;
    private     static      String[]                lastUpdates     =   null;
    private     static      String[]                fileAuthors     =   null;

    public FileServerClient(String cmd) throws IOException {
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

        System.out.println("socket: " + socket);

        if (socket == null) { System.err.println("Socket is null"); }

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            networkOut = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (IOException e) { System.err.println("IOException while opening a read/write connection"); }

        command = cmd;

        if (command.equalsIgnoreCase("DIR")) {
            setDirectory();
        } else if (command.contains("UPLOAD")) {
            sendFile();
        } else if (command.contains("DOWNLOAD")) {
            receiveFile();
        } else { System.err.println("VALID COMMAND NOT DETECTED"); }

    }

    public void setDirectory() throws IOException {

        networkOut.println(command);

        int numFiles = Integer.parseInt(in.readLine());

        // getting / setting fileNames
        String[] fileNames = new String[numFiles];
        for (int x = 0; x < numFiles; x++) { fileNames[x] = in.readLine(); }
        this.fileNames = fileNames;

        // getting / setting fileSizes
        String[] fileSizes = new String[numFiles];
        for (int x = 0; x < numFiles; x++) { fileSizes[x] = in.readLine(); }
        this.fileSizes = fileSizes;

        // getting / setting lastUpdates
        String[] lastUpdates = new String[numFiles];
        for (int x = 0; x < numFiles; x++) { lastUpdates[x] = in.readLine(); }
        this.lastUpdates = lastUpdates;

        // getting / setting fileAuthors
        String[] fileAuthors = new String[numFiles];
        for (int x = 0; x < numFiles; x++) { fileAuthors[x] = in.readLine(); }
        this.fileAuthors = fileAuthors;



    }

    public void sendFile() {

        // getting file name, path, size and author
        String requestedFileName = GUI.selectedFileName;
        String requestedFilePath = ("./src/Client/resources/local/" + GUI.selectedFileName);

        // send submission ping (contains file name)
        networkOut.println(command);

        try {

            // getting path of file in local directory and writing file to byte array
            Path filePath = Path.of(requestedFilePath);
            byte[] byteArray = Files.readAllBytes(filePath);

            // assigning data output stream
            DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());

            int fileLength = (int)byteArray.length;

            // sending size of file to server
            DOS.write(fileLength);

            // sending byte array to server
            DOS.write(byteArray);

            // closing DOS
            DOS.flush();
            DOS.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void receiveFile() {

        String testFilePath = "./src/Client/resources/local/fifth-business.pdf";
        File testFile = new File(testFilePath);

        try {
            byte[] testByte = Files.readAllBytes(testFile.toPath());

            System.out.println("length of testByte: " + testByte.length);
            System.out.println("testByte.toString(): " + testByte.toString());
            File testFile2 = new File("./src/Client/resources/local/fifth-business2.pdf");
            OutputStream oot = new FileOutputStream(testFile2);
            oot.write(testByte);
            oot.close();

        } catch (IOException e) {
            System.out.println("Test failed");
        }

        networkOut.println(command);

        DataInputStream DIS = null;

        int selectedFileSize = 0;
        int copyIndex = 1;

        String requestedFileName = GUI.selectedFileName;
        String requestedFilePath = ("./src/Client/resources/local/" + GUI.selectedFileName);
        String requestedFileSize = GUI.selectedFileSize;
        String requestedFileAuthor = GUI.selectedFileAuthor;

        String[] parts = requestedFileSize.split(" ");
        selectedFileSize = Integer.parseInt(parts[0]) * 1000;

        File output = new File(requestedFilePath);

        byte[] byteArray = null;

        try {
            // assigning data input stream
            DIS = new DataInputStream(socket.getInputStream());

            // creating byte array for file
            byteArray = new byte[selectedFileSize];

            // writing bytes to byte array
            if (selectedFileSize > 0) { DIS.readFully(byteArray, 0, byteArray.length); }

        } catch (IOException e) { e.printStackTrace(); }


        try {

            // if output already exists, give it a different name by adding index to end
            if (output.exists()) {

                while (output.exists()) {
                    requestedFilePath = requestedFilePath + String.valueOf(copyIndex);
                    output = new File(requestedFilePath);
                    copyIndex++;
                }
            }

            // create new file with output
            output.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            OutputStream fileOutput = new FileOutputStream(output);
            fileOutput.write(byteArray);
            fileOutput.close();

        } catch (FileNotFoundException e) { e.printStackTrace(); }
          catch (IOException e) { e.printStackTrace(); }


    }

    public static String[] getFileNames() { return fileNames; }
    public static String[] getFileSizes() { return fileSizes; }
    public static String[] getLastUpdates() { return lastUpdates; }
    public static String[] getFileAuthors() { return fileAuthors; }
}