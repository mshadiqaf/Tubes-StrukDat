package project.kamussearchengine.Models;

public class RawNode {

    public String word;
    public String wordTranslated;
    public String desc;
    public String descriptionTranslated;
    public Runnable gimmick;

    public RawNode(String word, String wordTranslated, String desc, String descriptionTranslated, Runnable gimmick) {
        this.word = word;
        this.wordTranslated = wordTranslated;
        this.desc = desc;
        this.descriptionTranslated = descriptionTranslated;
        this.gimmick = gimmick;
    }

    public RawNode(String word, String wordTranslated, String desc, String descriptionTranslated) {
        this.word = word;
        this.wordTranslated = wordTranslated;
        this.desc = desc;
        this.descriptionTranslated = descriptionTranslated;
        this.gimmick = null;
    }
}
