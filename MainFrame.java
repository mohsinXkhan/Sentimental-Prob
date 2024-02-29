import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Properties;

public class MainFrame extends JFrame {

    private JPanel contentPane;
    private CardLayout cardLayout;
    private JPanel introPanel;
    private JPanel projectPanel;

    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;
    private JLabel lblNewLabel_3;
    private JLabel lblNewLabel_4;
    private DefaultCategoryDataset dataset;
    private JFreeChart barChart;
    private JTextArea textArea;
    private JButton analyzeButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainFrame() {
        super("SentiMatrix");
        setFont(new Font("Arial", Font.BOLD, 12));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        ImageIcon image = new ImageIcon("C:\\Users\\kmohs\\Videos\\Mini Project\\logo.png");
        setIconImage(image.getImage());
        contentPane = new JPanel();
        cardLayout = new CardLayout();
        contentPane.setLayout(cardLayout);
        setContentPane(contentPane);

        // Create and add the introductory panel
        introPanel = createIntroductoryPanel();
        contentPane.add(introPanel, "Intro");

        // Create and add the project panel
        projectPanel = createProjectPanel();
        contentPane.add(projectPanel, "Project");

        // Initially show the introductory panel
        cardLayout.show(contentPane, "Intro");
    }

    private JPanel createIntroductoryPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());

        // Created by label
        JPanel createdByPanel = new JPanel();
        createdByPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        createdByPanel.setBackground(Color.WHITE);
        panel.add(createdByPanel, BorderLayout.CENTER);
        createdByPanel.setLayout(null);

        JLabel lblNewLabel_5 = new JLabel("SentiMatrix - Sentiment Analysis Tool");
        lblNewLabel_5.setForeground(new Color(0, 0, 128));
        lblNewLabel_5.setFont(new Font("BankGothic Md BT", Font.BOLD, 28));
        lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_5.setBounds(10, 33, 764, 131);
        createdByPanel.add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("Created by:");
        lblNewLabel_6.setForeground(new Color(0, 0, 255));
        lblNewLabel_6.setFont(new Font("Tahoma", Font.ITALIC, 14));
        lblNewLabel_6.setBounds(272, 175, 140, 27);
        createdByPanel.add(lblNewLabel_6);

        JLabel lblNewLabel_7 = new JLabel("................................................................................................................................................................................");
        lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_7.setForeground(new Color(100, 149, 237));
        lblNewLabel_7.setBounds(75, 128, 644, 14);
        createdByPanel.add(lblNewLabel_7);

        JLabel lblNewLabel_8 = new JLabel("Mohsin Khan");
        lblNewLabel_8.setForeground(new Color(0, 0, 128));
        lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_8.setBounds(10, 213, 764, 27);
        createdByPanel.add(lblNewLabel_8);

        JLabel lblNewLabel_9 = new JLabel("Yuvraj Kadam");
        lblNewLabel_9.setForeground(new Color(0, 0, 128));
        lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_9.setBounds(10, 251, 764, 27);
        createdByPanel.add(lblNewLabel_9);

        JLabel lblNewLabel_10 = new JLabel("Taqi Khan");
        lblNewLabel_10.setForeground(new Color(0, 0, 128));
        lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel_10.setBounds(10, 289, 764, 27);
        createdByPanel.add(lblNewLabel_10);

        // Next button
        JButton nextButton = new JButton("Next");
        nextButton.setForeground(new Color(230, 230, 250));
        nextButton.setBackground(Color.BLUE);
        nextButton.setBounds(335, 400, 117, 23);
        createdByPanel.add(nextButton);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switch to the project panel when the "Next" button is clicked
                cardLayout.show(contentPane, "Project");
            }
        });

        return panel;
    }

    private JPanel createProjectPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Use absolute layout for precise component placement
        panel.setBackground(Color.white);

        JLabel lblNewLabel = new JLabel("Enter a paragraph for sentiment analysis:");
        lblNewLabel.setBounds(10, 11, 364, 14);
        panel.add(lblNewLabel);

        textArea = new JTextArea();
        textArea.setBounds(10, 31, 764, 96);
        // Create a border and set it around the textArea
        Border border = BorderFactory.createLineBorder(Color.BLACK); // You can change the color to your preference
        textArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        panel.add(textArea);

        lblNewLabel_1 = new JLabel("Positive:");
        lblNewLabel_1.setBounds(10, 187, 181, 23);
        panel.add(lblNewLabel_1);

        lblNewLabel_2 = new JLabel("Negative:");
        lblNewLabel_2.setBounds(10, 216, 181, 23);
        panel.add(lblNewLabel_2);

        lblNewLabel_3 = new JLabel("Neutral:");
        lblNewLabel_3.setBounds(10, 245, 181, 23);
        panel.add(lblNewLabel_3);

        lblNewLabel_4 = new JLabel("Overall Sentiment:");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_4.setBounds(10, 274, 181, 23);
        panel.add(lblNewLabel_4);

        dataset = new DefaultCategoryDataset();
        barChart = ChartFactory.createBarChart(
            "Sentiment Analysis Results", "Sentiment", "Percentage",
            dataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.GREEN);  // Positive series
        renderer.setSeriesPaint(1, Color.RED);    // Negative series
        renderer.setSeriesPaint(2, Color.BLUE);   // Neutral series

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBounds(220, 187, 300, 210);
        chartPanel.setBackground(Color.WHITE);
        panel.add(chartPanel);

        analyzeButton = new JButton("Analyze Sentiment");
        analyzeButton.setForeground(new Color(230, 230, 250));
        analyzeButton.setBackground(Color.BLUE);
        analyzeButton.setBounds(335, 145, 170, 23);
        panel.add(analyzeButton);

        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSentimentAnalysis(textArea.getText());
            }
        });

        return panel;
    }

    private void performSentimentAnalysis(String paragraph) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        CoreDocument document = new CoreDocument(paragraph);
        pipeline.annotate(document);

        int positiveCount = 0;
        int negativeCount = 0;
        int neutralCount = 0;
        int totalSentences = 0;

        List<CoreMap> sentences = document.annotation().get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            totalSentences++;
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);

            if (containsNegation(sentence.toString())) {
                sentiment = negateSentiment(sentiment);
            }

            if (sentiment.equals("Positive")) {
                positiveCount++;
            } else if (sentiment.equals("Negative")) {
                negativeCount++;
            } else if (sentiment.equals("Neutral")) {
                neutralCount++;
            }
        }

        double positivePercentage = (double) positiveCount / totalSentences * 100;
        double negativePercentage = (double) negativeCount / totalSentences * 100;
        double neutralPercentage = (double) neutralCount / totalSentences * 100;

        String overallSentiment = calculateOverallSentiment(positiveCount, negativeCount);

        lblNewLabel_1.setText("Positive: " + String.format("%.2f", positivePercentage) + "%");
        lblNewLabel_2.setText("Negative: " + String.format("%.2f", negativePercentage) + "%");
        lblNewLabel_3.setText("Neutral: " + String.format("%.2f", neutralPercentage) + "%");
        lblNewLabel_4.setText(overallSentiment);

        dataset.clear();
        dataset.addValue(positivePercentage, "Sentiment", "Positive");
        dataset.addValue(negativePercentage, "Sentiment", "Negative");
        dataset.addValue(neutralPercentage, "Sentiment", "Neutral");
    }

    private boolean containsNegation(String sentence) {
        String[] negationWords = {"not", "no", "never"};

        for (String negationWord : negationWords) {
            if (sentence.contains(negationWord)) {
                return true;
            }
        }
        return false;
    }

    private String negateSentiment(String sentiment) {
        if (sentiment.equals("Positive")) {
            return "Negative";
        } else if (sentiment.equals("Negative")) {
            return "Positive";
        }
        return sentiment;
    }

    private String calculateOverallSentiment(int positiveCount, int negativeCount) {
        if (positiveCount > negativeCount) {
            return "Overall sentiment: Positive";
        } else if (negativeCount > positiveCount) {
            return "Overall sentiment: Negative";
        } else {
            return "Overall sentiment: Neutral";
        }
    }
}
