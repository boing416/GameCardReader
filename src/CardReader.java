import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.*;

public class CardReader {

    static  ArrayList<String> dbFiles;

    public static void main(String[] args) throws IOException {

        dbFiles = new ArrayList<>();
        final File folder = new File("dbCards");
        listFilesForFolder(folder);
        checkCard("t33.png");

        //♣ Трефы — clubs
        //♦ Бубны — diamonds
        //♥️ Червы — hearts
        //♠ Пики — spades
//     //   System.out.println("Count card: " + getCountCard());
//
//        writeImage(150,595, 30, "test2.png", "test1.png");
//
//        System.out.println(getDifferenceCard("test.txt"));

//        int[][] result = FileCardReader("test.txt");
//        System.out.println("-----------PRINT-----------------");
//        for (int[] number : result) {
//            for (int number2 : number) {
//                System.out.print(number2 + " ");
//            }
//            System.out.println(" -- ");
//        }



       // writeImage(150,600);

       // writeImage(143,600, 1);   // begin card 1

       // writeImage(215,600, 1);     //begin card 2

      //  writeImage(286,600, 1);     //begin card 3

//        writeImage(150,608, 1);     //1 card 29 29 29
//        writeImage(230,608, 1);     //2 card 29 29 29
//        writeImage(310,608, 1);     //3 card 29 29 29
//        writeImage(380,608, 1);     //4 card 29 29 29
//        writeImage(450,608, 1);     //5 card 29 29 29
//        writeImage(520,608, 1);     //6 card 29 29 29
//        File file = new File("E:\\Projects\\Java\\ImageCardReader\\imgs\\20180821_055341.782_0x26080126.png");
//        File file = new File("smile.png");
//        BufferedImage image = ImageIO.read(file);
//
//
//        WritableRaster raster = image.getRaster();
//        System.out.println("W: " + raster.getWidth());
//        System.out.println("H: " + raster.getHeight());
//        for(int i = 0; i < raster.getWidth(); i++)
//        {
//            int[] pixel = raster.getPixel(i,0,new int[4]);
//            pixel[0] = 255;
//            pixel[1] = 0;
//            pixel[2] = 0;
//            raster.setPixel(i,0,pixel);
//        }
//        int[] pixel = raster.getPixel(150,550,new int[4]);
//        pixel[0] = 255;
//        pixel[1] = 0;
//        pixel[2] = 0;
//        raster.setPixel(50,50,pixel);
//        image.setData(raster);
//        ImageIO.write(image,"png", new File("test1.png"));
//        ImageIO.write(image, "png", new File("smile.png"));
//
//        String[] extension = ImageIO.getReaderFileSuffixes();
//        for (String str : extension)
//        {
//            System.out.println(str);
//        }
//
//        ImageReader reader = null;
//        Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("png");
//        if(iterator.has)
    }

    public static void checkCard(String cardImage) throws IOException {
        //   System.out.println("Count card: " + getCountCard());
        int count = getCountCard(cardImage);
        int x = 150;
        String info = cardImage;
        for(int i = 0; i < count; i++)
        {
            writeImage(x,595, 30, cardImage, "showCheck" + i + ".png", "txtFile" + i + ".txt");
            info += " " + getDifferenceCard("txtFile" + i + ".txt");
           // System.out.println("Check Card N" + (i+1) + ": " + getDifferenceCard("txtFile" + i + ".txt"));
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
//                System.out.println(fileEntry.getName());
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
            //System.out.println("Check:  " + fileStr);
//             if(ArEqual(result, file))
//             {
//                 diff = fileStr;
//             }
            int ArEqualCard = ArEqualCard(result, file);
            if(smallDiff > ArEqualCard)
            {
                smallDiff = ArEqualCard;
                diff = fileStr + " diff: " + ArEqualCard(result, file) + " |";
            }
          //  diff += "| " + fileStr + " diff: " + ArEqualCard(result, file) + " |";
        }
//        if(smallDiff != 200000)
//        {
//            return
//        }
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
//                System.out.println("Number of integers: " + intArray.length);
//                System.out.println("The integers are:");
//                for (int number : intArray) {
//                    System.out.println(number);
//                }
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
//                System.out.println(pixel[0] + " " + pixel[1] + " " + pixel[2]);
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
            // Maybe:
            outputWriter.write(x[i]+"");
            // Or:
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
