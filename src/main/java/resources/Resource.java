package resources;

import utils.CSVUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * single resource defined in csv file
 * @author sufu
 * @date 2020/12/7
 */
public class Resource {
    private String plantName;
    private String plantInfo;
    private String imageAddress;
    private String plantKind;
    private String insectKind;

    private Resource(List<String> strings) {
        this.plantName = strings.get(0);
        this.plantInfo = strings.get(1);
        this.imageAddress = strings.get(2);
        this.plantKind = strings.get(3);
        this.insectKind = strings.get(4);
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantInfo() {
        return plantInfo;
    }

    public void setPlantInfo(String plantInfo) {
        this.plantInfo = plantInfo;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public String getPlantKind() {
        return plantKind;
    }

    public void setPlantKind(String plantKind) {
        this.plantKind = plantKind;
    }

    public String getInsectKind() {
        return insectKind;
    }

    public void setInsectKind(String insectKind) {
        this.insectKind = insectKind;
    }

    public static ArrayList<Resource> getResources(String filePath){
        List<List<String>> list = new ArrayList();
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fileReader);
            String[] header = br.readLine().split(",");
            String ss;
            while ((ss = br.readLine())!=null){
                String[] strArr = ss.trim().split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)",-1);
                strArr[1] = strArr[1].replace("\"","");
                strArr[4] = strArr[4].replace("\"","");
                list.add(Arrays.asList(strArr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Resource> resources = new ArrayList<>();
        if(list.size() > 0){
            for (List<String> strings : list) {
                resources.add(new Resource(strings));
            }
        }
        return resources;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "plantName='" + plantName + '\'' +
                ", plantInfo='" + plantInfo + '\'' +
                ", imageAddress='" + imageAddress + '\'' +
                ", plantKind='" + plantKind + '\'' +
                ", insectKind='" + insectKind + '\'' +
                '}';
    }
}
