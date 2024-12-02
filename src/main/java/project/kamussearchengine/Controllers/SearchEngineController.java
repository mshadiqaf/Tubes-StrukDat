package project.kamussearchengine.Controllers;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import project.kamussearchengine.Models.Node;
import project.kamussearchengine.Models.RawNode;
import project.kamussearchengine.Models.RedBlackTree;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchEngineController implements Initializable {

    private static final ArrayList<RawNode> allData = new ArrayList<>();
    private static final RedBlackTree dataINtoEN = new RedBlackTree();
    private static final RedBlackTree dataENtoIN = new RedBlackTree();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ToggleButton languageSwitch;

    @FXML
    private VBox vBox;

    @FXML
    private VBox vBoxResult;

    @FXML
    private TextField searchBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            search();
        });

        inputData();

        dataENtoIN.printTree();

    }

    @FXML
    private void search() {
        resetEffect();

        String inputText = searchBar.getText().trim();
        vBoxResult.getChildren().clear();

        if (inputText.isEmpty()) {
            Label noInput = new Label("Masukkan teks untuk pencarian.");
            noInput.setStyle("-fx-font-size: 14; -fx-font-style: italic");
            VBox.setMargin(noInput, new Insets(10, 0, 10, 0)); // Menambahkan margin
            vBoxResult.getChildren().add(noInput);
            return;
        }

        System.out.println(languageSwitch.isSelected());

        boolean isIndoToEng = languageSwitch.isSelected();

        List<Node> allNodes = isIndoToEng ? dataENtoIN.getData() : dataINtoEN.getData();
        boolean foundContains = false;

        for (Node node : allNodes) {
            VBox dataContainer = new VBox();

            if (node.getWord().toLowerCase().startsWith(inputText.toLowerCase())) {
                System.out.println("Word: " + node.getWord());

                Label resultLabel = new Label(node.getWord());
                resultLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
                VBox.setMargin(resultLabel, new Insets(12, 12, 5, 12)); // Menambahkan margin
                dataContainer.getChildren().add(resultLabel);

                Label wordTranslatedLabel = new Label("Bahasa " + (isIndoToEng ? "Indonesia: " + node.getWordTranslated() : "Inggris: " + node.getWordTranslated()));
                wordTranslatedLabel.setStyle("-fx-font-size: 14;");
                VBox.setMargin(wordTranslatedLabel, new Insets(0, 12, 5, 12));
                dataContainer.getChildren().add(wordTranslatedLabel);

                Label descIDLabel = new Label("Deskripsi : " + node.getDesc());
                descIDLabel.setStyle("-fx-font-size: 14;");
                VBox.setMargin(descIDLabel, new Insets(0, 12, 5, 12));
                dataContainer.getChildren().add(descIDLabel);

                Label descENLabel = new Label("Deskripsi (EN): " + node.getDescriptionTranslated());
                descENLabel.setStyle("-fx-font-size: 14;");
                VBox.setMargin(descENLabel, new Insets(0, 12, 12, 12));
                dataContainer.getChildren().add(descENLabel);

                if (node.getWord().equalsIgnoreCase(inputText)) {
                    resultLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18; -fx-font-weight: bold;");
                    wordTranslatedLabel.setStyle("-fx-font-size: 14; -fx-text-fill: red;");
                    descIDLabel.setStyle("-fx-font-size: 14; -fx-text-fill: red;");
                    descENLabel.setStyle("-fx-font-size: 14; -fx-text-fill: red;");
                }

                dataContainer.getStyleClass().add("resultcontainer");
                VBox.setMargin(dataContainer, new Insets(0, 12, 5, 12));

                vBoxResult.getChildren().add(dataContainer);

                foundContains = true;
            }
        }

        if (!foundContains) {
            Label noResults = new Label("Hasil tidak ditemukan.");
            noResults.setStyle("-fx-font-size: 14; -fx-font-style: italic");
            VBox.setMargin(noResults, new Insets(10, 0, 10, 0)); // Menambahkan margin
            vBoxResult.getChildren().add(noResults);
        }
    }

    @FXML
    private void searchGimmick() {

        boolean isIndoToEng = languageSwitch.isSelected();

        String inputText = searchBar.getText().trim();

        List<Node> allNodes = isIndoToEng ? dataENtoIN.getData() : dataINtoEN.getData();

        for (Node node : allNodes) {
            if (node.getWord().equalsIgnoreCase(inputText)) {
                Runnable gimmick = node.getGimmick();
                if (gimmick != null) {
                    System.out.println("Menjalankan gimmick untuk kata: " + node.getWord());
                    gimmick.run();
                }
            }
        }
    }

    private Runnable blur() {
        return () -> {

            BoxBlur blur = new BoxBlur();
            blur.setWidth(5);
            blur.setHeight(5);
            blur.setIterations(3);

            rootPane.setEffect(blur);

            System.out.println("Gimmick berhasil dijalankan!");

        };
    }

    private Runnable flip3D() {
        return () -> {
            // Membuat efek flip 3D pada rootPane
            RotateTransition flipTransition = new RotateTransition(Duration.seconds(1), rootPane);
            flipTransition.setAxis(Rotate.Y_AXIS); // Flip pada sumbu Y
            flipTransition.setFromAngle(0);
            flipTransition.setToAngle(180); // Flip 180 derajat
            flipTransition.setInterpolator(Interpolator.EASE_BOTH);
            flipTransition.setCycleCount(1);

            flipTransition.setOnFinished(event -> {
                RotateTransition resetFlip = new RotateTransition(Duration.seconds(1), rootPane);
                resetFlip.setAxis(Rotate.Y_AXIS); // Sama seperti flip pertama
                resetFlip.setFromAngle(180);
                resetFlip.setToAngle(0);
                resetFlip.setInterpolator(Interpolator.EASE_BOTH);
                resetFlip.setCycleCount(1);
                resetFlip.play();
            });

            flipTransition.play();
        };
    }

    private void resetEffect() {
        rootPane.setEffect(null); // Hilangkan efek blur
        System.out.println("Efek direset ke kondisi semula!");
    }

    private void inputData() {

        allData.add(new RawNode("Abadi", "Eternal", "Tidak pernah berakhir, berlangsung selamanya.", "Never-ending, lasts forever."));
        allData.add(new RawNode("Abadi Selamanya", "Antos", "Tidak pernah berakhir, berlangsung selamanya.", "Never-ending, lasts forever."));
        allData.add(new RawNode("Adil", "Fair", "Tidak memihak, sesuai aturan atau keadilan.", "Impartial, in accordance with justice."));
        allData.add(new RawNode("Aktif", "Active", "Selalu bergerak atau berpartisipasi.", "Always moving or participating."));
        allData.add(new RawNode("Alam", "Nature", "Lingkungan fisik yang ada di sekitar kita.", "The physical environment around us."));
        allData.add(new RawNode("Amal", "Charity", "Perbuatan baik untuk membantu orang lain.", "Acts of kindness to help others."));
        allData.add(new RawNode("Angin", "Wind", "Udara yang bergerak karena perbedaan tekanan.", "Air that moves due to pressure differences."));
        allData.add(new RawNode("Anjing", "Dog", "Hewan peliharaan yang setia.", "A loyal domestic animal."));
        allData.add(new RawNode("Api", "Fire", "Panas dan cahaya yang dihasilkan oleh pembakaran.", "Heat and light produced by combustion."));
        allData.add(new RawNode("Arti", "Meaning", "Makna sesuatu, baik kata maupun simbol.", "The definition of something, whether a word or symbol."));
        allData.add(new RawNode("Asli", "Original", "Bukan tiruan, berasal dari sumbernya.", "Not a copy, from the source."));
        allData.add(new RawNode("Badai", "Storm", "Cuaca ekstrem dengan angin kencang dan hujan deras.", "Extreme weather with strong winds and heavy rain."));
        allData.add(new RawNode("Bahasa", "Language", "Sistem komunikasi manusia.", "A system of human communication."));
        allData.add(new RawNode("Bahu", "Shoulder", "Bagian tubuh di antara leher dan lengan.", "The body part between the neck and arms."));
        allData.add(new RawNode("Baja", "Steel", "Logam yang kuat dan tahan lama.", "A strong and durable metal."));
        allData.add(new RawNode("Banyak", "Many", "Jumlah yang besar.", "A large number."));
        allData.add(new RawNode("Baru", "New", "Belum lama ada atau digunakan.", "Recently created or used."));
        allData.add(new RawNode("Balik", "Flip", "Mengubah posisi atau arah objek secara cepat, sering kali dengan rotasi atau pembalikan.", "To change the position or direction of an object quickly, often by rotation or inversion.", flip3D()));
        allData.add(new RawNode("Bebas", "Free", "Tidak terikat, memiliki kebebasan.", "Not bound, having freedom."));
        allData.add(new RawNode("Belajar", "Learn", "Proses mendapatkan pengetahuan.", "The process of gaining knowledge."));
        allData.add(new RawNode("Besar", "Big", "Ukuran yang lebih dari rata-rata.", "Larger in size than average."));
        allData.add(new RawNode("Buram", "Blur", "Menjadi tidak jelas atau kabur, terutama akibat efek visual.", "Becoming unclear or fuzzy, especially due to a visual effect.", blur()));
        allData.add(new RawNode("Bicara", "Talk", "Berkomunikasi dengan kata-kata.", "To communicate using words."));
        allData.add(new RawNode("Cahaya", "Light", "Energi yang terlihat dan membuat kita bisa melihat.", "Visible energy that allows us to see."));
        allData.add(new RawNode("Cinta", "Love", "Perasaan kasih sayang yang mendalam.", "A deep feeling of affection."));
        allData.add(new RawNode("Cepat", "Fast", "Bergerak dengan kecepatan tinggi.", "Moving with high speed."));
        allData.add(new RawNode("Cermin", "Mirror", "Permukaan yang memantulkan gambar.", "A surface that reflects images."));
        allData.add(new RawNode("Cerah", "Bright", "Terang, tidak gelap.", "Having a lot of light, not dark."));
        allData.add(new RawNode("Cerita", "Story", "Kisah atau narasi suatu kejadian.", "A narrative about an event."));
        allData.add(new RawNode("Cipta", "Create", "Membuat sesuatu yang baru.", "To make something new."));
        allData.add(new RawNode("Cuaca", "Weather", "Keadaan atmosfer pada waktu tertentu.", "The atmospheric conditions at a given time."));
        allData.add(new RawNode("Curah", "Rainfall", "Jumlah hujan yang turun dalam periode tertentu.", "The amount of rain that falls over a period of time."));
        allData.add(new RawNode("Cukai", "Tax", "Pajak yang dikenakan pada barang tertentu.", "A fee levied on goods or services."));
        allData.add(new RawNode("Damai", "Peace", "Keadaan tanpa konflik atau kekerasan.", "A state of harmony without conflict."));
        allData.add(new RawNode("Daya", "Power", "Kemampuan untuk melakukan sesuatu.", "The ability to do something or act in a particular way."));
        allData.add(new RawNode("Dingin", "Cold", "Suhu yang rendah.", "Having a low temperature."));
        allData.add(new RawNode("Dosa", "Sin", "Perbuatan yang dianggap salah secara moral atau agama.", "An immoral act considered wrong by religious standards."));
        allData.add(new RawNode("Dunia", "World", "Planet tempat manusia hidup atau alam semesta.", "The earth or the universe in which humans live."));
        allData.add(new RawNode("Duduk", "Sit", "Posisi tubuh dengan pantat berada di permukaan.", "A position where the body is resting on the lower part, usually on a chair."));
        allData.add(new RawNode("Dukun", "Shaman", "Orang yang dianggap memiliki kemampuan supranatural.", "A person believed to have supernatural powers."));
        allData.add(new RawNode("Dalam", "Deep", "Jarak ke bagian bawah yang jauh dari permukaan.", "Far down from the surface."));
        allData.add(new RawNode("Dewasa", "Adult", "Telah mencapai tahap kematangan fisik dan mental.", "Reached a mature state physically and mentally."));
        allData.add(new RawNode("Dokter", "Doctor", "Profesional medis yang merawat pasien.", "A professional who treats patients."));
        allData.add(new RawNode("Ekonomi", "Economy", "Sistem pengelolaan sumber daya dan keuangan.", "The system of managing resources and finances."));
        allData.add(new RawNode("Emas", "Gold", "Logam mulia berwarna kuning.", "A valuable yellow metal."));
        allData.add(new RawNode("Energi", "Energy", "Kekuatan untuk melakukan kerja.", "The capacity to perform work or cause change."));
        allData.add(new RawNode("Ekor", "Tail", "Bagian tubuh belakang pada hewan.", "The rear part of an animal's body."));
        allData.add(new RawNode("Elang", "Eagle", "Burung pemangsa yang besar dan kuat.", "A large, strong bird of prey."));
        allData.add(new RawNode("Es", "Ice", "Air dalam keadaan beku.", "Frozen water."));
        allData.add(new RawNode("Etika", "Ethics", "Prinsip moral yang mengatur perilaku.", "Moral principles that govern behavior."));
        allData.add(new RawNode("Eksperimen", "Experiment", "Proses uji coba untuk menemukan fakta.", "A scientific procedure to test a hypothesis."));
        allData.add(new RawNode("Efek", "Effect", "Hasil atau dampak dari suatu tindakan.", "The result or outcome of an action."));
        allData.add(new RawNode("Enak", "Delicious", "Rasa yang menyenangkan di lidah.", "Pleasing to the taste."));
        allData.add(new RawNode("Fakta", "Fact", "Sesuatu yang nyata dan dapat dibuktikan.", "Something that is true and can be verified."));
        allData.add(new RawNode("Fasilitas", "Facility", "Sarana untuk membantu kegiatan tertentu.", "Resources or equipment that aid an activity."));
        allData.add(new RawNode("Film", "Movie", "Cerita yang direkam untuk ditonton.", "A recorded story for viewing."));
        allData.add(new RawNode("Foto", "Photo", "Gambar yang dihasilkan oleh kamera.", "An image created with a camera."));
        allData.add(new RawNode("Fungsi", "Function", "Peran atau kegunaan sesuatu.", "The purpose or role of something."));
        allData.add(new RawNode("Gagah", "Brave", "Memiliki keberanian.", "Showing courage and determination."));
        allData.add(new RawNode("Galak", "Fierce", "Bersikap keras atau menakutkan.", "Showing aggression or intensity."));
        allData.add(new RawNode("Garis", "Line", "Tanda memanjang yang dibuat oleh pena atau benda lainnya.", "A long, narrow mark."));
        allData.add(new RawNode("Gaya", "Style", "Cara atau bentuk ekspresi.", "A particular way of doing something."));
        allData.add(new RawNode("Gembira", "Happy", "Merasa senang dan puas.", "Feeling or showing joy."));
        allData.add(new RawNode("Gempa", "Earthquake", "Getaran yang terjadi di permukaan bumi akibat pergerakan lempeng.", "Vibrations that occur on the surface of the earth due to tectonic movements."));
        allData.add(new RawNode("Gelap", "Dark", "Tidak ada cahaya, sulit untuk dilihat.", "Without light, hard to see."));
        allData.add(new RawNode("Gelombang", "Wave", "Pergerakan naik-turun air atau getaran.", "A rising and falling motion of water or vibration."));
        allData.add(new RawNode("Gemuk", "Fat", "Memiliki berat badan yang lebih besar dari rata-rata.", "Having a larger body size than average."));
        allData.add(new RawNode("Gigi", "Teeth", "Bagian tubuh di dalam mulut untuk mengunyah makanan.", "Hard structures in the mouth used for chewing."));
        allData.add(new RawNode("Hadir", "Present", "Berada di suatu tempat.", "Being in a specific location."));
        allData.add(new RawNode("Halus", "Smooth", "Tidak kasar, memiliki permukaan yang rata.", "Having a flat and even surface, not rough."));
        allData.add(new RawNode("Harapan", "Hope", "Keinginan atau cita-cita untuk masa depan.", "A desire for something to happen in the future."));
        allData.add(new RawNode("Hati", "Heart", "Organ tubuh yang memompa darah, juga sering diartikan sebagai pusat perasaan.", "An organ that pumps blood, also symbolizes emotions."));
        allData.add(new RawNode("Hewan", "Animal", "Makhluk hidup yang bukan tumbuhan atau manusia.", "A living organism that is not a plant or human."));
        allData.add(new RawNode("Hijau", "Green", "Warna yang sering diasosiasikan dengan dedaunan.", "A color often associated with plants or nature."));
        allData.add(new RawNode("Hujan", "Rain", "Air yang jatuh dari awan ke bumi.", "Water that falls from clouds."));
        allData.add(new RawNode("Hutan", "Forest", "Kawasan luas yang dipenuhi pohon.", "A large area covered chiefly with trees."));
        allData.add(new RawNode("Harga", "Price", "Nilai yang ditentukan untuk suatu barang atau jasa.", "The amount of money required for something."));
        allData.add(new RawNode("Hebat", "Great", "Luar biasa, sangat bagus.", "Remarkable or impressive."));
        allData.add(new RawNode("Ilmu", "Science", "Pengetahuan yang didapatkan melalui penelitian.", "Knowledge gained through study or experimentation."));
        allData.add(new RawNode("Indah", "Beautiful", "Sangat menarik atau menyenangkan untuk dilihat.", "Attractive and pleasant to look at."));
        allData.add(new RawNode("Impian", "Dream", "Harapan atau cita-cita.", "A hope or desire for the future."));
        allData.add(new RawNode("Istirahat", "Rest", "Menghentikan aktivitas untuk memulihkan tenaga.", "Taking a break to recover energy."));
        allData.add(new RawNode("Ikan", "Fish", "Hewan air yang bernapas dengan insang.", "A water-dwelling animal that breathes through gills."));
        allData.add(new RawNode("Ikhlas", "Sincere", "Melakukan sesuatu dengan hati yang tulus.", "Doing something with genuine intentions."));
        allData.add(new RawNode("Isi", "Content", "Apa yang ada di dalam sesuatu.", "What is contained within something."));
        allData.add(new RawNode("Ingat", "Remember", "Tidak lupa, menyimpan dalam memori.", "To retain information in memory."));
        allData.add(new RawNode("Industri", "Industry", "Aktivitas ekonomi untuk menghasilkan barang atau jasa.", "The production of goods or services."));
        allData.add(new RawNode("Identitas", "Identity", "Karakteristik unik yang membedakan seseorang atau sesuatu.", "The characteristics that distinguish someone or something."));
        allData.add(new RawNode("Jalan", "Road", "Lintasan untuk kendaraan atau pejalan kaki.", "A path or route for vehicles or pedestrians."));
        allData.add(new RawNode("Jantung", "Heart", "Organ vital yang memompa darah.", "The organ that pumps blood throughout the body."));
        allData.add(new RawNode("Jauh", "Far", "Tidak dekat, memiliki jarak yang panjang.", "A great distance from something."));
        allData.add(new RawNode("Jendela", "Window", "Bukaan di dinding untuk ventilasi atau cahaya.", "An opening in a wall or structure for light or air."));
        allData.add(new RawNode("Jenis", "Type", "Kategori atau golongan tertentu.", "A category or kind of something."));
        allData.add(new RawNode("Jejak", "Footprint", "Tanda yang ditinggalkan di permukaan setelah dilalui.", "A mark left behind after something passes."));
        allData.add(new RawNode("Jernih", "Clear", "Tidak keruh, transparan.", "Transparent or easy to see through."));
        allData.add(new RawNode("Jujur", "Honest", "Tidak berbohong, berkata sesuai kenyataan.", "Truthful, not lying or deceiving."));
        allData.add(new RawNode("Jurang", "Cliff", "Lembah yang dalam dan curam.", "A steep face of rock or earth."));
        allData.add(new RawNode("Jutaan", "Millions", "Dalam jumlah sangat banyak, setara dengan satu juta.", "A very large number, equivalent to one million."));
        allData.add(new RawNode("Kaca", "Glass", "Bahan transparan yang digunakan untuk jendela atau cermin.", "A transparent material used for windows or mirrors."));
        allData.add(new RawNode("Kadar", "Level", "Ukuran atau jumlah tertentu.", "A specific amount or degree of something."));
        allData.add(new RawNode("Kado", "Gift", "Hadiah yang diberikan kepada seseorang.", "A present given to someone."));
        allData.add(new RawNode("Kamar", "Room", "Ruangan dalam bangunan untuk aktivitas tertentu.", "A space within a building for a particular purpose."));
        allData.add(new RawNode("Kapal", "Ship", "Kendaraan besar untuk perjalanan di air.", "A large vessel for traveling on water."));
        allData.add(new RawNode("Kartu", "Card", "Lembar kecil dari kertas atau plastik untuk tujuan tertentu.", "A small piece of paper or plastic used for various purposes."));
        allData.add(new RawNode("Kasih", "Affection", "Perasaan cinta dan perhatian.", "A feeling of love or care."));
        allData.add(new RawNode("Kaya", "Rich", "Memiliki banyak harta.", "Having a large amount of wealth."));
        allData.add(new RawNode("Kecil", "Small", "Ukuran yang lebih kecil dari rata-rata.", "A size smaller than average."));
        allData.add(new RawNode("Keindahan", "Beauty", "Keadaan yang menyenangkan untuk dilihat.", "The quality of being aesthetically pleasing."));
        allData.add(new RawNode("Lagu", "Song", "Karya musik yang dinyanyikan.", "A musical composition that is sung."));
        allData.add(new RawNode("Laut", "Sea", "Perairan luas yang mengelilingi daratan.", "A large body of saltwater that is smaller than an ocean."));
        allData.add(new RawNode("Lambat", "Slow", "Tidak cepat, bergerak dengan kecepatan rendah.", "Not fast, moving with little speed."));
        allData.add(new RawNode("Lampu", "Lamp", "Sumber cahaya buatan.", "A device that produces light."));
        allData.add(new RawNode("Langit", "Sky", "Ruang di atas bumi yang terlihat biru pada siang hari.", "The expanse above the earth that we see from the surface."));
        allData.add(new RawNode("Lapar", "Hungry", "Keadaan tubuh yang memerlukan makanan.", "The feeling of needing food."));
        allData.add(new RawNode("Lemah", "Weak", "Tidak kuat atau mudah patah.", "Not strong or easily broken."));
        allData.add(new RawNode("Lembut", "Soft", "Tidak keras, memiliki tekstur halus.", "Not hard, with a smooth texture."));
        allData.add(new RawNode("Lingkungan", "Environment", "Area sekitar yang memengaruhi kehidupan.", "The surrounding conditions that affect living beings."));
        allData.add(new RawNode("Luka", "Wound", "Cedera pada tubuh akibat sesuatu.", "An injury to the body caused by something sharp or forceful."));
        allData.add(new RawNode("Lurus", "Straight", "Tidak berbelok, dalam jalur yang sama.", "Not curved, in the same direction."));
        allData.add(new RawNode("Manis", "Sweet", "Rasa yang menyenangkan seperti gula.", "Having a pleasant taste like sugar."));
        allData.add(new RawNode("Matahari", "Sun", "Bintang yang memberikan cahaya ke bumi.", "The star that provides light to the earth."));
        allData.add(new RawNode("Melodi", "Melody", "Susunan nada yang harmonis.", "A sequence of harmonious notes."));
        allData.add(new RawNode("Mesra", "Affectionate", "Menunjukkan rasa cinta atau kasih sayang.", "Showing love or affection."));
        allData.add(new RawNode("Mimpi", "Dream", "Pengalaman atau bayangan saat tidur.", "An experience or vision during sleep."));
        allData.add(new RawNode("Nyaman", "Comfortable", "Membuat perasaan tenang dan puas.", "Providing ease and satisfaction."));
        allData.add(new RawNode("Pantai", "Beach", "Daerah berpasir di tepi laut.", "A sandy area along the shore."));
        allData.add(new RawNode("Pekerjaan", "Job", "Tugas atau aktivitas yang dilakukan untuk tujuan tertentu.", "A task or activity done for a specific purpose."));
        allData.add(new RawNode("Pemandangan", "View", "Gambaran indah dari suatu tempat.", "A beautiful scene from a place."));
        allData.add(new RawNode("Pohon", "Tree", "Tumbuhan tinggi dengan batang kayu.", "A tall plant with a wooden trunk."));
        allData.add(new RawNode("Rindu", "Miss", "Perasaan ingin bertemu atau berada di dekat seseorang.", "A feeling of wanting to see or be close to someone."));
        allData.add(new RawNode("Rumah", "House", "Bangunan tempat tinggal manusia.", "A building where people live."));
        allData.add(new RawNode("Sahabat", "Friend", "Orang yang memiliki hubungan dekat dengan kita.", "A person who has a close relationship with us."));
        allData.add(new RawNode("Sejuk", "Cool", "Suhu yang menyenangkan, tidak panas atau dingin.", "A pleasant temperature, neither hot nor cold."));
        allData.add(new RawNode("Semangat", "Spirit", "Energi atau motivasi untuk melakukan sesuatu.", "Energy or motivation to do something."));
        allData.add(new RawNode("Senja", "Dusk", "Waktu menjelang malam setelah matahari terbenam.", "The time just after sunset."));
        allData.add(new RawNode("Senyum", "Smile", "Ekspresi wajah yang menunjukkan kebahagiaan.", "A facial expression showing happiness."));
        allData.add(new RawNode("Taman", "Garden", "Area yang ditanami tanaman untuk keindahan.", "An area planted with plants for beauty."));
        allData.add(new RawNode("Tangan", "Hand", "Bagian tubuh yang digunakan untuk memegang atau menyentuh.", "A body part used for holding or touching."));
        allData.add(new RawNode("Teman", "Friend", "Orang yang dikenal dan disukai.", "A person known and liked."));
        allData.add(new RawNode("Tenang", "Calm", "Dalam keadaan tidak terganggu atau cemas.", "In a state of not being disturbed or anxious."));
        allData.add(new RawNode("Udara", "Air", "Gas yang ada di atmosfer dan diperlukan untuk bernapas.", "The gas in the atmosphere needed for breathing."));
        allData.add(new RawNode("Unggas", "Poultry", "Burung yang dibudidayakan untuk daging atau telur.", "Birds raised for meat or eggs."));
        allData.add(new RawNode("Warna", "Color", "Ciri visual dari cahaya yang terlihat oleh mata.", "The visual characteristic of light seen by the eye."));
        allData.add(new RawNode("Waktu", "Time", "Periode yang dapat diukur.", "A measurable period."));
        allData.add(new RawNode("Zebra", "Zebra", "Hewan berloreng hitam dan putih.", "An animal with black and white stripes."));
        allData.add(new RawNode("Zikir", "Remembrance", "Mengulang nama Tuhan atau doa.", "Repeating God's name or prayers."));

        for (RawNode data : allData) {
            dataINtoEN.insert(data.word, data.wordTranslated, data.desc, data.descriptionTranslated, data.gimmick);
            dataENtoIN.insert(data.wordTranslated, data.word, data.descriptionTranslated, data.desc, data.gimmick);
        }

    }
}
