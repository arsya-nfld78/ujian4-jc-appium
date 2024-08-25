package com.juaracoding;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class TestCatatanKeuangan {
    private DesiredCapabilities dc;
    private AndroidDriver driver;

    @BeforeClass
    // Melakukan setup device sebelum melakukan testing menggunakan desired capabilities.
    public void setUp() throws MalformedURLException {
        dc = new DesiredCapabilities();
        dc.setCapability("deviceName", "Pixel 2 API 30");
        dc.setCapability("udid", "emulator-5554");
        dc.setCapability("platformName", "android");
        dc.setCapability("platformVersion", "11");
        dc.setCapability("appPackage", "com.chad.financialrecord");
        dc.setCapability("appActivity", "com.rookie.catatankeuangan.feature.splash.SplashActivity");
        dc.setCapability("noReset", "true");
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AndroidDriver(url,dc);
    }

    @AfterClass
    public void finish(){
        delay(5);
        driver.quit();
    }

    //test fitur pemasukan diutamakan sampai test fungsi cek saldo di akhir menggunakan priority.
    @Test(priority = 1)
    public void testPemasukan(){
        delay(6); //pastikan kasih delay agar mudah terbaca elemennya

        //test pemasukan gaji
        driver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.chad.financialrecord:id/fabMenu']").click();
        delay(1);
        driver.findElementByXPath("//android.widget.TextView[@resource-id='com.chad.financialrecord:id/btnIncome']").click();
        delay(1);
        driver.findElementByXPath("//android.widget.EditText[@resource-id='com.chad.financialrecord:id/etAmount']").sendKeys("20000000");
        driver.findElementByXPath("//android.widget.EditText[@resource-id='com.chad.financialrecord:id/etNote']").sendKeys("Gaji bulanan");
        delay(1);
        driver.findElementByXPath("//android.widget.Button[@resource-id='com.chad.financialrecord:id/btSave']").click();

        //test pemasukan tabungan
        delay(3);
        driver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.chad.financialrecord:id/fabMenu']").click();
        delay(1);
        driver.findElementByXPath("//android.widget.TextView[@resource-id='com.chad.financialrecord:id/btnIncome']").click();
        delay(1);
        driver.findElementByXPath("//android.widget.Spinner[@resource-id='com.chad.financialrecord:id/spCategory']").click();
        delay(1);
        driver.findElementByXPath("//android.widget.TextView[@resource-id='com.chad.financialrecord:id/tvName' and @text='Tabungan']").click();
        driver.findElementByXPath("//android.widget.EditText[@resource-id='com.chad.financialrecord:id/etAmount']").sendKeys("5000000");
        driver.findElementByXPath("//android.widget.EditText[@resource-id='com.chad.financialrecord:id/etNote']").sendKeys("Tabungan debit");
        delay(1);
        driver.findElementByXPath("//android.widget.Button[@resource-id='com.chad.financialrecord:id/btSave']").click();

        //sebelum melakukan assert, klik widget untuk masuk ke bagian rincian transaksi
        delay(3);
        driver.findElementByXPath("//android.widget.ExpandableListView[@resource-id='com.chad.financialrecord:id/elTransaction']/android.widget.RelativeLayout/android.widget.LinearLayout").click();

        //assert untuk mengecek pemasukan yang sudah terhitung
        String actualPemasukan = driver.findElementByXPath("//android.widget.TextView[@resource-id='com.chad.financialrecord:id/tvIncome']").getText();
        Assert.assertEquals(actualPemasukan,"25.000.000","Pemasukan Sesuai");

        //kembali ke halaman utama
        driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    @Test(priority = 2)
    public void testPengeluaran(){
        delay(3);
        //test pengeluaran pajak
        driver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.chad.financialrecord:id/fabMenu']").click();
        delay(1);
        driver.findElementByXPath("//android.widget.Spinner[@resource-id='com.chad.financialrecord:id/spCategory']").click();
        delay(1);
        driver.findElementByXPath("//android.widget.TextView[@resource-id='com.chad.financialrecord:id/tvName' and @text='Pajak']").click();
        driver.findElementByXPath("//android.widget.EditText[@resource-id='com.chad.financialrecord:id/etAmount']").sendKeys("1500000");
        driver.findElementByXPath("//android.widget.EditText[@resource-id='com.chad.financialrecord:id/etNote']").sendKeys("Pajak PBB");
        delay(1);
        driver.findElementByXPath("//android.widget.Button[@resource-id='com.chad.financialrecord:id/btSave']").click();

        //test pengeluaran pulsa
        delay(1);
        driver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.chad.financialrecord:id/fabMenu']").click();
        delay(1);
        driver.findElementByXPath("//android.widget.Spinner[@resource-id='com.chad.financialrecord:id/spCategory']").click();
        delay(1);
        driver.findElementByXPath("//android.widget.TextView[@resource-id='com.chad.financialrecord:id/tvName' and @text='Pulsa']").click();
        driver.findElementByXPath("//android.widget.EditText[@resource-id='com.chad.financialrecord:id/etAmount']").sendKeys("110000");
        driver.findElementByXPath("//android.widget.EditText[@resource-id='com.chad.financialrecord:id/etNote']").sendKeys("Paket bulanan");
        delay(1);
        driver.findElementByXPath("//android.widget.Button[@resource-id='com.chad.financialrecord:id/btSave']").click();

        //test pengeluaran tagihan
        driver.findElementByXPath("//android.widget.ImageButton[@resource-id='com.chad.financialrecord:id/fabMenu']").click();
        delay(1);
        driver.findElementByXPath("//android.widget.Spinner[@resource-id='com.chad.financialrecord:id/spCategory']").click();
        delay(1);
        driver.findElementByXPath("//android.widget.TextView[@resource-id='com.chad.financialrecord:id/tvName' and @text='Tagihan']").click();
        driver.findElementByXPath("//android.widget.EditText[@resource-id='com.chad.financialrecord:id/etAmount']").sendKeys("2500000");
        driver.findElementByXPath("//android.widget.EditText[@resource-id='com.chad.financialrecord:id/etNote']").sendKeys("Tagihan PLN dan PDAM");
        delay(1);
        driver.findElementByXPath("//android.widget.Button[@resource-id='com.chad.financialrecord:id/btSave']").click();

        //mengarah ke halaman rincian transaksi
        driver.findElementByXPath("//android.widget.ExpandableListView[@resource-id='com.chad.financialrecord:id/elTransaction']/android.widget.RelativeLayout/android.widget.LinearLayout").click();

        //assert untuk mengecek pengeluaran yang sudah terhitung
        String actualPemasukan = driver.findElementByXPath("//android.widget.TextView[@resource-id='com.chad.financialrecord:id/tvExpense']").getText();
        Assert.assertEquals(actualPemasukan,"4.110.000","Pengeluaran Sesuai");

        //kembali ke halaman utama
        driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    @Test(priority = 3)
    public void testCekSaldo(){
        delay(3);
        //mengarah ke halaman rincian transaksi
        driver.findElementByXPath("//android.widget.ExpandableListView[@resource-id='com.chad.financialrecord:id/elTransaction']/android.widget.RelativeLayout/android.widget.LinearLayout").click();

        //cek total saldo menggunakan assert
        String actualSaldo = driver.findElementByXPath("//android.widget.TextView[@resource-id='com.chad.financialrecord:id/tvBalance']").getText();
        Assert.assertEquals(actualSaldo,"20.890.000","Saldo Sesuai");
    }

    // inisiasi method delay
    public static void delay(long detik){
        try {
            Thread.sleep(detik*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
