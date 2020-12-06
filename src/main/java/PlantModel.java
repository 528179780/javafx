import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;


public class PlantModel {
    /**
     * Save the initial homepage picture information
     **/
    public static HashMap<String, PlantInfo> nativeMap = new HashMap<>();
    public static HashMap<String, PlantInfo> nativeMap2 = new HashMap<>();
    /**
     * Save growth picture information
     **/
    public static HashMap<String, LinkedList<PlantInfo>> growthMap = new HashMap<>();
    /**
     * Save season picture information
     **/
    public static HashMap<String, LinkedList<PlantInfo>> seasonMap = new HashMap<>();

    /**
     * init plant and growth picture information
     * @author sufu
     * @date 2020/12/5 13:25
     **/
    public static void initData() {
    	try {
            FileInputStream fis = new FileInputStream("src/main/resources/plant/plantname");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            FileInputStream fis1 = new FileInputStream("src/main/resources/plant/plantinfo");
            BufferedReader br1 = new BufferedReader(new InputStreamReader(fis1));
            FileInputStream fis2 = new FileInputStream("src/main/resources/plant/plantaddress");
            BufferedReader br2 = new BufferedReader(new InputStreamReader(fis2));

            String line = null;
            String line1 = null;
            String line2 = null;
            while (((line = br.readLine()) != null)&&((line1 = br1.readLine()) != null)&&((line2 = br2.readLine()) != null)) {
                if (line.equals("")&&line1.equals("")&&line1.equals("")) {
                    continue;
                }
                String plants = line;
                String plants1 = line1;
                String plants2 = line2;
                String[] plantList = plants.split(",");
                String[] plantList1 = plants1.split("&");
                String[] plantList2 = plants2.split(",");
                for (String plantName : plantList) {
		            LinkedList<PlantInfo> list = new LinkedList<>();
		            list.add(new PlantInfo("Seed stage", "", "file:src/main/resources/growth/" + plantName + "1.png"));
		            list.add(new PlantInfo("Growth period", "", "file:src/main/resources/growth/" + plantName + "2.png"));
		            list.add(new PlantInfo("The mature stage", "", "file:src/main/resources/growth/" + plantName + "3.png"));
		            growthMap.put(plantName, list);
		        }
                for(int i =0;i<plantList.length;i++) {
                nativeMap.put(plantList[i], new PlantInfo(plantList[i], plantList1[i], plantList2[i]));
                }
            }

            br.close();
            br1.close();
            br2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    }
    
    public static void initData2() {
    	try {
            FileInputStream fis = new FileInputStream("src/main/resources/plant/plantname");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;
            while ((line = br.readLine()) != null) {
            	if (line.equals("")) {
            		continue;
            	}
            	String plants = line;
                String[] plantList = plants.split(",");
            for (String plantName : plantList) {
                LinkedList<PlantInfo> list = new LinkedList<>();
                list.add(new PlantInfo("Spring", "", "file:src/main/resources/season/" + plantName + "_spring.png"));
                list.add(new PlantInfo("Summer", "", "file:src/main/resources/season/" + plantName + "_summer.png"));
                list.add(new PlantInfo("Fall", "", "file:src/main/resources/season/" + plantName + "_fall.png"));
                list.add(new PlantInfo("Winter", "", "file:src/main/resources/season/" + plantName + "_winter.png"));
                seasonMap.put(plantName, list);
            }
        }
    	}catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void initData3() {
    	try {
            FileInputStream fis = new FileInputStream("src/main/resources/plant/plantname");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            FileInputStream fis1 = new FileInputStream("src/main/resources/plant/insectinfo");
            BufferedReader br1 = new BufferedReader(new InputStreamReader(fis1));
            FileInputStream fis2 = new FileInputStream("src/main/resources/plant/plantaddress");
            BufferedReader br2 = new BufferedReader(new InputStreamReader(fis2));

            String line = null;
            String line1 = null;
            String line2 = null;
            while (((line = br.readLine()) != null)&&((line1 = br1.readLine()) != null)&&((line2 = br2.readLine()) != null)) {
                if (line.equals("")&&line1.equals("")&&line1.equals("")) {
                    continue;
                }
                String plants = line;
                String plants1 = line1;
                String plants2 = line2;
                String[] plantList = plants.split(",");
                String[] plantList1 = plants1.split("&");
                String[] plantList2 = plants2.split(",");
        for (String plantName : plantList) {
            LinkedList<PlantInfo> list = new LinkedList<>();
            list.add(new PlantInfo("Spring", "", "file:src/main/resources/season/" + plantName + "_spring.png"));
            list.add(new PlantInfo("Summer", "", "file:src/main/resources/season/" + plantName + "_summer.png"));
            list.add(new PlantInfo("Fall", "", "file:src/main/resources/season/" + plantName + "_fall.png"));
            list.add(new PlantInfo("Winter", "", "file:src/main/resources/season/" + plantName + "_winter.png"));
            growthMap.put(plantName, list);
        }
        for(int i=0;i<plantList.length;i++) {
        	nativeMap2.put(plantList[i], new PlantInfo(plantList[i], plantList1[i], plantList2[i]));
        }
            }
            br.close();
            br1.close();
            br2.close();
    	}catch (Exception e) {
            e.printStackTrace();
        }
            }
	/**
     * plant picture information model
     * 
     */
    public static class PlantInfo {
        private String name;
        private String description;
        private String image;
        private int xloc, yloc;

        public PlantInfo(String name, String description, String image) {
            this.name = name;
            this.description = description;
            this.image = image;
        }
        
        public PlantInfo(int xloc, int yloc) {
        	this .xloc = xloc;
        	this.yloc = yloc;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getImage() {
            return image;
        }
        
        @Override 
        public int hashCode() {
         int hc = Objects.hash(name,xloc, yloc);
         return hc;
        }
        
        public void swapLoc(int x, int y) {
        	  xloc = x;
        	  yloc = y;
        	 }
    }
}

