/*
Copyright 2008-2010 Gephi
Authors : Mathieu Bastian <mathieu.bastian@gephi.org>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
*/
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFImageWriter;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.filters.api.FilterController;
import org.gephi.filters.api.Query;
import org.gephi.filters.api.Range;
import org.gephi.filters.plugin.graph.DegreeRangeBuilder.DegreeRangeFilter;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.GraphView;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.NodeIterable;
import org.gephi.graph.api.NodeIterator;
import org.gephi.graph.api.Renderable;
import org.gephi.graph.api.UndirectedGraph;
import org.gephi.graph.dhns.graph.iterators.NodeIterableImpl;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.importer.api.NodeDraft;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.layout.plugin.forceAtlas.ForceAtlasLayout;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2;
import org.gephi.layout.plugin.multilevel.MultiLevelLayout;
import org.gephi.layout.plugin.multilevel.MultiLevelLayout.CoarseningStrategy;
import org.gephi.layout.plugin.rotate.RotateLayout;
import org.gephi.layout.plugin.scale.ScaleLayout;
import org.gephi.layout.spi.Layout;
import org.gephi.partition.api.Partition;
import org.gephi.partition.api.PartitionController;
import org.gephi.partition.plugin.NodeColorTransformer;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.types.EdgeColor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.ranking.api.Ranking;
import org.gephi.ranking.api.RankingController;
import org.gephi.ranking.api.Transformer;
import org.gephi.ranking.plugin.transformer.AbstractColorTransformer;
import org.gephi.ranking.plugin.transformer.AbstractSizeTransformer;
import org.gephi.statistics.plugin.GraphDistance;
import org.gephi.statistics.plugin.Modularity;
import org.gephi.visualization.impl.TextDataImpl;
import org.jfree.layout.RadialLayout;
import org.openide.util.Lookup;


public class GexfToPDF {
   
     ProjectController pc;
     Workspace workspace;

       //Get models and controllers for this new workspace - will be useful later
     AttributeModel attributeModel;
     GraphModel graphModel;
     PreviewModel model;
     ImportController importController;
     FilterController filterController;
     RankingController rankingController;
     
