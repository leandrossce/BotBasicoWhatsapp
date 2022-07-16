package third;
import org.apache.commons.io.FileUtils;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import javax.swing.JOptionPane;
import java.security.SecureRandom;
import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BotBasicoWhatsapp
{

public String status = "Não conectou";
long VarRandomico=0;
long numeroapostas=0;

          
    public static void main(String[] args) throws IOException, WriteException, BiffException,Exception {
        BotBasicoWhatsappcon = new BotBasicoWhatsapp();
        con.getConnectionFactory();
       
    }


    public BotBasicoWhatsapp() {
        //this.getConnectionFactory();
    }

    
    public void getConnectionFactory ()throws Exception {
   
  
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Gabriel\\Desktop\\chromedriver_win32\\chromedriver.exe");
        String baseUrl;
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        baseUrl = "https://web.whatsapp.com/";

        driver.get(baseUrl);
            
            
            
        Connection connection = null;       //usada para o banco de dados
             
        String status;     //status da conexão com o banco de dados
            
        connection = DriverManager.getConnection("jdbc:firebirdsql:localhost/3050:C:\\megasena\\MEGAPOSSIBILIDADES.FDB","SYSDBA","masterkey");
        
            if(connection !=null) {
                status = ("Status -> Conectado com Sucesso!");
                System.out.println(status);
            } 
            else {
                status = ("Status -> Não foi possível realizar a conexão");
            }
                
            if (connection == null) {
                throw new IllegalStateException("Conexao nula. Não foi possível efetuar a conexao.");
            }
                 

        Scanner entrada = new Scanner (System.in);  // aguarda que seja selecionado o grupo correto
        System.out.print("Selecione o grupo de Whatsapp e pressione enter: ");
        String nome = entrada.nextLine();       // grupo correto selecionado


        int size=0;
        String digitacao;

     //   driver.findElement(By.xpath("//*[@id=\"main\"]/div[3]/div/div[2]/div[1]")).click();  //clica na tela principal para capturar eventos     
        
        List<WebElement> TextoWhatsappDigitado = driver.findElements(	By.xpath("//*[@id='main']//div//span[@class='_2YPr_ i0jNr selectable-text copyable-text']")); /// textos copiados dentro da caixa de diálogo
        List<WebElement> EventosDeUsuarios = driver.findElements(	By.xpath("//*[@id='main']//div/span[@class='i0jNr']")); // retorna hoje, saiu, entrou, etc.
        
        

        
                    
                    
        for(;;)        // varredura permanente do whatsapp
        {
            //inicio de bloco de atualização de textos digitados
            // TextoWhatsappDigitado vai acumulando todos os textos digitados pelos usuários
            // Quando a tela é limpa, fica zerado o conteúdo ->TextoWhatsappDigitado.clear();
            
            
            // para alternar campo de digitação de texto (notou-se que atualiza o processamento de eventos)
            driver.findElement(By.xpath("/html//div[@id='main']/footer[@class='_2cYbV']//div[@class='p3_M1']/div/div[@role='textbox'] ")).click(); // ativar campo para digitar mensagem
     
            
            try{        // esse bloco percorre todos os textos digitados no whatsapp e apaga quando todos são percorridos
                TextoWhatsappDigitado = driver.findElements(	By.xpath("	//*[@id='main']//span[@class='i0jNr selectable-text copyable-text']")); /// textos copiados dentro da caixa de diálogo
                size = TextoWhatsappDigitado.size();
                
                WebElement OKBtnElement;    //elemento para cliques
                JavascriptExecutor executor; //elemento para cliques
                
                for(int i=0;i<size;i++){        // LOOP RESPONSÁVEL POR PERCORRER TODOS OS TEXTOS DIGITADOS
                    System.out.println(TextoWhatsappDigitado.get(i).getText());
                
                }
                
                EventosDeUsuarios = driver.findElements(	By.xpath("//*[@id='main']//div/span[@class='i0jNr']")); // retorna hoje, saiu, entrou, etc.
                size = EventosDeUsuarios.size();
                
                for(int i=0;i<size;i++){        // LOOP RESPONSÁVEL POR PERCORRER TODOS EVENTOS DE ENTRADA E SAIDA DE USUÁRIOS
                    if(EventosDeUsuarios.get(i).getText().contains("entrou")){
                        System.out.println(EventosDeUsuarios.get(i).getText());
                        System.out.println("Seja bem vindo(a) " + EventosDeUsuarios.get(i).getText()"); 
                     }
                    
                     if(EventosDeUsuarios.get(i).getText().contains("saiu")){     
                        driver.findElement(By.xpath("/html//div[@id='main']/footer[@class='_2cYbV']//div[@class='p3_M1']/div/div[@role='textbox']")).sendKeys( (EventosDeUsuarios.get(i).getText() + " :/ "));
                        driver.findElement(By.xpath("//*[@id='main']//button[@class='tvf2evcx oq44ahr5 lb5m6g5c svlsagor p2rjqpw5 epia9gcq']")).click();	// enviar mensagem
                     
                    }
                }                
                 
                
                if(TextoWhatsappDigitado.size()>=1 || EventosDeUsuarios.size()>=1){        // APÓS PERCORRER TODOS OS TEXTOS, ESSE BLOCO DELETA AS MENSAGENS DO WHATSAPP DE FORMA QUE FIQUE APENAS MENSAGEN NÃO PROCESSADAS
                    Thread.sleep(100);  // continuar/sim limpar
                    driver.findElement(By.xpath("//*[@id=\"main\"]/header/div[3]/div/div[2]/div/div/span")).click(); // menu do botão limpar e clica

                    Thread.sleep(100);  // continuar/sim limpar
                    driver.findElement(By.xpath("/html/body/div[1]/div/span[4]/div/ul/div/div/li[5]/div[1]")).click(); // opção limpar / clica
                                                 //*[@id=\"app\"]/div/span[4]/div/ul/div/div/li[5]/div[1]
                                                 
                    OKBtnElement = driver.findElement(By.xpath(" //*[@id=\"app\"]/div/span[2]/div/div/div/div/div/div/div[3]/div/div[2]/div/div")); // há um aviso quando é clicado pela primeira vez... necssário clicar em continuar
                    executor = (JavascriptExecutor)driver;
                    Thread.sleep(100);  
                    executor.executeScript("arguments[0].click();", OKBtnElement );// continuar sim
                    
                    
                    
                    OKBtnElement = driver.findElement(By.xpath("//*[@id=\"app\"]/div/span[2]/div/div/div/div/div/div/div[3]/div/div[2]/div/div"));// continuar/sim limpar
                    executor = (JavascriptExecutor)driver;
                    Thread.sleep(100);  // continuar/sim limpar
                    executor.executeScript("arguments[0].click();", OKBtnElement );// continuar/sim limpar
                    
                    Thread.sleep(100);
                    TextoWhatsappDigitado.clear();
                    TextoWhatsappDigitado = driver.findElements(	By.xpath("	//*[@id='main']//span[@class='i0jNr selectable-text copyable-text']")); /// textos copiados dentro da caixa de diálogo                    
                }
            }
                     
            catch(StaleElementReferenceException e){TextoWhatsappDigitado.clear();}       // quando a tela é limpa e fica sem elementos, há erro que necessita ser tratado por essa exceção
            catch(ElementClickInterceptedException e){TextoWhatsappDigitado.clear();}       // quando a tela é limpa e fica sem elementos, há erro que necessita ser tratado por essa exceção
            catch(NoSuchElementException e){TextoWhatsappDigitado.clear();} 
            
            //fim de bloco de atualização de textos digitados
            
        }

                    
    }


}  //FIM public class BotBasicoWhatsapp




