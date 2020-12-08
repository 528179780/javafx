package resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * single resource defined in csv file
 * @author sufu
 * @date 2020/12/7
 */
public class Resource {
    private String plantName;
    private String plantInfo;
    private String imageAddress;
    private String kind;
    private String insectKind;
    /**
     * based on garden size
     */
    private int size;

    private Resource(List<String> strings) {
        this.plantName = strings.get(0);
        this.plantInfo = strings.get(1);
        this.imageAddress = strings.get(2);
        this.kind = strings.get(3);
        this.insectKind = strings.get(4);
        this.size = Integer.parseInt(strings.get(5));
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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getInsectKind() {
        return insectKind;
    }

    public void setInsectKind(String insectKind) {
        this.insectKind = insectKind;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static HashMap<String,List<Resource>> getResources(String filePath){
        HashMap<String,List<Resource>> resMap = new HashMap<>();
        List<List<String>> list = new ArrayList();
        try(FileReader fileReader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fileReader);) {
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
        resources.stream().collect(Collectors.groupingBy(Resource::getKind,Collectors.toList())).forEach(resMap::put);
        return resMap;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "plantName='" + plantName + '\'' +
                ", plantInfo='" + plantInfo + '\'' +
                ", imageAddress='" + imageAddress + '\'' +
                ", kind='" + kind + '\'' +
                ", insectKind='" + insectKind + '\'' +
                ", size=" + size +
                '}';
    }
}
