import MaxFibonacciHeap.Implement;
import MaxFibonacciHeap.Node;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;


@SuppressWarnings("ALL")
public class hashtagcounter {

    public static void main(String[] list) throws IOException {
        Implement MaxFH = new Implement();
        HashMap<String, Node> table = new HashMap<>();

        /**
         * Read input file
         * Create output file
         * Initialize the IO streams.
         */
        String inputfileName = list[0];
        File myFile = new File(inputfileName);

        AtomicReference<File> outputFile = null;
        if (list.length == 1) {
            outputFile = new AtomicReference<>(new File("output_file.txt"));
        } else {
            outputFile = new AtomicReference<>(new File(list[1]));
        }

        outputFile.get().createNewFile();


        FileInputStream inputStream = new FileInputStream(myFile);
        BufferedInputStream inputBuffer = new BufferedInputStream(inputStream);
        Scanner scanner = new Scanner(inputBuffer);
        FileOutputStream outputStream = new FileOutputStream(String.valueOf(outputFile));
        BufferedOutputStream outputBuffer = new BufferedOutputStream(outputStream);
        DataOutputStream data = new DataOutputStream(outputBuffer);

        try {
            while (scanner.hasNextLine()) {
                /**
                 * Read the keyword after "#";
                 * if the table contains same keyword, run increaseaKey();
                 * if not, run insertNode() to insert a new keyword;
                 * I use "," to separate keywords and create a new line;
                 * Then I reinsert the keywords removed by removemax() for further calculate.
                 */
                String letter = scanner.nextLine();
                if (letter.startsWith("#")) {
                    String keyword = letter.substring(1, letter.indexOf(" "));
                    int frequency = Integer.parseInt(letter.substring(letter.indexOf(" ") + 1));
                    if (table.containsKey(keyword)) {
                        MaxFH.increaseKey(table.get(keyword), frequency);
                    } else {
                        Node newKeyword = new Node(frequency, keyword);
                        table.put(keyword, newKeyword);
                        MaxFH.insertNode(newKeyword);
                    }
                } else if (letter.startsWith("stop")) {
                        break;
                } else {
                    int numOfKeyword = Integer.parseInt(letter);
                    Node[] sortedKeyword = new Node[numOfKeyword];

                    int temp;
                    for (temp = 0; MaxFH.max != null && temp < numOfKeyword; temp++) {
                        sortedKeyword[temp] = MaxFH.removeMax();

                        if (temp != 0) {
                            data.write(44);
                        }

                        data.write(sortedKeyword[temp].keyword.getBytes());
                    }

                    data.write("\r\n".getBytes());

                    for (int temp2 = 0; temp2 < temp; temp2++) {
                        MaxFH.insertNode(sortedKeyword[temp2]);
                    }
                }
            }

        } catch (FileNotFoundException exp) {
            exp.printStackTrace();
        }

        inputBuffer.close();
        scanner.close();
        data.close();

        /**
         * If we specify the output_file, the output results will be also shown on the console
         */
        if (list.length == 1) {
            try {
                FileReader fileReader = new FileReader("output_file.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String s  = bufferedReader.readLine();
                while (s != null) {
                    System.out.println(s);
                    s = bufferedReader.readLine();
                }
                bufferedReader.close();
                fileReader.close();
            } catch (IOException exp1) {
                System.out.println("No output_file");
            }
        }

    }

}
