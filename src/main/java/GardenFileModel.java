import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Version save file management class
 * @author sufu
 */
public class GardenFileModel {

    public int version_a = 0;
    public String version_b = "v1.0.";

    /**
     * the path where save the file
     **/
    private final String filePath = "src/main/resources/file/garden";
    public HashMap<String, List<ImageModel>> versionMap = new HashMap<>();

    public GardenFileModel() {
        loadVersionMapFromGardenFile();
    }

    /**
     * delete version
     */
    public void doDelete(String version) {
        if (version == null || version.length() ==0) {
            return;
        }
        // Remove version
        versionMap.remove(version);
        saveVersionMap(filePath);
    }

    /**
     * Empty file
     */
    private void clearInfoForFile() {
        File file = new File(filePath);
        try {
            if (file.exists()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save the pane to the version ,if nowVersion is null, create a new version, or save to nowVersion
     * @author sufu
     * @date 2020/12/5 16:03
     * @param list
     * @return
     **/
    public void doSave(List<ImageModel> list,String nowVersion) {
        if (list == null || list.size() <= 0) {
            return;
        }
        // get new version
        String version = getNewVersion();
        StringBuilder value = new StringBuilder();
        for (ImageModel model : list) {
            value.append("&").append(model.toString());
        }
        String result = version + value.toString() + "\n";
        writer(result);
        // writeImageModelToFIle(list);
        versionMap.put(version, list);
        version_a++;
    }

    /**
     *
     * @author sufu
     * @date 2020/12/6 9:00
     * @param nowVersion
     * @param list
     * @return
     **/
    public void doSave(String nowVersion,List<ImageModel> list){
        if(list == null|| list.size()==0){
            return;
        }
        versionMap.put(nowVersion, list);
        saveVersionMap("src/main/resources/file/garden");
    }
    /**
     * write the string to file where the path is filePath with add pattern
     * @author sufu
     * @date 2020/12/6 0:23
     * @param result the string to be added to file
     * @return
     **/
    private void writer(String result) {
        FileWriter fw = null;
        try {
            // If the file exists, append the content; if the file does not exist, create the file
            File f = new File(filePath);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(result);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * write the given ImageModelList to filepath
     * @author sufu
     * @date 2020/12/6 0:42
     * @param imageModelList the list to be saved
     * @return boolean true if write is success
     **/
    private boolean writeImageModelToFIle(List<ImageModel> imageModelList){
        // TODO 是否需要将每一个版本保存到不同的文件 处理版本信息和 ImageModel的 关系
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("src/main/resources/file/garden-test.txt"));
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(imageModelList);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * save versionMap to disk, auto close resources
     * @author sufu
     * @date 2020/12/6 8:39
     * @param filePath filePath that will be saved
     * @return boolean true if save is success
     **/
    private boolean saveVersionMap(String filePath){
        try(FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream os = new ObjectOutputStream(fos)) {
            os.writeObject(versionMap);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * Get all versions
     */
    public Set<String> getVersions() {
        return versionMap.keySet();
    }

    /**
     * Initialize file content to memory
     */
    private void initFileData() {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.equals("")) {
                    continue;
                }
                String[] list = line.split("&");
                List<ImageModel> models = new ArrayList<>();
                for (int i = 1; i < list.length; i++) {
                    String[] imageInfo = list[i].split(",");
                    ImageModel imageModel = new ImageModel();
                    imageModel.imageUrl = imageInfo[0];
                    imageModel.x = Double.parseDouble(imageInfo[1]);
                    imageModel.y = Double.parseDouble(imageInfo[2]);
                    imageModel.w = Double.parseDouble(imageInfo[3]);
                    imageModel.h = Double.parseDouble(imageInfo[4]);
                    models.add(imageModel);
                }
                String key = list[0];
                int index = Integer.parseInt(key.substring(key.lastIndexOf("") - 1));
                version_a = 1 + index;
                versionMap.put(key, models);
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * load versionMap to memory from gardenFile with the filePath
     * @author sufu
     * @date 2020/12/6 9:16
     **/
    private void loadVersionMapFromGardenFile(){
        File file = new File(filePath);
        if(!file.exists()){
            try {
                file.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(file.length() == 0){
            versionMap = new HashMap<>();
            return;
        }
        try(FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            versionMap = (HashMap<String, List<ImageModel>>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * get a new version, auto version change
     * @author sufu
     * @date 2020/12/6 9:26
     * @return java.lang.String new version
     **/
    public String getNewVersion(){
        String s = version_b + version_a;
        while (versionMap.containsKey(s)){
            version_a++;
            s = version_b + version_a;
        }
        return s;
    }
}
