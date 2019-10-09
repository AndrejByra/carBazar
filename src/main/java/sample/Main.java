package sample;

import com.google.common.collect.Table;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;


public class Main extends Application {
    public static WebDriver driver;
    @FXML
    TableView<Table> tableView;

    @Override
    public void start(Stage primaryStage) throws Exception{
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.addColumn("Cars");

        Properties prop = new Properties();
        FileInputStream file = null;
        {
            try {
                file = new FileInputStream("C:\\Users\\andrej.byra\\Desktop\\carBazar\\src\\main\\resources\\dataFile.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        prop.load(file);
        String brandName = prop.getProperty("brand");
        String url = prop.getProperty("url");
        String priceF = prop.getProperty("priceF");
        String priceT = prop.getProperty("priceT");
        String yearF = prop.getProperty("yearF");
        String yearT = prop.getProperty("yearT");

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\andrej.byra\\Desktop\\carBazar\\src\\main\\java\\sample\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);

        WebElement dropdown = driver.findElement(By.id("BrandID"));
        WebElement dropdown1 = driver.findElement(By.id("price_from"));
        WebElement dropdown2 = driver.findElement(By.id("price_to"));
        WebElement dropdown3 = driver.findElement(By.id("made_from"));
        WebElement dropdown4 = driver.findElement(By.id("made_to"));


        Select select = new Select(dropdown);
        Select select1 = new Select(dropdown1);
        Select select2 = new Select(dropdown2);
        Select select3 = new Select(dropdown3);
        Select select4 = new Select(dropdown4);


        select.selectByVisibleText(brandName);
        select1.selectByValue(priceF);
        select2.selectByValue(priceT);
        select3.selectByValue(yearF);
        select4.selectByValue(yearT);
        driver.findElement(By.id("searchgo")).click();
        driver.findElement(By.xpath("//*[@id=\"savepreference\"]/div/span/img")).click();
        driver.findElement(By.id("setup_display_on_page")).clear();
        driver.findElement(By.id("setup_display_on_page")).sendKeys("50");
        driver.findElement(By.id("setup_display_on_page")).sendKeys(Keys.ENTER);

        List<WebElement> elements =  driver.findElements(By.className("cnt-box2"));
        List<WebElement> image = driver.findElements(By.tagName("img"));



        for (int i=1; i<elements.size();i++){
                System.out.println(elements.get(i).getText());
                model.insertRow(i - 1, new Object[]{elements.get(i).getText()});
                System.out.println(image.get(i).getAttribute("src"));

        }


        driver.close();
        JFrame f = new JFrame();
        f.setSize(1920,1080);
        f.add(new JScrollPane(table));
        f.setVisible(true);
    }


    public static void main(String[] args) throws IOException, InterruptedException {

        launch(args);

    }
}
