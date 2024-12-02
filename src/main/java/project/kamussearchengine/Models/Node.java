package project.kamussearchengine.Models;

public class Node {

    String word;
    String wordTranslated;
    String desc;
    String descriptionTranslated;
    Node right;
    Node left;
    Node parent;
    boolean red;
    Runnable gimmick;

    public Node() {
    }

    public Node(String word, String wordTranslated, String desc, String descriptionTranslated) {
        this.word = word;
        this.wordTranslated = wordTranslated;
        this.desc = desc;
        this.descriptionTranslated = descriptionTranslated;
        this.red = true;
        this.gimmick = null;
    }

    // Getter dan Setter untuk properti baru
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordTranslated() {
        return wordTranslated;
    }

    public void setWordTranslated(String wordTranslated) {
        this.wordTranslated = wordTranslated;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDescriptionTranslated() {
        return descriptionTranslated;
    }

    public void setDescriptionTranslated(String descriptionTranslated) {
        this.descriptionTranslated = descriptionTranslated;
    }

    // Getter dan Setter lainnya tetap ada
    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public void setGimmick(Runnable gimmick) {
        this.gimmick = gimmick;
    }

    public Runnable getGimmick() {
        return gimmick;
    }
}
