import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.*;
import java.nio.file.FileSystem;
import java.util.*;

public class CardReader {

    static  ArrayList<String> dbFiles;
    static  ArrayList<String> dbFilesToRead;

    public static void main(String[] args) throws IOException {
        if(args.length > 0) {
            dbFiles = new ArrayList<>();
            dbFilesToRead = new ArrayList<>();
           // System.out.println(System.getProperty("user.dir") + File.separator + "dbCards");
            final File folder = new File("dbCards");
            listFilesForFolder(folder);
         //   checkCard("t2.png");
            final File folderToRead = new File(args[0]);
            ParseFolder(folderToRead, args[0]);
            ReadAllFiles();
        }
        else System.out.println("Please enter params");

      //  System.out.println(args[0]);


    }

    private static void ReadAllFiles() throws IOException {
        for (String fileStr : dbFilesToRead)
        {
            checkCard(fileStr);
        }
    }

    private static void ParseFolder(final File folder, String folderPath) {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                ParseFolder(fileEntry, folderPath);
            } else {
                dbFilesToRead.add(folderPath + File.separator + fileEntry.getName());
            }
        }
    }

    public static void checkCard(String cardImage) throws IOException {
        int count = getCountCard(cardImage);
        int x = 150;
        String info = cardImage + " ";
        for(int i = 0; i < count; i++)
        {
            writeImage(x,595, 30, cardImage, "showCheck" + i + ".png", "txtFile" + i + ".txt");
            info += getDifferenceCard("txtFile" + i + ".txt");
            x = x + 72;
        }
        System.out.println(info);
    }

    public static void listFilesForFolder(final File folder) {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                dbFiles.add(fileEntry.getName());
            }
        }
    }

    static String getDifferenceCard(String fileCheck) throws FileNotFoundException {
        String diff = "";
        int[][] result = FileCardReader(fileCheck);
        int smallDiff = 200000;
        for (String fileStr : dbFiles)
        {
            int[][] file = FileCardReader("dbCards\\" + fileStr);
            int ArEqualCard = ArEqualCard(result, file);
            if(smallDiff > ArEqualCard)
            {
                smallDiff = ArEqualCard;
//                diff = fileStr + " " + ((100000 - ArEqualCard(result, file)) / 1000) + "% |";
                diff = fileStr;
            }

        }
        return diff;
    }

    private static int[][] FileCardReader(String filePath) throws FileNotFoundException, FileNotFoundException {
        int[][] fileArray = new int[900][3];
        File file = new File(filePath);
        if (file.isFile()) {
            Scanner scanner = new Scanner(file);
            String sampleString = "";
            int lineNum = 0;
            while (scanner.hasNextLine()) {
                sampleString = scanner.nextLine();
                String[] stringArray = sampleString.split(",");
                int[] intArray = new int[stringArray.length];
                for (int i = 0; i < stringArray.length; i++) {
                    String numberAsString = stringArray[i];
                    intArray[i] = Integer.parseInt(numberAsString);
                }
                fileArray[lineNum] = intArray;
                lineNum++;
            }
            scanner.close();
        }
        else
            System.out.println("Not a File");
        return fileArray;
    }

    static int getCountCard(String cardImg) throws IOException {
        int count = 0;
        File file = new File(cardImg);
        BufferedImage image = ImageIO.read(file);
        Raster raster = image.getRaster();
        int start = 150;
        for(int i = 0; i < 6; i++)
        {
            int[] pixel = raster.getPixel(start, 589 ,new int[4]);
            if(pixel[0] == 255 && pixel[1] == 255 && pixel[2] == 255)
                count++;
            start = start + 72;
        }
        return count;

    }

    static void writeImage(int x, int y, int h , String fileName, String fileToWrite, String txtIntFile) throws IOException {

        File file = new File(fileName);
        BufferedImage image = ImageIO.read(file);
        WritableRaster raster = image.getRaster();
        String fileStr = "";
        for(int i = x; i < x+ h; i++)
        {
            for(int t = y; t < y+ h; t++)
            {
                int[] pixel = raster.getPixel(i,t,new int[4]);
                fileStr +=  pixel[0] + "," + pixel[1] + "," + pixel[2] + "\n";
                pixel[0] = 255;
                pixel[1] = 0;
                pixel[2] = 0;
                raster.setPixel(i,t,pixel);
            }

        }
        writeFileString(txtIntFile, fileStr);
        image.setData(raster);
        ImageIO.write(image,"png", new File(fileToWrite));
    }

    public static void writeFileString (String filename, String text) throws IOException{
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        outputWriter.write(text);
        outputWriter.flush();
        outputWriter.close();
    }

    public static void writeIntArray (String filename, int[]x) throws IOException{
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < x.length; i++) {
            outputWriter.write(x[i]+"");
            outputWriter.write(Integer.toString(x[i]));
            outputWriter.newLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    public static boolean ArEqual(final int[][] arr1, final int[][] arr2) {
        if (arr1 == null) {
            return (arr2 == null);
        }
        if (arr2 == null) {
            return false;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (!Arrays.equals(arr1[i], arr2[i])) {
                return false;
            }
        }
        return true;
    }

    public static int ArEqualCard(final int[][] arr1, final int[][] arr2) {
        int dif = 0;
        if (arr1 == null) {
            return dif;
        }
        if (arr2 == null) {
            return dif;
        }
        if (arr1.length != arr2.length) {
            return dif;
        }

        for (int i = 0; i < arr1.length; i++) {
            for (int y = 0; y < arr1[i].length; y++) {
                if(arr1[i][y] != arr2[i][y])
                {
                    if(arr1[i][y] > arr2[i][y])
                        dif += arr1[i][y] - arr2[i][y];
                    else
                        dif += arr2[i][y] - arr1[i][y];
                }
            }
        }
        return dif;
    }
}
