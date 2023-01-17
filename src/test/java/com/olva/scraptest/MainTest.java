/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olva.scraptest;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
            for (int i = 0; i < 1; i++) {
                String fileName = "C:\\Users\\chris\\Desktop\\Saint Seiya\\Next Dimension\\";
                String srcImg = "", disabled;
                System.out.println("obteniendo pagina: " + "https://sites.google.com/site/saintseiyand/capitulos/saint-seiya-next-dimension-" + FORMAT_NUMBER.format(i));
//                Document main = Jsoup.connect("https://sites.google.com/site/saintseiyand/capitulos/saint-seiya-next-dimension-" + FORMAT_NUMBER.format(i)).get();
                Document main = Jsoup.connect("https://es.novelcool.com/chapter/Saint-Seiya-Next-Dimension-Cap-tulo-96/5739868-2.html").get();
                System.out.println("obtenido");
//                Elements iframe = main.select(".sites-embed-content.sites-embed-type-punch iframe");
                Elements imagenes = main.select(".site-content.mangaread-img.pic_box img");
//                String src = iframe.attr("src");
                System.out.println("cantidad de imagenes: " + imagenes.size());
                Iterator<Element> elements = imagenes.iterator();
//                while (elements.hasNext()) {                    
//                    Element element = elements.next();
//                    element.
//                }
//                System.out.println("obteniendo frame: " + src);
//                ghostDriver.get(src);
//                System.out.println("obtenido");
//                int nombreImg = 0;
//                do {
//                    Document frame = Jsoup.parse(ghostDriver.getPageSource());
//                    Elements imagenes = frame.select(".punch-viewer-svgpage-svgcontainer image");
//                    Element imagen = imagenes.last();
//                    String imageName = fileName + "\\" + FORMAT_NUMBER.format(i) + "-" + FORMAT_NUMBER.format(nombreImg++) + ".jpg";
//                    if (imagen != null) {
//                        srcImg = imagen.attr("xlink:href");
//                        System.out.println(srcImg);
//                        BufferedImage image = null;
//                        try {
//                            URL url = new URL(srcImg);
//                            image = ImageIO.read(url);
//                            ImageIO.write(image, "jpg", new File(imageName));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }else{
//                        try {
//                            ImageIO.write(imageNotFound, "jpg", new File(imageName));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    List<WebElement> botones = ghostDriver.findElements(By.cssSelector(".goog-inline-block.goog-flat-button"));
//                    WebElement boton = botones.get(3);
//                    disabled = boton.getAttribute("aria-disabled");
//                    System.out.println(disabled);
//                    boton.click();
//                } while (!Boolean.valueOf(disabled));
            }
        } catch (IOException ex) {
            Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ghostDriver.quit();
        }
    }

}
