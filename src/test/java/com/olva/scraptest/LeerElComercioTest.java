/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olva.scraptest;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author chris
 */
public class LeerElComercioTest {

    private final static String URL = "https://elcomercio.pe/politica/partidos/frepap-el-pescadito-y-la-corriente-una-cronica-de-fernando-vivas-congreso-noticia/";
    private final static List<String> SELECTORS_BANNER = Arrays.asList("div[role=banner] .story-header__header-title", "div[role=banner] .sht");
    private final static List<String> SELECTORS_IMAGEN = Arrays.asList(".story-content picture", ".s-multimedia picture");
    private final static List<String> SELECTORS_ARTICULO = Arrays.asList("#contenedor");

    @Ignore
    @Test
    public void testLeerFromFile() {
        File file = new File("C:\\Users\\chris\\Documents\\main.html");
        try {
            Document main = Jsoup.parse(file, null);
            Element banner = main.select("div[role=banner] .sht").get(0);
            System.out.println(banner.html());
        } catch (IOException ex) {
            Logger.getLogger(LeerElComercioTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getSelector(Document doc, List<String> selectores){
        for(String selector: selectores){
            Elements elements = doc.select(selector);
            if(elements.size() > 0){
                return elements.get(0).html();
            }
        }
        return "";
    }

    @Test
    public void testLeer() {
        File origen = new File("src/test/resource/main.html");
        File destino = new File("C:\\Users\\chris\\Documents\\main2.html");
        try {
            Document noticia = Jsoup.connect(URL).get();
            Document plantilla = Jsoup.parse(origen, null);
            Element divImagen = plantilla.select("#imagen-a-insertar").get(0);
            divImagen.append(getSelector(noticia, SELECTORS_IMAGEN));
            Element divBanner = plantilla.select("#banner").get(0);
            divBanner.append(getSelector(noticia, SELECTORS_BANNER));
            Element contenedor = plantilla.select("#contenedor").get(0);
            contenedor.append(getSelector(noticia, SELECTORS_ARTICULO));
            FileUtils.writeStringToFile(destino, plantilla.html(), "UTF-8");

            if (!Desktop.isDesktopSupported()) {
                System.out.println("Desktop is not supported");
                return;
            }

            Desktop desktop = Desktop.getDesktop();
            if (destino.exists()) {
                desktop.open(destino);
            }
        } catch (IOException ex) {
            Logger.getLogger(LeerElComercioTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
