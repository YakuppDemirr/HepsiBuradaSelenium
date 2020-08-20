import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class HomePage extends BasePage {

    public HomePage() throws IOException {
    }

    @BeforeScenario
    public void hazirla() {
        System.setProperty("webdriver.chrome.driver", "file/chromedriver.exe");
        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-popup-blocking");
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Step("AnaSayfaya Git")
    public void anaSayfaAc() {
        logger.info("Anasayfa açılıyor...");
        driver.get("https://www.hepsiburada.com/");
    }

    @Step("Kullanici Girişi Yap")
    public void GirisYap() {
        logger.info("Kullanıcı girişi yapılıyor...");
        hoverElement(By.cssSelector("div[id='myAccount']"));
        clickById("login");
        setById("txtUserName", ConstantsPage.USER_NAME);
        setById("txtPassword", ConstantsPage.PASSWORD);
        clickById("btnLogin");
    }

    @Step("Login Kontrol")
    public void loginKontrol() {
        logger.info("Login başarılı. Sepet tutar kontrolü yapılacak...");
        clickById("shoppingCart");
    }

    @Step("Sepet Kontrol")
    public void sepetKontrol() {
        logger.info("Sepet tutar kontrolü yapılıyor...");
        logger.info("Sepette ürün yok.");
        waitBySeconds(1);
        logger.info("Anasayfaya yönlendiriliyor...");
        driver.get("https://www.hepsiburada.com/");
    }

    @Step("Rastgele Kategori Sec")
    public void rastgeleKategoriSec() {
        logger.info("Rastgele bir kategori seçiliyor...");
        optionClick("li[class='MenuItems-1-U3X']", 0);
        logger.info("Rastgele bir kategori seçildi...");
        logger.info("Rastgele bir alt kategori seçiliyor...");
        optionClick("div[class='ChildMenuItems-3m2LI']>ul[class='ChildMenuItems-3m2LI']", 0);
        logger.info("Rastgele bir alt kategori seçildi...");
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,550)");
    }

    @Step("Rastgele Marka Sec")
    public void markaSec() {
        logger.info("Rastgele bir marka seçiliyor...");
        optionClick("ol[class='show-all-brands scrollable-filter-container scroll-lock']>li[class='unselected']", 5);
        logger.info("Rastgele bir marka seçildi...");
    }

    @Step("Fiyat Araligi Belirle")
    public void fiyatBelirle() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,650)");
        logger.info("Fiyat aralığı giriliyor..");
        waitBySeconds(1);
        setByXpath("//input[@placeholder='En az']", "1000");
        waitBySeconds(1);
        setByXpath("//input[@placeholder='En çok']", "8000");
        clickElementCssSelector("div[class='range-contain-row right']>button[class='button small']");
        jse.executeScript("window.scrollBy(0,550)");
    }

    @Step("Rastgele Urun Sec")
    public void urunSec() {
        try {
            logger.info("Rastgele bir ürün seciliyor...");
            optionClick("li[class='search-item col lg-1 md-1 sm-1  custom-hover not-fashion-flex']", 5);
            logger.info("Rastgele bir ürün seçildi...");
            String urunAdi = driver.findElement(By.cssSelector("h1[id='product-name']")).getText();
            String tutar = driver.findElement(By.cssSelector("span[id='offering-price']>span[data-bind=\"markupText:'currentPriceBeforePoint'\"]")).getText();
            writer.write("Urun Adi: " +urunAdi);
            logger.info("Ürün adı bilgisi csv dosyasına yazdırıldı...");
            writer.write("Urun tutari: " +tutar);
            writer.flush();
            logger.info("Tutar bilgisi csv dosyasına yazdırıldı...");
            System.out.println("Ürün Adı: " + urunAdi);
            System.out.println("Toplam Tutar: " + tutar);
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollBy(0,250)");
            logger.info("Seçilen ürün sepete ekleniyor...");
            clickById("addToCart");
            logger.info("Seçilen ürün sepete eklendi...");
            waitBySeconds(1);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Step("Sepete Git")
    public void sepeteGit()  {
        try {
            clickElementCssSelector("div[class='SalesFrontCash-3zIbr hb_sfc_close']");
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollBy(0,-100)");
            logger.info("Sepet kontrol edilecek...");
            clickById("shoppingCart");
            logger.info("Sepete gidildi...");
            clickElementCssSelector("button[class='increase']");
            logger.info("Sepetteki ürün bir adet arttırıldı...");
            waitBySeconds(2);
            clickElementCssSelector("button[class='increase']");
            logger.info("Sepetteki ürün bir adet arttırıldı...");
            waitBySeconds(2);
            String toplamTutar = driver.findElement(By.cssSelector("div[class='price']>strong[data-bind='text: cartItemTotalPriceWithoutDiscount']")).getText();
            String kargoUcreti = driver.findElement(By.cssSelector("div[class='price']>strong[data-bind='text: shippingPrice']")).getText();
            writer.write("Toplam Ucret: " +toplamTutar);
            logger.info("Ürün Toplam Değeri csv dosyasına yazdırıldı...");
            writer.write("Toplam Kargo ucreti: " +kargoUcreti);
            writer.flush();
            writer.close();
            logger.info("Toplam Kargo Değeri csv dosyasına yazdırıldı...");
            System.out.println("Toplam Ücret: " + toplamTutar);
            System.out.println("Toplam Kargo Ucreti: " + kargoUcreti);
            clickElementCssSelector("i[class='icon-chevron-right']");
            logger.info("Teslimat adresi sayfasına gidiliyor...");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Step("Adres Ekle")
    public void adresEkle() {
        logger.info("Adres Ekranına yönlendirildi...");
        clickElementCssSelector("div[class='col delivery-addresses']>div[class='selectbox-header']>a[class='link-type-two']");

        logger.info("Adres bilgileri giriliyor...");
        setById("first-name", "Yakup");
        setById("last-name", "Demir");

        clickElementCssSelector("button[data-id='city']>span[class='filter-option pull-left']");
        setByXpath("//*[@id=\"form-address\"]/div/div/section[2]/div[2]/div/div/div/div/input","Adana");
        clickByxpath("//span[text()='Adana']");

        clickElementCssSelector("button[data-id='town']>span[class='filter-option pull-left']");
        setByXpath("//*[@id=\"form-address\"]/div/div/section[2]/div[3]/div/div/div/div/input","SEYHAN");
        clickByxpath("//span[text()='SEYHAN']");

        clickElementCssSelector("button[data-id='district']>span[class='filter-option pull-left']");
        setByXpath("//*[@id=\"form-address\"]/div/div/section[2]/div[4]/div/div/div/div/input","NARLICA");
        clickByxpath("//span[text()='NARLICA']");

        setById("address", "Adana-Seyhan");
        setById("address-name", "Ev Adresi");
        setById("phone", "+905423604875");

        clickElementCssSelector("button[class='btn btn-primary btn-save-shipping-address']>i[class='icon-chevron-right']");
        logger.info("Adres bilgileri eklendi...");
        clickElementCssSelector("button[class='btn btn-primary full']>i[class='icon-chevron-right']");
    }

    @Step("Odeme Bilgileri")
    public void odemeBilgileri() {
        logger.info("Ödeme Ekranına yönlendiriliyor...");
        clickElementCssSelector("a[class='accordion-title paymentType-1']");
        setById("card-no", "1234567812345678");
        setById("holder-Name", "YAKUP DEMIR");
        //dropdownSelect("select[name='expireMonth']","01");
        //dropdownSelect("select[name='expireYear']","2024");
        setById("cvc", "123");
        logger.info("Ödeme bilgileri girildi ana sayfaya yönlendiriliyor...");
        driver.get("https://www.hepsiburada.com/");
    }

    @Step("Sepet Bosalt")
    public void sepetBosalt() {
        logger.info("Sepet ekranına yönlendiriliyor...");
        clickById("shoppingCart");
        clickByxpath("//a[text() = 'Sil']");
        //clickElementCssSelector("div[class='utils']>a[class='btn-delete']");
        logger.info("Sepet boşaltıldı...");
    }

    @Step("Adres Sil")
    public void adresSil() {
        logger.info("Adreslerim ekranına yönlendiriliyor...");
        hoverElement(By.cssSelector("div[id='myAccount']"));
        clickElementCssSelector("div[class='usersProcess']>ul>li>a[href='http://www.hepsiburada.com/hesabim']");
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,200)");
        waitBySeconds(2);
        driver.get("https://www.hepsiburada.com/ayagina-gelsin/teslimat-adreslerim");
        //clickElementCssSelector("div[class='NavigationMenuItem-1CvRp']>a[href='https://www.hepsiburada.com/ayagina-gelsin/teslimat-adreslerim']");
        jse.executeScript("window.scrollBy(0,200)");
        optionClick("a[data-bind='click: openDelete']", 0);
        optionClick("a[class='btn btn-md']", 0);
        logger.info("Adreslerim silindi...");
        logger.info("Otomasyon tamamlandı...");
    }

    @AfterScenario
    public void bitir() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }
}
