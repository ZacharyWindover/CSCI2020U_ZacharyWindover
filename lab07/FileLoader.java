package Lab07;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class FileLoader {

    private String filename;

    private static Map<String, Integer> NaturalDisasters = new TreeMap<>();

    public FileLoader(String filename) {
        this.filename = filename;
    }


    public static void addNaturalDisaster(String naturalDisaster) {

        if (NaturalDisasters.containsKey(naturalDisaster)) {
            int previous = NaturalDisasters.get(naturalDisaster);
            NaturalDisasters.put(naturalDisaster, previous+1);
        }

        else {
            NaturalDisasters.put(naturalDisaster, 1);
        }
    }


    public void extractData() throws FileNotFoundException {

        File NaturalDisasters = new File(this.filename);
        Scanner dataScanner = new Scanner(NaturalDisasters);

        while (dataScanner.hasNextLine()) {

            String line = dataScanner.nextLine();
            String[] data = new String[7];

            data = line.split(",", 7);

            String naturalDisaster = data[5];

            addNaturalDisaster(naturalDisaster);

        }

    }

    public static TreeMap<String, Integer> getMap() { return (TreeMap<String, Integer>) NaturalDisasters; }

    // save NaturalDisasters to a file
    public static void outputNaturalDisasters(File output) throws IOException {
        System.out.println("Saving natural disasters to file: " + output.getAbsolutePath());

        if (!output.exists()) {										// if the file doesn't already exist
            output.createNewFile();									// create a new file with that name

            if (output.canWrite()) {								// if the file can be written to
                PrintWriter fileOutput = new PrintWriter(output);	// create a new PrintWrite to write to the file

                Set<String> keys = NaturalDisasters.keySet();					// get the set of keys from the dictionary map
                Iterator<String> keyIterator = keys.iterator();		// create an iterator to iterate through the map keys

                while(keyIterator.hasNext()) {
                    String key = keyIterator.next();				// get the key
                    float count = NaturalDisasters.get(key);					// get the value

                    fileOutput.println(key + " " + count);			// write the key : value pair to the file

                }

                fileOutput.close();									// close the file write

            }
        }

        else {
            System.out.println("Error: the output file already exists: " + output.getAbsolutePath());
        }

    }

}
