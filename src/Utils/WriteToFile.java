package Utils;

import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WriteToFile {

    public static void simpleLongFileWrite(String filename, ArrayList<Long> data)
    {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            String printFile = "";
            for(int i = 0; i < data.size()-1; i++)
            {
                printFile+=data.get(i) + ",";
            }

            if(data.size()>0)
            {
                printFile+=data.get(data.size()-1);
            }
            System.out.println(printFile);
            writer.println(printFile);
            writer.close();
        } catch (IOException e)
        {
            System.out.println("File Write Error: " + filename);
        }
    }
}
