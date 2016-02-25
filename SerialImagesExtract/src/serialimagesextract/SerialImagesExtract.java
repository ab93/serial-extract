
package serialimagesextract;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author avik
 */
public class SerialImagesExtract 
{
    public ArrayList<String> homePage() throws Exception 
    {
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
    
        try  
        {      
            DefaultCredentialsProvider creds = new DefaultCredentialsProvider();
            
            System.out.println("Enter username: ");
            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();
            
            System.out.println("Enter password: ");
            String password = scanner.nextLine();
          
       
            creds.addCredentials(username, password);
            webClient.setCredentialsProvider(creds);
        
            final HtmlPage page = webClient.getPage("http://imagecat.dyndns.org/weapons/imagespace/#search/http%3A%2F%2Fdarpamemex%3Adarpamemex%40imagecat.dyndns.org%2Fweapons%2Falldata%2Fcom%2Fbackpage%2Fimages1%2FE052D904C7162A7747830B7F292C7351B7DC0543B903AFC976897BDDCE4BBC2F/smqtk-similarity");
        //Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
            webClient.waitForBackgroundJavaScript(30000);
            final String pageAsXml = page.asXml();
        //Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

            final String pageAsText = page.asText();
        //Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
        
            List<String> divs = new ArrayList<String>();
            divs.add(page.getByXPath("//div").toString());
            
            String [] strs=divs.get(0).split(",");
            ArrayList<String> urls = new ArrayList<String>();
        
            String patternString = "file([^,]*)";
            Pattern pattern = Pattern.compile(patternString);
        
            int j = -1;
            for(int i=0; i<strs.length; i++)
            {
                Matcher matcher = pattern.matcher(strs[i]);
                while(matcher.find())
                {
                    //j++;
                    String s = matcher.group(0);
                    s = s.split(" ")[0].replace("\"","");
                    //s = s.replace("file:/data2/USCWeaponsStatsGathering/nutch/full_dump/","http://imagecat.dyndns.org/weapons/alldata/");
                    //strs[i] = s;
                    if (j==-1)
                    {    
                        j++;
                        urls.add(s);
                    }
                    
                    else if(urls.get(j) != s)
                    {
                        j++;
                        urls.add(s);
                    }
                    System.out.println(j);
                }
            
            }
            
            System.out.println("arrayList:\n");
            for(int i=0; i<urls.size();i++)
            {
                System.out.println(urls.get(i));
            }
            
            return urls;
       
        }
        catch(Exception e)
        {
            System.out.println("error");
            return null;
        }
    }
    
    public static void write(ArrayList<String> urls) throws Exception
    {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter("urlList.txt"));
        for(int i=0; i<urls.size();i++)
        {
            outputWriter.write(urls.get(i));
            outputWriter.newLine();
        }
        outputWriter.flush();
        outputWriter.close();
        
    }
    
    public void save(ArrayList<String> urls) throws Exception
    {
        //java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        
        DefaultCredentialsProvider creds = new DefaultCredentialsProvider();
        creds.addCredentials("darpamemex", "darpamemex");
        webClient.setCredentialsProvider(creds);
             
        System.out.println("serialimagesextract.SerialImagesExtract.save()");
        
        final HtmlPage page = webClient.getPage(urls.get(0));
        
        List<String> divs = new ArrayList<String>();
        divs.add(page.getByXPath("//body").toString());
        
        //List<HtmlImage> imageList = (List<HtmlImage>) page.getByXPath("//img");
        
        //HtmlImage image = page.<HtmlImage>getFirstByXPath("//img");
        //webClient.waitForBackgroundJavaScript(30000);
        //File imageFile = new File("0.jpg");
        //image.saveAs(imageFile);
        //final String pageAsText = page.asText();
        System.out.println(divs.size());
        
        try
        {
            /*for(int i=0; i<urls.length;i++)
            {
                DefaultCredentialsProvider creds = new DefaultCredentialsProvider();
                creds.addCredentials("darpamemex", "darpamemex");
                webClient.setCredentialsProvider(creds);
                
                final HtmlPage page = webClient.getPage(urls[i]);
                HtmlImage image = page.<HtmlImage>getFirstByXPath("//img");
                File imageFile = new File(i + ".jpg");
                image.saveAs(imageFile);
            }*/
            
        }
        catch(Exception e)
        {
            System.out.println("Images not saved!");
        }
    }
    
    public static void main(String[] args) 
    {
        // TODO code application logic here
        SerialImagesExtract extract = new SerialImagesExtract();
        try
        {
            //String[] urls = new String[];
            ArrayList<String> urls = extract.homePage();
            extract.write(urls);
            
            //extract.save(urls);
            
        }
        catch(Exception e)
        {}
    
    }
}
