import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * Decode Morse code from a WAV file.
 * <p>
 * We begin by classifying regions of the Morse code file as tone or silence. This requires binning
 * the file and computing power measurements for all of the samples that fall into each bin. The
 * BIN_SIZE parameter controls how long each bin is.
 * <p>
 * The next step is to classify each bin as tone or silence. The POWER_THRESHOLD parameter controls
 * this cutoff. We combine this with looking for runs of tone or silence and classifying them as dot
 * (short run of tone), dash (long run of tone), or space (long run of silence, equivalent in length
 * to dash). (In real Morse code there are also longer runs of silence that separate words, but
 * we'll ignore them for now.) DASH_BIN_COUNT controls the cutoff between dots and dashes when
 * considering tone, and is also used for spaces when considering silence.
 * <p>
 * At this point we have valid Morse code as space-separated letters! So the last step is to look up
 * each symbol in a table and return the corresponding letter value.
 */
public class MorseDecoder {

    /**
     * Bin size for power binning. We compute power over bins of this size. You will probably not
     * need to modify this value.
     */
    private static final int BIN_SIZE = 100;

    /**
     * Compute power measurements for fixed-size bins of WAV samples.
     * <p>
     * We have started this function for you. You should finish it.
     *
     * @param inputFile the input file to process
     * @return the double[] array of power measurements, one per bin
     * @throws WavFileException thrown if there is a WavFile-specific IO error
     * @throws IOException thrown on other IO errors
     */
    private static double[] binWavFilePower(final WavFile inputFile)
            throws IOException, WavFileException {

        /*
         * We should check the results of getNumFrames to ensure that they are safe to cast to int.
         */
        int totalBinCount = (int) Math.ceil(inputFile.getNumFrames() / BIN_SIZE);
        double[] returnBuffer = new double[totalBinCount];

        double[] sampleBuffer = new double[BIN_SIZE * inputFile.getNumChannels()];
        for (int binIndex = 0; binIndex < totalBinCount; binIndex++) {
            // Get the right number of samples from the inputFile
            // Sum all the samples together and store them in the returnBuffer
        }
        return returnBuffer;
    }

    /** Power threshold for power or no power. You may need to modify this value. */
    private static final double POWER_THRESHOLD = 10;

    /** Bin threshold for dots or dashes. Related to BIN_SIZE. You may need to modify this value. */
    private static final int DASH_BIN_COUNT = 8;

    /**
     * Convert power measurements to dots, dashes, and spaces.
     * <p>
     * This function receives the result from binWavPower. It's job is to convert intervals of tone
     * or silence into dots (short tone), dashes (long tone), or space (long silence).
     * <p>
     * Write this function.
     *
     * @param powerMeasurements the array of power measurements from binWavPower
     * @return the Morse code string of dots, dashes, and spaces
     */
    private static String powerToDotDash(final double[] powerMeasurements) {
        /*
         * There are four conditions to handle. Symbols should only be output when you see
         * transitions. You will also have to store how much power or silence you have seen.
         */

        // if ispower and waspower
        // else if ispower and not waspower
        // else if issilence and wassilence
        // else if issilence and not wassilence

        return "";
    }

    /**
     * Morse code to alpha mapping.
     *
     * @see <a href="https://morsecode.scphillips.com/morse2.html">Morse code reference</a>
     **/
    private static final Map<String, String> MORSE_TO_ALPHA = //
            new HashMap<>() {
            {
                put(".-", "a");
                put("-...", "b");
                put("-.-.", "c");
                put("-..", "d");
                put(".", "e");
                put("..-.", "f");
                put("--.", "g");
                put("....", "h");
                put("..", "i");
                put(".---", "j");
                put("-.-", "k");
                put(".-..", "l");
                put("--", "m");
                put("-.", "n");
                put("---", "o");
                put(".--.", "p");
                put("--.-", "q");
                put(".-.", "r");
                put("...", "s");
                put("-", "t");
                put("..-", "u");
                put("...-", "v");
                put(".--", "w");
                put("-..-", "x");
                put("-.--", "y");
                put("--..", "z");
                put(".----", "1");
                put("..---", "2");
                put("...--", "3");
                put("....-", "4");
                put(".....", "5");
                put("-....", "6");
                put("--...", "7");
                put("---..", "8");
                put("----.", "9");
                put("-----", "0");
                put(".-.-.-", ".");
            }
    };

    /**
     * Convert a Morse code string to alphanumeric characters using the mapping above.
     * <p>
     * Note that this will output "_" if it cannot look up a mapping. Usually this indicates that
     * there is a problem with your parameters. However, there are missing mappings in the table
     * above. Feel free to add them.
     * <p>
     * We have provided this function and the mapping above for you. You're welcome!
     *
     * @param dotDashes the Morse code string to decode
     * @return the decoded alphanumeric string
     * @see <a href="https://morsecode.scphillips.com/morse2.html">Morse code reference</a>
     */
    private static String dotDashToAlpha(final String dotDashes) {
        StringBuilder returnString = new StringBuilder();
        for (String dotDash : dotDashes.split(" ")) {
            returnString.append(MORSE_TO_ALPHA.getOrDefault(dotDash, "_"));
        }
        return returnString.toString();
    }

    /**
     * Convert a WAV file containing Morse code to a string.
     * <p>
     * This function brings everything together: the binning, the power thresholding, and the final
     * Morse to alphanumeric conversion.
     *
     * @param inputFile the input file to process
     * @return the decoded Morse code from the WAV file as a string
     * @throws WavFileException thrown if there is a WavFile-specific IO error
     * @throws IOException thrown on other IO errors
     */
    public static String morseWavToString(final WavFile inputFile)
            throws IOException, WavFileException {
        double[] binnedSamples = binWavFilePower(inputFile);
        String dotDash = powerToDotDash(binnedSamples);
        String outputString = dotDashToAlpha(dotDash);
        return dotDash + "\n" + outputString;
    }

    /**
     * Main method for testing.
     * <p>
     * Takes an input file from the user and tries to print out the Morse code by processing the
     * file using your code above. You should feel free to modify this, as well as to insert
     * additional print statements above to help determine what is going on... or what is going
     * wrong.
     *
     * @param unused unused input arguments
     */
    public static void main(final String[] unused) {

        String inputPrompt = "Enter the WAV filename (in src/main/resources):";
        Scanner lineScanner = new Scanner(System.in);

        while (true) {
            String inputFilename;
            System.out.println(inputPrompt);

            /*
             * We could just use lineScanner.hasNextInt() and not initialize a separate scanner. But
             * the default Scanner class ignores blank lines and continues to search for input until
             * a non-empty line is entered. This approach allows us to detect empty lines and remind
             * the user to provide a valid input.
             */
            String nextLine = lineScanner.nextLine();
            Scanner inputScanner = new Scanner(nextLine);
            if (!(inputScanner.hasNext())) {
                /*
                 * These should be printed as errors using System.err.println. Unfortunately,
                 * Eclipse can't keep System.out and System.err ordered properly.
                 */
                System.out.println("Invalid input: please enter a filename with no spaces.");
                continue;
            }
            inputFilename = inputScanner.next();
            /*
             * If the line started with a string but contains other tokens, reinitialize userInput
             * and prompt the user again.
             */
            if (inputScanner.hasNext()) {
                System.out.println("Invalid input: please enter only a single filename.");
                continue;
            }
            inputScanner.close();

            WavFile inputWavFile;
            try {
                String inputFilePath = Objects.requireNonNull(MorseDecoder.class.getClassLoader()
                        .getResource(inputFilename)).getFile();
                inputFilePath = new URI(inputFilePath).getPath();
                File inputFile = new File(inputFilePath);
                inputWavFile = WavFile.openWavFile(inputFile);
                if (inputWavFile.getNumChannels() != 1) {
                    throw new InvalidParameterException("We only process files with one channel.");
                }
                System.out.println(morseWavToString(inputWavFile));
                break;
            } catch (NullPointerException | WavFileException | IOException | URISyntaxException e) {
                throw new InvalidParameterException("Bad file path" + e);
            }
        }
        lineScanner.close();
    }
}
