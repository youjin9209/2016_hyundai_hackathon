package ExUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

//import org.gephi.toolkit.demos.HeadlessSimple;
//import org.gephi.toolkit.demos.mixFrame.sub;


public class MainFrame extends JFrame implements ActionListener {
   

   JButton openButton, saveButton;
   JLabel thumbnailLabel;
   ImageIcon thumb1;
   ImageIcon thumb2;
   ImageIcon thumb3;
   ImageIcon thumb4;
   ImageIcon thumb5;
   ImageIcon thumb6;
   ImageIcon thumb7;
   ImageIcon thumb8;
   ImageIcon thumb9;
   JButton thButton1,thButton2,thButton3,thButton4,thButton5,thButton6,thButton7,thButton8,thButton9;
   JFileChooser fc;
   File file;
   
   JRadioButton  rb1 = new JRadioButton("Label"); 
   
   Thumbnail tb;
   
   String fileName;
   //public String[] queue;
   
   public MainFrame(){
      
      super("메인화면");
      openButton = new JButton("Open");
      saveButton = new JButton("save");
      
      tb = new Thumbnail();
      
      System.out.println("wht");
      
      thumb1 =  new ImageIcon(tb.makingThumbnail(data.MyAddress.inputDirectory + "Koala.jpg", 400, 400));
      thumb2 = new ImageIcon(tb.makingThumbnail(data.MyAddress.inputDirectory + "Koala.jpg", 400, 400));
      thumb3 = new ImageIcon(tb.makingThumbnail(data.MyAddress.inputDirectory + "Koala.jpg", 400, 400));
      

      
      thButton1 = new JButton(thumb1);
      thButton2 = new JButton(thumb2);
      thButton3 = new JButton(thumb3);
      
        //button function
       openButton.addActionListener(this);
       thButton1.addActionListener(this);
       thButton2.addActionListener(this);
       thButton3.addActionListener(this);
//       thButton4.addActionListener(this);
       
       


     
      JPanel bottom = new JPanel();
  
      bottom.add(rb1);
      bottom.add(openButton);
      bottom.add(saveButton);
      
      rb1.addActionListener(this);
      
      JPanel center = new JPanel();
      center.setLayout(new BorderLayout());
      center.setSize(200,300);

      
      center.add("West", thButton1);
      center.add("Center", thButton2);
      center.add("East", thButton3);
      
      JPanel bbottom = new JPanel();
      bbottom.setLayout(new BorderLayout());
      bbottom.setSize(200,100);
      bbottom.add("West", new JLabel("                                                      Yifan Hu"));
      bbottom.add("Center", new JLabel("                                                                                                                               Scale"));
      bbottom.add("East", new JLabel("ForceAtlas2                                                "));
      
      center.add("South", bbottom);

//      center.add(thButton4);
//      center.add(thButton5);
//      center.add(thButton6);
//      center.add(thButton7);
//      center.add(thButton8);
//      center.add(thButton9);

      

      getContentPane().add(center,BorderLayout.CENTER);
      getContentPane().add(bottom,BorderLayout.SOUTH);
      
      thumbnailLabel = new JLabel();
      thumbnailLabel.setBorder(BorderFactory.createBevelBorder(2));
      
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(800, 800);
      setVisible(true);
      
   
   }
   


   @Override
   public void actionPerformed(ActionEvent e) {
         // TODO Auto-generated method stub
         
      try{
         Object o = e.getSource();
         if(o == openButton){
            openButton_actionPerformed(e);
         }else if(o == thButton1){
                
        
              
            SubFrame mt = new SubFrame(data.MyAddress.inputDirectory + fileName + "Yifan1.jpg", "yifan", file);
         
           
         }else if(o == thButton2){
           
           
            SubFrame mt = new SubFrame(data.MyAddress.inputDirectory + fileName + "scale1.jpg", "scale", file);
         
            
            
         }else if(o == thButton3){
            

            SubFrame mt = new SubFrame(data.MyAddress.inputDirectory + fileName + "force21.jpg", "force2", file);
            
            
         }
      }
      catch (IOException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      } 
   }

   
   

   void openButton_actionPerformed(ActionEvent o) {
       if (o.getSource() == openButton) {        

          //파일창 오픈
         fc = new JFileChooser();
         
         FileFilter filter = new FileNameExtensionFilter("gexf files","gexf");
         fc.addChoosableFileFilter(filter);
         
         int value = fc.showOpenDialog(null);
         if(value == JFileChooser.APPROVE_OPTION){
                
            file = fc.getSelectedFile();
            
            int pos = file.getName().lastIndexOf(".");
            fileName = file.getName().substring(0,pos);
            
            //gexf -> pdf 생성 & 저장
            GexfToPDF t = new GexfToPDF();
     
                   
            if (t.script(file, rb1.isSelected())){
               
               
               
                
                
                Image image = Toolkit.getDefaultToolkit().getImage(data.MyAddress.inputDirectory + fileName + "Yifan1.jpg");
                thumb1.setImage(image);
                
                Image image1 = Toolkit.getDefaultToolkit().getImage(data.MyAddress.inputDirectory + fileName + "scale1.jpg");
                thumb2.setImage(image1);
                
                Image image2 = Toolkit.getDefaultToolkit().getImage(data.MyAddress.inputDirectory + fileName + "force21.jpg");
                thumb3.setImage(image2);
                
                fileCopy(file.getPath(), data.MyAddress.inputDirectory + fileName + ".gexf");
                
                
                //파일 복사을 원한다면
               
             
             
             
           //  file.d
             /////Mainframe에 파일하나 추가
             /////배열에 추가 
            }
         }
        }

   }
   
   
   public static void fileCopy(String inFileName, String outFileName) {
      try {
       FileInputStream fis = new FileInputStream(inFileName);
       FileOutputStream fos = new FileOutputStream(outFileName);
       
       int data = 0;
       while((data=fis.read())!=-1) {
        fos.write(data);
       }
       fis.close();
       fos.close();
       
      } catch (IOException e) {
       // TODO Auto-generated catch block
       e.printStackTrace();
      }
     }

}
