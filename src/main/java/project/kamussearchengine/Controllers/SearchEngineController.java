package project.kamussearchengine.Controllers;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import project.kamussearchengine.Models.Node;
import project.kamussearchengine.Models.RawNode;
import project.kamussearchengine.Models.RedBlackTree;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchEngineController implements Initializable {

    private static ArrayList<RawNode> data = new ArrayList<>();
    private static RedBlackTree dataINtoEN = new RedBlackTree();
    private static RedBlackTree dataENtoIN = new RedBlackTree();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private VBox vBox;

    @FXML
    private VBox vBoxResult;

    @FXML
    private TextField searchBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inputData();

    }

    @FXML
    private void search() {
        String inputText = searchBar.getText().trim();

        vBoxResult.getChildren().clear();

        System.out.println("Input: " + inputText);

        if (inputText.isEmpty()) {
            Label noInput = new Label("Masukkan teks untuk pencarian.");
            noInput.setStyle("-fx-font-size: 14; -fx-font-style: italic");
            VBox.setMargin(noInput, new Insets(10, 0, 10, 0)); // Menambahkan margin
            vBoxResult.getChildren().add(noInput);
            return;
        }

        List<Node> allNodes = dataINtoEN.getData();
        boolean foundContains = false;

        for (int i = 0; i < allNodes.size(); i++) {
            Node node = allNodes.get(i);
            VBox dataContainer = new VBox();

            if (node.getWord().toLowerCase().startsWith(inputText.toLowerCase())) {
                System.out.println("Word: " + node.getWord());


                Label resultLabel = new Label("Kata ditemukan: " + node.getWord());
                resultLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
                VBox.setMargin(resultLabel, new Insets(12, 12, 5, 12)); // Menambahkan margin
                dataContainer.getChildren().add(resultLabel);

                // Menambahkan Label untuk Bahasa Inggris
                Label wordTranslatedLabel = new Label("Bahasa Inggris: " + node.getWordTranslated());
                wordTranslatedLabel.setStyle("-fx-font-size: 14;");
                VBox.setMargin(wordTranslatedLabel, new Insets(0, 12, 5, 12));
                dataContainer.getChildren().add(wordTranslatedLabel);

                // Menambahkan Label untuk Deskripsi (ID)
                Label descIDLabel = new Label("Deskripsi (ID): " + node.getDesc());
                descIDLabel.setStyle("-fx-font-size: 14;");
                VBox.setMargin(descIDLabel, new Insets(0, 12, 5, 12));
                dataContainer.getChildren().add(descIDLabel);

                // Menambahkan Label untuk Deskripsi (EN)
                Label descENLabel = new Label("Deskripsi (EN): " + node.getDescriptionTranslated());
                descENLabel.setStyle("-fx-font-size: 14;");
                VBox.setMargin(descENLabel, new Insets(0, 12, 12, 12));
                dataContainer.getChildren().add(descENLabel);

                if (node.getWord().equalsIgnoreCase(inputText)) {
                    Runnable gimmick = node.getGimmick();
                    if (gimmick != null) {
                        gimmick.run();
                    }
                    resultLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: red;");
                    wordTranslatedLabel.setStyle("-fx-font-size: 14; -fx-text-fill: red;");
                    descIDLabel.setStyle("-fx-font-size: 14; -fx-text-fill: red;");
                    descENLabel.setStyle("-fx-font-size: 14; -fx-text-fill: red;");
                }

                dataContainer.setStyle("-fx-border-color: red;");

                vBoxResult.getChildren().add(dataContainer);

                foundContains = true;
            }
        }

        // Jika tidak ada hasil sama sekali
        if (!foundContains) {
            Label noResults = new Label("Hasil tidak ditemukan.");
            noResults.setStyle("-fx-font-size: 14; -fx-font-style: italic");
            VBox.setMargin(noResults, new Insets(10, 0, 10, 0)); // Menambahkan margin
            vBoxResult.getChildren().add(noResults);
        }
    }

    private Runnable callGimmick() {
        return () -> {

            System.out.println("Gimmick berhasil dijalankan!");

        };
    }

    private void inputData() {

        data.add(new RawNode("Abadi", "Eternal", "Tidak pernah berakhir, berlangsung selamanya.", "Never-ending, lasts forever.", callGimmick()));
        data.add(new RawNode("Adil", "Fair", "Tidak memihak, sesuai aturan atau keadilan.", "Impartial, in accordance with justice."));
        data.add(new RawNode("Aktif", "Active", "Selalu bergerak atau berpartisipasi.", "Always moving or participating."));
        data.add(new RawNode("Alam", "Nature", "Lingkungan fisik yang ada di sekitar kita.", "The physical environment around us."));
        data.add(new RawNode("Amal", "Charity", "Perbuatan baik untuk membantu orang lain.", "Acts of kindness to help others."));
        data.add(new RawNode("Angin", "Wind", "Udara yang bergerak karena perbedaan tekanan.", "Air that moves due to pressure differences."));
        data.add(new RawNode("Anjing", "Dog", "Hewan peliharaan yang setia.", "A loyal domestic animal."));
        data.add(new RawNode("Api", "Fire", "Panas dan cahaya yang dihasilkan oleh pembakaran.", "Heat and light produced by combustion."));
        data.add(new RawNode("Arti", "Meaning", "Makna sesuatu, baik kata maupun simbol.", "The definition of something, whether a word or symbol."));
        data.add(new RawNode("Asli", "Original", "Bukan tiruan, berasal dari sumbernya.", "Not a copy, from the source."));
        data.add(new RawNode("Badai", "Storm", "Cuaca ekstrem dengan angin kencang dan hujan deras.", "Extreme weather with strong winds and heavy rain."));
        data.add(new RawNode("Bahasa", "Language", "Sistem komunikasi manusia.", "A system of human communication."));
        data.add(new RawNode("Bahu", "Shoulder", "Bagian tubuh di antara leher dan lengan.", "The body part between the neck and arms."));
        data.add(new RawNode("Baja", "Steel", "Logam yang kuat dan tahan lama.", "A strong and durable metal."));
        data.add(new RawNode("Banyak", "Many", "Jumlah yang besar.", "A large number."));
        data.add(new RawNode("Baru", "New", "Belum lama ada atau digunakan.", "Recently created or used."));
        data.add(new RawNode("Bebas", "Free", "Tidak terikat, memiliki kebebasan.", "Not bound, having freedom."));
        data.add(new RawNode("Belajar", "Learn", "Proses mendapatkan pengetahuan.", "The process of gaining knowledge."));
        data.add(new RawNode("Besar", "Big", "Ukuran yang lebih dari rata-rata.", "Larger in size than average."));
        data.add(new RawNode("Bicara", "Talk", "Berkomunikasi dengan kata-kata.", "To communicate using words."));
        data.add(new RawNode("Cahaya", "Light", "Energi yang terlihat dan membuat kita bisa melihat.", "Visible energy that allows us to see."));
        data.add(new RawNode("Cinta", "Love", "Perasaan kasih sayang yang mendalam.", "A deep feeling of affection."));
        data.add(new RawNode("Cepat", "Fast", "Bergerak dengan kecepatan tinggi.", "Moving with high speed."));
        data.add(new RawNode("Cermin", "Mirror", "Permukaan yang memantulkan gambar.", "A surface that reflects images."));
        data.add(new RawNode("Cerah", "Bright", "Terang, tidak gelap.", "Having a lot of light, not dark."));
        data.add(new RawNode("Cerita", "Story", "Kisah atau narasi suatu kejadian.", "A narrative about an event."));
        data.add(new RawNode("Cipta", "Create", "Membuat sesuatu yang baru.", "To make something new."));
        data.add(new RawNode("Cuaca", "Weather", "Keadaan atmosfer pada waktu tertentu.", "The atmospheric conditions at a given time."));
        data.add(new RawNode("Curah", "Rainfall", "Jumlah hujan yang turun dalam periode tertentu.", "The amount of rain that falls over a period of time."));
        data.add(new RawNode("Cukai", "Tax", "Pajak yang dikenakan pada barang tertentu.", "A fee levied on goods or services."));
        data.add(new RawNode("Damai", "Peace", "Keadaan tanpa konflik atau kekerasan.", "A state of harmony without conflict."));
        data.add(new RawNode("Daya", "Power", "Kemampuan untuk melakukan sesuatu.", "The ability to do something or act in a particular way."));
        data.add(new RawNode("Dingin", "Cold", "Suhu yang rendah.", "Having a low temperature."));
        data.add(new RawNode("Dosa", "Sin", "Perbuatan yang dianggap salah secara moral atau agama.", "An immoral act considered wrong by religious standards."));
        data.add(new RawNode("Dunia", "World", "Planet tempat manusia hidup atau alam semesta.", "The earth or the universe in which humans live."));
        data.add(new RawNode("Duduk", "Sit", "Posisi tubuh dengan pantat berada di permukaan.", "A position where the body is resting on the lower part, usually on a chair."));
        data.add(new RawNode("Dukun", "Shaman", "Orang yang dianggap memiliki kemampuan supranatural.", "A person believed to have supernatural powers."));
        data.add(new RawNode("Dalam", "Deep", "Jarak ke bagian bawah yang jauh dari permukaan.", "Far down from the surface."));
        data.add(new RawNode("Dewasa", "Adult", "Telah mencapai tahap kematangan fisik dan mental.", "Reached a mature state physically and mentally."));
        data.add(new RawNode("Dokter", "Doctor", "Profesional medis yang merawat pasien.", "A professional who treats patients."));
        data.add(new RawNode("Ekonomi", "Economy", "Sistem pengelolaan sumber daya dan keuangan.", "The system of managing resources and finances."));
        data.add(new RawNode("Emas", "Gold", "Logam mulia berwarna kuning.", "A valuable yellow metal."));
        data.add(new RawNode("Energi", "Energy", "Kekuatan untuk melakukan kerja.", "The capacity to perform work or cause change."));
        data.add(new RawNode("Ekor", "Tail", "Bagian tubuh belakang pada hewan.", "The rear part of an animal's body."));
        data.add(new RawNode("Elang", "Eagle", "Burung pemangsa yang besar dan kuat.", "A large, strong bird of prey."));
        data.add(new RawNode("Es", "Ice", "Air dalam keadaan beku.", "Frozen water."));
        data.add(new RawNode("Etika", "Ethics", "Prinsip moral yang mengatur perilaku.", "Moral principles that govern behavior."));
        data.add(new RawNode("Eksperimen", "Experiment", "Proses uji coba untuk menemukan fakta.", "A scientific procedure to test a hypothesis."));
        data.add(new RawNode("Efek", "Effect", "Hasil atau dampak dari suatu tindakan.", "The result or outcome of an action."));
        data.add(new RawNode("Enak", "Delicious", "Rasa yang menyenangkan di lidah.", "Pleasing to the taste."));
        data.add(new RawNode("Fakta", "Fact", "Sesuatu yang nyata dan dapat dibuktikan.", "Something that is true and can be verified."));
        data.add(new RawNode("Fasilitas", "Facility", "Sarana untuk membantu kegiatan tertentu.", "Resources or equipment that aid an activity."));
        data.add(new RawNode("Film", "Movie", "Cerita yang direkam untuk ditonton.", "A recorded story for viewing."));
        data.add(new RawNode("Foto", "Photo", "Gambar yang dihasilkan oleh kamera.", "An image created with a camera."));
        data.add(new RawNode("Fungsi", "Function", "Peran atau kegunaan sesuatu.", "The purpose or role of something."));
        data.add(new RawNode("Gagah", "Brave", "Memiliki keberanian.", "Showing courage and determination."));
        data.add(new RawNode("Galak", "Fierce", "Bersikap keras atau menakutkan.", "Showing aggression or intensity."));
        data.add(new RawNode("Garis", "Line", "Tanda memanjang yang dibuat oleh pena atau benda lainnya.", "A long, narrow mark."));
        data.add(new RawNode("Gaya", "Style", "Cara atau bentuk ekspresi.", "A particular way of doing something."));
        data.add(new RawNode("Gembira", "Happy", "Merasa senang dan puas.", "Feeling or showing joy."));
        data.add(new RawNode("Gempa", "Earthquake", "Getaran yang terjadi di permukaan bumi akibat pergerakan lempeng.", "Vibrations that occur on the surface of the earth due to tectonic movements."));
        data.add(new RawNode("Gelap", "Dark", "Tidak ada cahaya, sulit untuk dilihat.", "Without light, hard to see."));
        data.add(new RawNode("Gelombang", "Wave", "Pergerakan naik-turun air atau getaran.", "A rising and falling motion of water or vibration."));
        data.add(new RawNode("Gemuk", "Fat", "Memiliki berat badan yang lebih besar dari rata-rata.", "Having a larger body size than average."));
        data.add(new RawNode("Gigi", "Teeth", "Bagian tubuh di dalam mulut untuk mengunyah makanan.", "Hard structures in the mouth used for chewing."));
        data.add(new RawNode("Hadir", "Present", "Berada di suatu tempat.", "Being in a specific location."));
        data.add(new RawNode("Halus", "Smooth", "Tidak kasar, memiliki permukaan yang rata.", "Having a flat and even surface, not rough."));
        data.add(new RawNode("Harapan", "Hope", "Keinginan atau cita-cita untuk masa depan.", "A desire for something to happen in the future."));
        data.add(new RawNode("Hati", "Heart", "Organ tubuh yang memompa darah, juga sering diartikan sebagai pusat perasaan.", "An organ that pumps blood, also symbolizes emotions."));
        data.add(new RawNode("Hewan", "Animal", "Makhluk hidup yang bukan tumbuhan atau manusia.", "A living organism that is not a plant or human."));
        data.add(new RawNode("Hijau", "Green", "Warna yang sering diasosiasikan dengan dedaunan.", "A color often associated with plants or nature."));
        data.add(new RawNode("Hujan", "Rain", "Air yang jatuh dari awan ke bumi.", "Water that falls from clouds."));
        data.add(new RawNode("Hutan", "Forest", "Kawasan luas yang dipenuhi pohon.", "A large area covered chiefly with trees."));
        data.add(new RawNode("Harga", "Price", "Nilai yang ditentukan untuk suatu barang atau jasa.", "The amount of money required for something."));
        data.add(new RawNode("Hebat", "Great", "Luar biasa, sangat bagus.", "Remarkable or impressive."));
        data.add(new RawNode("Ilmu", "Science", "Pengetahuan yang didapatkan melalui penelitian.", "Knowledge gained through study or experimentation."));
        data.add(new RawNode("Indah", "Beautiful", "Sangat menarik atau menyenangkan untuk dilihat.", "Attractive and pleasant to look at."));
        data.add(new RawNode("Impian", "Dream", "Harapan atau cita-cita.", "A hope or desire for the future."));
        data.add(new RawNode("Istirahat", "Rest", "Menghentikan aktivitas untuk memulihkan tenaga.", "Taking a break to recover energy."));
        data.add(new RawNode("Ikan", "Fish", "Hewan air yang bernapas dengan insang.", "A water-dwelling animal that breathes through gills."));
        data.add(new RawNode("Ikhlas", "Sincere", "Melakukan sesuatu dengan hati yang tulus.", "Doing something with genuine intentions."));
        data.add(new RawNode("Isi", "Content", "Apa yang ada di dalam sesuatu.", "What is contained within something."));
        data.add(new RawNode("Ingat", "Remember", "Tidak lupa, menyimpan dalam memori.", "To retain information in memory."));
        data.add(new RawNode("Industri", "Industry", "Aktivitas ekonomi untuk menghasilkan barang atau jasa.", "The production of goods or services."));
        data.add(new RawNode("Identitas", "Identity", "Karakteristik unik yang membedakan seseorang atau sesuatu.", "The characteristics that distinguish someone or something."));
        data.add(new RawNode("Jalan", "Road", "Lintasan untuk kendaraan atau pejalan kaki.", "A path or route for vehicles or pedestrians."));
        data.add(new RawNode("Jantung", "Heart", "Organ vital yang memompa darah.", "The organ that pumps blood throughout the body."));
        data.add(new RawNode("Jauh", "Far", "Tidak dekat, memiliki jarak yang panjang.", "A great distance from something."));
        data.add(new RawNode("Jendela", "Window", "Bukaan di dinding untuk ventilasi atau cahaya.", "An opening in a wall or structure for light or air."));
        data.add(new RawNode("Jenis", "Type", "Kategori atau golongan tertentu.", "A category or kind of something."));
        data.add(new RawNode("Jejak", "Footprint", "Tanda yang ditinggalkan di permukaan setelah dilalui.", "A mark left behind after something passes."));
        data.add(new RawNode("Jernih", "Clear", "Tidak keruh, transparan.", "Transparent or easy to see through."));
        data.add(new RawNode("Jujur", "Honest", "Tidak berbohong, berkata sesuai kenyataan.", "Truthful, not lying or deceiving."));
        data.add(new RawNode("Jurang", "Cliff", "Lembah yang dalam dan curam.", "A steep face of rock or earth."));
        data.add(new RawNode("Jutaan", "Millions", "Dalam jumlah sangat banyak, setara dengan satu juta.", "A very large number, equivalent to one million."));
        data.add(new RawNode("Kaca", "Glass", "Bahan transparan yang digunakan untuk jendela atau cermin.", "A transparent material used for windows or mirrors."));
        data.add(new RawNode("Kadar", "Level", "Ukuran atau jumlah tertentu.", "A specific amount or degree of something."));
        data.add(new RawNode("Kado", "Gift", "Hadiah yang diberikan kepada seseorang.", "A present given to someone."));
        data.add(new RawNode("Kamar", "Room", "Ruangan dalam bangunan untuk aktivitas tertentu.", "A space within a building for a particular purpose."));
        data.add(new RawNode("Kapal", "Ship", "Kendaraan besar untuk perjalanan di air.", "A large vessel for traveling on water."));
        data.add(new RawNode("Kartu", "Card", "Lembar kecil dari kertas atau plastik untuk tujuan tertentu.", "A small piece of paper or plastic used for various purposes."));
        data.add(new RawNode("Kasih", "Affection", "Perasaan cinta dan perhatian.", "A feeling of love or care."));
        data.add(new RawNode("Kaya", "Rich", "Memiliki banyak harta.", "Having a large amount of wealth."));
        data.add(new RawNode("Kecil", "Small", "Ukuran yang lebih kecil dari rata-rata.", "A size smaller than average."));
        data.add(new RawNode("Keindahan", "Beauty", "Keadaan yang menyenangkan untuk dilihat.", "The quality of being aesthetically pleasing."));
        data.add(new RawNode("Lagu", "Song", "Karya musik yang dinyanyikan.", "A musical composition that is sung."));
        data.add(new RawNode("Laut", "Sea", "Perairan luas yang mengelilingi daratan.", "A large body of saltwater that is smaller than an ocean."));
        data.add(new RawNode("Lambat", "Slow", "Tidak cepat, bergerak dengan kecepatan rendah.", "Not fast, moving with little speed."));
        data.add(new RawNode("Lampu", "Lamp", "Sumber cahaya buatan.", "A device that produces light."));
        data.add(new RawNode("Langit", "Sky", "Ruang di atas bumi yang terlihat biru pada siang hari.", "The expanse above the earth that we see from the surface."));
        data.add(new RawNode("Lapar", "Hungry", "Keadaan tubuh yang memerlukan makanan.", "The feeling of needing food."));
        data.add(new RawNode("Lemah", "Weak", "Tidak kuat atau mudah patah.", "Not strong or easily broken."));
        data.add(new RawNode("Lembut", "Soft", "Tidak keras, memiliki tekstur halus.", "Not hard, with a smooth texture."));
        data.add(new RawNode("Lingkungan", "Environment", "Area sekitar yang memengaruhi kehidupan.", "The surrounding conditions that affect living beings."));
        data.add(new RawNode("Luka", "Wound", "Cedera pada tubuh akibat sesuatu.", "An injury to the body caused by something sharp or forceful."));
        data.add(new RawNode("Lurus", "Straight", "Tidak berbelok, dalam jalur yang sama.", "Not curved, in the same direction."));
        data.add(new RawNode("Manis", "Sweet", "Rasa yang menyenangkan seperti gula.", "Having a pleasant taste like sugar."));
        data.add(new RawNode("Matahari", "Sun", "Bintang yang memberikan cahaya ke bumi.", "The star that provides light to the earth."));
        data.add(new RawNode("Melodi", "Melody", "Susunan nada yang harmonis.", "A sequence of harmonious notes."));
        data.add(new RawNode("Mesra", "Affectionate", "Menunjukkan rasa cinta atau kasih sayang.", "Showing love or affection."));
        data.add(new RawNode("Mimpi", "Dream", "Pengalaman atau bayangan saat tidur.", "An experience or vision during sleep."));
        data.add(new RawNode("Nyaman", "Comfortable", "Membuat perasaan tenang dan puas.", "Providing ease and satisfaction."));
        data.add(new RawNode("Pantai", "Beach", "Daerah berpasir di tepi laut.", "A sandy area along the shore."));
        data.add(new RawNode("Pekerjaan", "Job", "Tugas atau aktivitas yang dilakukan untuk tujuan tertentu.", "A task or activity done for a specific purpose."));
        data.add(new RawNode("Pemandangan", "View", "Gambaran indah dari suatu tempat.", "A beautiful scene from a place."));
        data.add(new RawNode("Pohon", "Tree", "Tumbuhan tinggi dengan batang kayu.", "A tall plant with a wooden trunk."));
        data.add(new RawNode("Rindu", "Miss", "Perasaan ingin bertemu atau berada di dekat seseorang.", "A feeling of wanting to see or be close to someone."));
        data.add(new RawNode("Rumah", "House", "Bangunan tempat tinggal manusia.", "A building where people live."));
        data.add(new RawNode("Sahabat", "Friend", "Orang yang memiliki hubungan dekat dengan kita.", "A person who has a close relationship with us."));
        data.add(new RawNode("Sejuk", "Cool", "Suhu yang menyenangkan, tidak panas atau dingin.", "A pleasant temperature, neither hot nor cold."));
        data.add(new RawNode("Semangat", "Spirit", "Energi atau motivasi untuk melakukan sesuatu.", "Energy or motivation to do something."));
        data.add(new RawNode("Senja", "Dusk", "Waktu menjelang malam setelah matahari terbenam.", "The time just after sunset."));
        data.add(new RawNode("Senyum", "Smile", "Ekspresi wajah yang menunjukkan kebahagiaan.", "A facial expression showing happiness."));
        data.add(new RawNode("Taman", "Garden", "Area yang ditanami tanaman untuk keindahan.", "An area planted with plants for beauty."));
        data.add(new RawNode("Tangan", "Hand", "Bagian tubuh yang digunakan untuk memegang atau menyentuh.", "A body part used for holding or touching."));
        data.add(new RawNode("Teman", "Friend", "Orang yang dikenal dan disukai.", "A person known and liked."));
        data.add(new RawNode("Tenang", "Calm", "Dalam keadaan tidak terganggu atau cemas.", "In a state of not being disturbed or anxious."));
        data.add(new RawNode("Udara", "Air", "Gas yang ada di atmosfer dan diperlukan untuk bernapas.", "The gas in the atmosphere needed for breathing."));
        data.add(new RawNode("Unggas", "Poultry", "Burung yang dibudidayakan untuk daging atau telur.", "Birds raised for meat or eggs."));
        data.add(new RawNode("Warna", "Color", "Ciri visual dari cahaya yang terlihat oleh mata.", "The visual characteristic of light seen by the eye."));
        data.add(new RawNode("Waktu", "Time", "Periode yang dapat diukur.", "A measurable period."));
        data.add(new RawNode("Zebra", "Zebra", "Hewan berloreng hitam dan putih.", "An animal with black and white stripes."));
        data.add(new RawNode("Zikir", "Remembrance", "Mengulang nama Tuhan atau doa.", "Repeating God's name or prayers."));

        for (RawNode entry : data) {
            dataINtoEN.insert(entry.word, entry.wordTranslated, entry.desc, entry.descriptionTranslated, entry.gimmick);
            dataENtoIN.insert(entry.wordTranslated, entry.word, entry.descriptionTranslated, entry.desc, entry.gimmick);
        }
    }
}
