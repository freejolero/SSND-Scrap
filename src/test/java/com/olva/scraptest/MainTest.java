package com.olva.scraptest;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

/**
 *
 * @author chris
 */
public class MainTest {

    private final static DecimalFormat FORMAT_NUMBER = new DecimalFormat("00");

    @Test
    public void testMain() {
        System.out.println("main");
        WebDriverManager.phantomjs().setup();
        WebDriver ghostDriver = new PhantomJSDriver();

        BufferedImage imageNotFound = null;
        try {
            URL url = new URL("https://onlinezebra.com/wp-content/uploads/2019/01/error-404-not-found.jpg");
            imageNotFound = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for (int i = 97; i <= 102; i++) {
                String fileName = "C:\\Users\\chris\\Desktop\\Saint Seiya\\Next Dimension\\";
                String disabled;
                for (int j = 1; j < 2; j++) {
                    System.out.println("obteniendo pagina: " + "https://www.leercapitulo.com/read/saint-seiya-next-dimension/" + FORMAT_NUMBER.format(i) + "/#" + j);
                    Document main = Jsoup.connect("https://www.leercapitulo.com/read/saint-seiya-next-dimension/" + FORMAT_NUMBER.format(i) + "/#" + j)
                            .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                            .get();
                    System.out.println("obtenido");
                    int nombreImg = 0;
                    Elements imageElements = main.select("#arraydata");
                    String imageUrls = imageElements.text();
                    System.out.println("imagenes:");
                    System.out.println(imageUrls);
                    if(imageUrls != null && !imageUrls.isEmpty()){
                        String[] images = imageUrls.split(",");
                        for (int k = 0; k < images.length; k++) {
                            String srcImg = images[k];
                            String imageName = fileName + "\\" + FORMAT_NUMBER.format(i) + "-" + FORMAT_NUMBER.format(nombreImg++) + ".jpg";
                            System.out.println(srcImg);
                            try {
                                URL url = new URL(srcImg);
                                URLConnection urlConnection = url.openConnection();
                                urlConnection.setRequestProperty("User-Agent", "NING/1.0");
                                BufferedImage imageB = ImageIO.read(urlConnection.getInputStream());
                                ImageIO.write(imageB, "jpg", new File(imageName));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ghostDriver.quit();
        }
    }

}
