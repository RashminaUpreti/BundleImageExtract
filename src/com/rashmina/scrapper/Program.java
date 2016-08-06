/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rashmina.scrapper;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Rashu
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String link = "https://www.dreamstime.com/photos-images/male-model.html";
            URL url = new URL(link);
            // URLEncoder.encode(link,"UTF-8");
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));//, Charset.forName("UTF-8")
            String line = "";
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line);

            }
            reader.close();
            String regex ="<a href=\\\"(.*?)\\\" title=\\\"(.*?)\\\"><span><img id=\\\"(.*?)\\\" src=\\\"(.*?)\\\" alt=\\\"(.*?)\\\" title=\\\"(.*?)\\\" /></span><span id=\\\"(.*?)\\\">";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content.toString());
            while (matcher.find()) {
                String imageLink = matcher.group(4);
                URL imageUrl = new URL(imageLink);
                URLConnection conn1 = imageUrl.openConnection();
                InputStream is = conn1.getInputStream();

                String[] tokens = imageLink.split("/");
                String path = "C:\\Users\\rashu\\Desktop\\java\\output\\";
                FileOutputStream os = new FileOutputStream(path + tokens[tokens.length - 1]);
                byte[] data = new byte[1024];
                int i = 0;
                while ((i = is.read(data)) != -1) {
                    os.write(data, 0, i);
                }
                os.close();
                is.close();
            }
        } catch (IOException ioe) {

            System.out.println(ioe.getMessage());
        }

    }
}
