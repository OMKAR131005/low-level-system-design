import java.util.ArrayList;
import java.util.List;

interface FileSystemComponent{
    void openAll(int indent);
    void ls(int indent);
    FileSystemComponent cd(String name);
    int getSize();
    boolean isFolder();
    String getName();

}
class File implements FileSystemComponent{
    private String name;
    private int size;
    File(String name, int size){
        this.name = name;
        this.size = size;
    }
    @Override
    public void ls(int indent) {
        String indentSpaces=" ".repeat(indent);
        System.out.println(indentSpaces+name);
    }
    @Override
    public void openAll(int indent) {
        String indentSpaces=" ".repeat(indent);
        System.out.println(indentSpaces+name);
    }
    @Override
    public int getSize() {
        return size;
    }
    @Override
    public boolean isFolder() {
        return false;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public FileSystemComponent cd(String name) {
        return null;
    }
}
 class Folder implements FileSystemComponent{
    private String name;
    private List<FileSystemComponent> children;
    Folder(String name){
        this.name=name;
        children=new ArrayList<>();
    }
    @Override
     public void openAll(int indent) {
        String indentSpaces=" ".repeat(indent);
        System.out.println(indentSpaces+"+ "+name);
        for(FileSystemComponent child:children){
            child.openAll(indent+4);
        }
    }
    @Override
    public int getSize() {
        return children.size();
    }
    @Override
    public boolean isFolder() {
        return true;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public FileSystemComponent cd(String name) {
        for(FileSystemComponent child:children){
            if(child.getName().equals(name)){
               return child;
            }
        }
        return null;
    }
    @Override
     public void ls(int indent) {
        String indentSpaces = " ".repeat(indent);
        for(FileSystemComponent child:children){
            if (child.isFolder()) {
                System.out.println(indentSpaces + "+ " + child.getName());
            } else {
                System.out.println(indentSpaces + child.getName());
            }
        }

    }
     public void add(FileSystemComponent item) {
         children.add(item);
     }
 }
public class CompositeDesignPattern {

    public static void main(String[] args) {
        Folder root = new Folder("root");
        root.add(new File("file1.txt", 1));
        root.add(new File("file2.txt", 1));

        Folder docs = new Folder("docs");
        docs.add(new File("resume.pdf", 1));
        docs.add(new File("notes.txt", 1));
        root.add(docs);

        Folder images = new Folder("images");
        images.add(new File("photo.jpg", 1));
        root.add(images);

        root.ls(0);

        docs.ls(0);

        root.openAll(0);

        FileSystemComponent cwd = root.cd("docs");
        if (cwd != null) {
            cwd.ls(0);
        } else {
            System.out.println("\nCould not cd into docs\n");
        }

        System.out.println(root.getSize());
    }
}