     int count = 0;
     

   
   public void mkLayout(Layout layout, File file, String layoutN , boolean labelexists, boolean frame){
      
      System.out.println("call");
      
       //Init a project - and therefore a workspace
        pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();
        workspace = pc.getCurrentWorkspace();

        //Get models and controllers for this new workspace - will be useful later
        attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
        graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
        model = Lookup.getDefault().lookup(PreviewController.class).getModel();
        importController = Lookup.getDefault().lookup(ImportController.class);
        filterController = Lookup.getDefault().lookup(FilterController.class);
        rankingController = Lookup.getDefault().lookup(RankingController.class);
        
        

        //Import file       
        Container container;
        try {
            container = importController.importFile(file);
            container.getLoader().setEdgeDefault(EdgeDefault.DIRECTED);   //Force DIRECTED
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        //Append imported data to GraphAPIs
        importController.process(container, new DefaultProcessor(), workspace);

        //See if graph is well imported
        DirectedGraph graph = graphModel.getDirectedGraph();
        System.out.println("Nodes: " + graph.getNodeCount());
        System.out.println("Edges: " + graph.getEdgeCount());
        
 
        NodeIterable it = graph.getNodes();
        NodeIterator iterator = it.iterator();
        while(iterator.hasNext()){
           
           Node node = iterator.next();
           
        //   node.getNodeData().getLabel().contains("RED")
           
           
           if(count  > 100 ){
              node.getNodeData().setColor(1f, 0, 0);
              count++;
              
           }else if( count > 50 ){
              
              node.getNodeData().setColor(0, 0, 1f);
              count++;
              
           }else {
              node.getNodeData().setColor(0, 1f, 0);
              count++;
           }
           
           
           
          // if(node.getNodeData().getLabel().contains("zARED")){
              
              //System.out.println("RED "   + "\n");
           
          // node.getNodeData().setColor(1f, 0, 0);
           
           //else if(node.getNodeData().getLabel().contains("zABLUE")){
              

             // System.out.println("BLUE "   + "\n");
           
              //node.getNodeData().setColor(0, 0, 1f);
              
           //}else {node.getNodeData().setColor(0.1F, 0.1F, 0.1F);
           
          // }
           
        node.getAttributes().getValue(1);
           
//           System.out.println("node attribute 1 : "  + node + "\n");
//           System.out.println("node attribute 2 : "  + node.getNodeData().getTextData() + "\n");
//           
//         // Renderable ende =  node.getNodeData().getModel().getObj();
//         // TextDataImpl textData=(TextDataImpl)ende.getTextData();
//
//
//     // if(frame == true){
//              
//        //      //mainframe
//              
//      //    }else {System.out.println("node attribute 4 : "  + textData.getLine().getText() + "\n");}
//           
//     
//     
//           System.out.println("node attribute 5 : "  + node.getAttributes().getValue(1) + "\n");
//           System.out.println("node attribute 6 : "  + node.getAttributes().getValue(2) + "\n");
//           
           
           System.out.println("node attribute label : "  + node.getNodeData().getLabel() + "\n");

           
           
        }
        
        
        
        
//      NodeIterator iterator = graph.getNodes().iterator();
//      Map<Node, Color> nodeClusterMappings = new HashMap<Node, Color>();
//    
//              
//         List<Node> level1 = new ArrayList<Node>();
//         List<Node> level2 = new ArrayList<Node>(); 
//
//         while (iterator.hasNext()){ 
//            Node currentNode = iterator.next();
//            //여기서 검사하는 소스 넣고
//            System.out.print("Labeltest" + currentNode.getNodeData().getLabel());
//            level1.add(currentNode); 
//         } 
//
//       
//          // assigning each node to its own cluster 
//         for(Node node: level1){ 
//            Color cluster = new Color(0xF361DC); 
//               nodeClusterMappings.put(node, cluster); 
//               
//         } 
         
      
//      //Partition with 'source' column, which is in the data
  //      PartitionController partitionController = Lookup.getDefault().lookup(PartitionController.class);
//      Partition p = partitionController.buildPartition(attributeModel.getNodeTable().getColumn("source"), graph);
//      NodeColorTransformer nodeColorTransformer = new NodeColorTransformer();
//      System.out.println(p.getPartsCount() + " partitions found");
//      nodeColorTransformer.randomizeColors(p);
//      partitionController.transform(p, nodeColorTransformer);
      
      //Run modularity algorithm - community detection
   //   Modularity modularity = new Modularity();
     // modularity.execute(graphModel, attributeModel);

      //Partition with 'modularity_class', just created by Modularity algorithm
      //AttributeColumn modColumn = attributeModel.getNodeTable().getColumn(Modularity.MODULARITY_CLASS);
      
      
      //Partition p2 = partitionController.buildPartition(modColumn, graph);
     // System.out.println(p2.getPartsCount() + " partitions found");
      
     // NodeColorTransformer nodeColorTransformer2 = new NodeColorTransformer();
     // nodeColorTransformer2.randomizeColors(p2);
    // partitionController.transform(p2, nodeColorTransformer2);


        //Filter      
        DegreeRangeFilter degreeFilter = new DegreeRangeFilter();
        degreeFilter.init(graph);
        degreeFilter.setRange(new Range(0, Integer.MAX_VALUE));     //Remove nodes with degree < 0
        Query query = filterController.createQuery(degreeFilter);
        GraphView view = filterController.filter(query);
        graphModel.setVisibleView(view);    //Set the filter result as the visible view

        //See visible graph stats
        UndirectedGraph graphVisible = graphModel.getUndirectedGraphVisible();
        System.out.println("Nodes: " + graphVisible.getNodeCount());
        System.out.println("Edges: " + graphVisible.getEdgeCount());
        

       
//        }
     
           
        //   Node node = it.next();
           //System.out.println(node.getAttributes().getValue("color"));
     //      System.out.println(graphVisible.getNode(i).getNodeData().getAttributes());
           // NodeDraft nodeDraft=Lookup.getDefault().lookup(NodeDraft.class);
           // container.addNode(nodeDraft);
           // nodeArray[i]=nodeDraft;
           // Progress.progress(progress,++progressUnit);
          

        //for (graphVisible.getNode(0)

        //Run YifanHuLayout for 100 passes - The layout always takes the current visible view
      
      layout.setGraphModel(graphModel);
        layout.resetPropertiesValues();
//        layout.setOptimalDistance(200f);

        layout.initAlgo();
        for (int i = 0; i < 100 && layout.canAlgo(); i++) {
            layout.goAlgo();
        }
        layout.endAlgo();

        //Get Centrality
        GraphDistance distance = new GraphDistance();
        distance.setDirected(true);
        distance.execute(graphModel, attributeModel);

        //Rank color by Degree
     //   Ranking degreeRanking = rankingController.getModel().getRanking(Ranking.NODE_ELEMENT, Ranking.DEGREE_RANKING);
     //   AbstractColorTransformer colorTransformer = (AbstractColorTransformer) rankingController.getModel().getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_COLOR);
     //   colorTransformer.setColors(new Color[]{new Color(0xF361DC), new Color(0x00FF00)});
        
     //   rankingController.transform(degreeRanking,colorTransformer);

        //Rank size by centrality
//        AttributeColumn centralityColumn = attributeModel.getNodeTable().getColumn(GraphDistance.BETWEENNESS);
 //       Ranking centralityRanking = rankingController.getModel().getRanking(Ranking.NODE_ELEMENT, centralityColumn.getId());
  //      AbstractSizeTransformer sizeTransformer = (AbstractSizeTransformer) rankingController.getModel().getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_SIZE);
   //     sizeTransformer.setMinSize(3);
    //    sizeTransformer.setMaxSize(10);
      //  rankingController.transform(centralityRanking,sizeTransformer);

        //Preview
        model.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, labelexists);
        model.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(Color.GRAY));
        model.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, new Float(0.1f));
        model.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT, model.getProperties().getFontValue(PreviewProperty.NODE_LABEL_FONT).deriveFont(8));

        //Export
        ExportController ec = Lookup.getDefault().lookup(ExportController.class);
        try {
           
           int pos = file.getName().lastIndexOf(".");
              String fileName = file.getName().substring(0,pos) + layoutN;
           
     
           String path = MyAddress.inputDirectory+fileName;
           
            ec.exportFile(new File(path + ".pdf"));
            
            String pdfFilePath = path + ".pdf";
            
            PDDocument pdfDocument = new PDDocument();
            pdfDocument = PDDocument.load(pdfFilePath); //1. pdf파일 로드
            
            String imageFormat = "jpg"; //jpg와 png를 지원한다.
            //String outputPrefix = "test_"; // prefix뒤에 1,2,3....10 이렇게 페이지 번호가 붙는다.( 파일이름이라고 보면된다.)
            String passWord = ""; //암호화된 pdf파일을 열때 쓰는듯 하다. 없으면 공백이나 null
            int resolution = 1500; //이미지파일의 해상도 단위는 dpi
            int startPageNum = 1;
            int endPageNum = pdfDocument.getNumberOfPages();
            
            PDFImageWriter piw = new PDFImageWriter(); //2. 이미지 변환기 생성
            //piw.writeImage(pd, "jpg", "", 1, 2, "d:/test/img/test2_none");
     

            piw.writeImage(pdfDocument, imageFormat, passWord, startPageNum, endPageNum, path); //3. 각종설정후에 파일쓰기
            
            
            
      
        } catch (IOException ex) {
            ex.printStackTrace();
            
        }
      
      
      
   }

    public boolean script(File file, boolean labelexists) {
       
          int pos = file.getName().lastIndexOf(".");
          String fileName = file.getName().substring(0,pos);
       
     
          YifanHuLayout yifanlayout = new YifanHuLayout(null, new StepDisplacement(1f));
          mkLayout(yifanlayout,file, "Yifan", labelexists,true );
  
          
//          ScaleLayout scalelayout = new ScaleLayout(null, 10);
//          mkLayout(scalelayout,file, "scale", labelexists,true );
//          System.out.println("call2");
//   
//          ForceAtlas2 force2layout = new ForceAtlas2(null);
//          mkLayout(force2layout,file,"force2", labelexists,true );
    
          //RotateLayout rotalayout = new RotateLayout(null, 0);
          //mkLayout(rotalayout,file, "Rotate",labelexists);
            //  ScaleLayout layout = new ScaleLayout(null, 10);
          
       //   Thumbnail tb = new Thumbnail();
          
          return true;
          
          
          
  
    }
    
    public boolean script2(File file,String type, boolean labelexists) {
       
   //   int pos = file.getName().lastIndexOf(".");
    //  String fileName = file.getName().substring(0,pos);

   switch(type){
   
   case "yifan" : 
      System.out.println("ssss");
      YifanHuLayout yifanlayout = new YifanHuLayout(null, new StepDisplacement(1f));
         System.out.println("test");
         mkLayout(yifanlayout,file, "Yifan", labelexists, false );
         break;
         
         
   case "scale" : 
      ScaleLayout scalelayout = new ScaleLayout(null, 10);
         mkLayout(scalelayout,file, "scale", labelexists,false);
         break;
         
         
   case "force2" : 
       ForceAtlas2 force2layout = new ForceAtlas2(null);
         mkLayout(force2layout,file,"force2", labelexists,false);

         break;
      
   
      
   }
     
      return true;

}
    
}
