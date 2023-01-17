/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olva.scraptest;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author chris
 */
public class Main {

    private static String URL = "";
    private final static List<String> SELECTORS_BANNER = Arrays.asList("div[role=banner] .story-header__header-title", "div[role=banner] .sht");
    private final static List<String> SELECTORS_IMAGEN = Arrays.asList(".story-content picture", ".s-multimedia picture");
    private final static List<String> SELECTORS_ARTICULO = Arrays.asList("#contenedor");

    private static String getSelector(Document doc, List<String> selectores) {
        for (String selector : selectores) {
            Elements elements = doc.select(selector);
            if (elements.size() > 0) {
                return elements.get(0).html();
            }
        }
        return "";
    }

    public static void main(String[] args) {
        String path = System.getProperty("user.home");
        while (true) {
            System.out.println("Ingrese la URL de la pagina a ver...");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                URL = reader.readLine();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error al leer URL");
                return;
            }
            File destino = new File(path+"\\main2.html");
            try {
                Document noticia = Jsoup.connect(URL).get();
                Document plantilla = Jsoup.parse(Main.class.getResourceAsStream("/main.html"), null, "");
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
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
