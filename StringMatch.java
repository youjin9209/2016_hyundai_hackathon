

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;





import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class StringMatch {
   
   int count;
   
   public File stringMatch(File originFile, File file) throws TransformerException, SAXException, IOException, ParserConfigurationException{
      
      //origin load
   
      count = 0;
      
      
       //DOM Document 객체 생성하기 위한 메서드 
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        //DOM 파서로부터 입력받은 파일을 파싱하도록 요청
        DocumentBuilder parser = f.newDocumentBuilder();
        
        //XML 파일 지정
        String url = originFile.getPath();
    
        System.out.println(url +"dddddddddddd");
        
        Document originDoc = null;
        Document fileDoc = null;
        
        //DOM 파서로부터 입력받은 파일을 파싱하도록 요청 
        originDoc = parser.parse(url);
        fileDoc = parser.parse(file.getPath());
        
        //루트 엘리먼트 접근 
        Element originroot = originDoc.getDocumentElement();
        Element fileroot = fileDoc.getDocumentElement();

        
        //하위 엘리먼트 접근
        NodeList n1 = originroot.getElementsByTagName("node");
        NodeList n2 = fileroot.getElementsByTagName("node");
        
        Node originNode;
        Node fileNode;
        
        Element originElement;
        Element fileElement;
        
        for( int i = 0; i < n1.getLength() ; i++ ){
  
           
              originNode = n1.item(i);
              fileNode = n2.item(i);
           
              originElement = (Element)originNode;
              fileElement = (Element)fileNode;
           
      
      
              if (!originElement.getAttribute("label").equals(fileElement.getAttribute("label"))){
                 //내용물이 다른 경우\
                 count++;
                 // 해당  label에 추가
                // fileElement.setAttribute("color", "RED");
                 fileElement.setAttribute("label", fileElement.getAttribute("label") + "zA" + "RED");
                // fileElement.setAttribute(name, value);
                //fileElement.setTextContent("RED");
                 System.out.println("color : " + fileElement.getAttribute("color"));
                 
              }else {
                 //fileElement.setAttribute("color", "BLUE");
             // fileElement.setTextContent("BLUE");
              fileElement.setAttribute("label", fileElement.getAttribute("label") + "zA" + "BLUE");
              }
              
              
           
        }
        
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        t.setOutputProperty(OutputKeys.ENCODING, "euc-kr");
        t.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source =new DOMSource(fileDoc);
        StreamResult result = new StreamResult(MyAddress.inputDirectory + "change.gexf");
        t.transform(source, result);
        

      System.out.println("count : " + count );
      
      return new File(MyAddress.inputDirectory + "change.gexf");
      
      
   }

}
