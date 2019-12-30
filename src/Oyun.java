
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ateş{
    private int x;
    private int y;

    public Ateş(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}

public class Oyun extends JPanel implements KeyListener,ActionListener{  // klavye işlemlerini anlaması için keylistener yaptık. topun ve mekiğin hareketi için actionlistener yaptık
    Timer timer = new Timer(5, this); // topu hareket ettirmek için timer kullanıyoruz,timer her çalıştığında actionPerformed metodu çalışacak
    private int topX = 0; // topun x koordinatı sabit olmayıp değişeceği için tanımladık
    private int gemiX = 0; // geminin x koordinatı sabit olmayıp değişeceği için tanımladık   
    private BufferedImage image; // pngyi almak için
    private BufferedImage image2; //(ek*)
    private int topdirX = 2;
    private int harcanan_ateş = 0;
    private ArrayList<Ateş> ateşler = new ArrayList<Ateş>();
    private int geçen_süre = 0;
    
    public Oyun() {
        
        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png"))); // ımage okuma
            image2 = ImageIO.read(new FileImageInputStream(new File("space.png"))); //(ek*)
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setBackground(Color.black); // jpanelin arka kısmını siyah yaptık
        
        timer.start();
        
    }      

    @Override
    public void paint(Graphics g) {  // şekilleri çizmek için override ettik
        super.paint(g);
        double x = image2.getWidth()/1.25; // (ek*)
        g.drawImage(image2, 0, 0, (int)x,image2.getHeight(),this);  // (ek*)
        g.setColor(Color.red);
        g.fillOval(topX, 0, 20, 20);
        g.drawImage(image, gemiX, 490, image.getWidth()/10,image.getHeight()/10,this); // resim boyutunu direk imageden okuyabiliriz(this) ama büyük olacak kendimiz ayarlamamız lazım
        // g.drawImage(..... de this yaptık çünkü jpanelde olmasını istedik         
    
        for(Ateş ı : ateşler){
            if(ı.getY() <= 0){
                ateşler.remove(ı);
            }
        }
        
        g.setColor(Color.blue);
        for(Ateş ı : ateşler){
            g.fillRect(ı.getX(), ı.getY(), 15, 30);
        }
        
    }

    @Override
    public void repaint() {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) { // tuşa basınca çalışcak
        
        int c = e.getKeyCode(); // sola basınca bir değer, sağa basınca bir değer döncek
        
        if(c == KeyEvent.VK_LEFT){ // sol ok tuşuna basılmışsa
            if(gemiX <= 0){ // hiçbirşey olmıycak
                
            }
            else{
                gemiX -= 20;
            }
        }
        else if(c == KeyEvent.VK_RIGHT){  // sağ ok tuşuna basılmışsa
            if(gemiX >= 720){ // hiçbir şey olmıycak
                
            }
            else{
                gemiX +=20;
            }
        }
        else if(c == KeyEvent.VK_CONTROL){
            ateşler.add(new Ateş(gemiX+13, 470));
            harcanan_ateş++;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        geçen_süre += 5;
        for(Ateş x : ateşler){
            x.setY(x.getY() - 5);
        }
        
        // timer her çalıştığında bu metod çalışcak
        topX += topdirX;
        
        // topumuz belli bir X koordinatına gelince geriye doğru gitmesi gerek       
        if(topX >= 750){ // en sağa geldiğinde
            topdirX = -topdirX;
        }
        
        if(topX <= 0){ // en sola gelidiğinde 
            topdirX = -topdirX;
        }
        
        if(kontrolEt()){
            JOptionPane.showMessageDialog(this, "Kazandınız...\n" + "Ateş Sayısı: " + harcanan_ateş + "\nGeçen Süre: " + geçen_süre/1000);
            System.exit(0);
        }
        
        repaint();
        
    }
    
    public boolean kontrolEt(){
        for(Ateş ı : ateşler){
            if(new Rectangle(ı.getX(),ı.getY(),15,30).intersects(new Rectangle(topX,0,20,20))){
                timer.stop();
                return true;
            }
        }
        return false;
    }

    
    
    
}
