
import java.awt.HeadlessException;
import javax.swing.JFrame;


public class OyunEkranı extends JFrame{

    public OyunEkranı(String title) throws HeadlessException {
        super(title);
    }
    
    public static void main(String[] args) {
        
        OyunEkranı oyunEkranı = new OyunEkranı("Uzay Oyunu"); // jframe oluşturduk
        
        oyunEkranı.setResizable(true); // boyutlandırılabilir olmayacak
        oyunEkranı.setFocusable(false); // işlemlerimizin (tuşa basma) jframe değil, jpanelde olmasını istiyoruz
        oyunEkranı.setSize(800, 600);  // boyut ayarlama
        oyunEkranı.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // çarpıya basınca kapansın dedik
        
        Oyun oyun = new Oyun(); // jpanel oluşturduk
        
        oyun.requestFocus();  // klavyedeki işlemlerin odağı yapma
        oyun.addKeyListener(oyun); // kılavyeden işlemleri alma ve klavyemiz sadece jpanel üzerinde çalışcak
        oyun.setFocusable(true);  // odak jpanelde
        oyun.setFocusTraversalKeysEnabled(false); // jpanelin klavye işlemlerini anlaması için
        
        oyunEkranı.add(oyun); // jframe'e, jpanel ekledik
        oyunEkranı.setVisible(true); // görünürlük
        
    }
    
}
