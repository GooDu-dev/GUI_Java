package app_asset;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;

public class App{

    //General value
    private static String title = "GooDu Game";
    private static int app_width = 800;
    private static int app_height = 600;
    private JFrame frame;

    private boolean rePlay=false;

    //start+end menu object
    private JLabel welcome_text = new JLabel("Welcome to my first java app");
    private JButton start_button = new JButton("Start!!");

    //game object
    private JLabel timeCounter = new JLabel();
    private JLabel hangman = new JLabel();

    //image for game
    private ImageIcon hang1;
    private ImageIcon hang2;
    private ImageIcon hang3;
    private ImageIcon hang4;
    private ImageIcon hang5;
    private ImageIcon hang6;
    private ImageIcon hang7;

    // hang size
    private int hang_width = 400;
    private int hang_height = 350;

    // all hang image is collected here
    private int currentImg = 0;
    private ImageIcon[] img_array = new ImageIcon[7];

    //dictionary word
    private ArrayList<String> words = new ArrayList<String>();
    private File wordFile = new File("app_asset/word.txt");
    private String playWordText;
    private JLabel playWord = new JLabel();
    private String word = "", wordCheck = "";
    private JTextField text = new JTextField();

    public App() {
        //Setting overall app
        frame = new JFrame();
        Dimension d = new Dimension(app_width, app_height);
        frame.setSize(d);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle(title);
        frame.setLayout(new FlowLayout());
        rePlay = false;
        startapp();
    }

    public void startapp(){

        actionListener act = new actionListener();

        //Set label
        welcome_text.setPreferredSize(new Dimension(750, 50));
        welcome_text.setHorizontalAlignment(JLabel.CENTER);
        welcome_text.setFont(new Font(welcome_text.getFont().getName(), welcome_text.getFont().getStyle(), 24));
        frame.getContentPane().add(welcome_text);
        
        //Set button
        start_button.setPreferredSize(new Dimension(750, 200));
        start_button.setFont(new Font(start_button.getFont().getName(), start_button.getFont().getStyle(), 48));
        frame.getContentPane().add(start_button);

        //add after pressed button
        // frame.getContentPane().add(testButton);
        
        //add click detection
        start_button.addActionListener(act);

    }

    public void preStartGame(){

        try{

            hang1 = new ImageIcon("app_asset/hang1.png");
            hang2 = new ImageIcon("app_asset/hang2.png");
            hang3 = new ImageIcon("app_asset/hang3.png");
            hang4 = new ImageIcon("app_asset/hang4.png");
            hang5 = new ImageIcon("app_asset/hang5.png");
            hang6 = new ImageIcon("app_asset/hang6.png");
            hang7 = new ImageIcon("app_asset/hang7.png");

            img_array[0] = hang1;
            img_array[1] = hang2;
            img_array[2] = hang3;
            img_array[3] = hang4;
            img_array[4] = hang5;
            img_array[5] = hang6;
            img_array[6] = hang7;

        }catch(Exception e){ System.out.println(e); }

        //add image and resize it with modImage methods
        if(hangman.getIcon() == null){
            hangman.setIcon(modImage(hang1, hang_width, hang_height));
        }
        frame.getContentPane().add(hangman);

        rePlay=true;

        try{
            Scanner scan = new Scanner(wordFile);
            while(scan.hasNextLine()){
                words.add(scan.nextLine());
            }
            scan.close();
        }catch(Exception e){System.out.println(e);}

        keycodeListener kl = new keycodeListener();
        text.addKeyListener(kl);
        
        playWord.setPreferredSize(new Dimension(app_width, 50));
        playWord.setHorizontalAlignment(JLabel.CENTER);
        playWord.setFont(new Font(playWord.getFont().getName(), playWord.getFont().getStyle(), 24));
        frame.getContentPane().add(playWord);
        
        text.setPreferredSize(new Dimension(app_width/2, 50));
        text.setHorizontalAlignment(JLabel.CENTER);
        frame.getContentPane().add(text);

        startGame();

    }

    public void startGame(){
        welcome_text.setVisible(false);
        start_button.setVisible(false);

        hangman.setVisible(true);
        text.setVisible(true);
        playWord.setVisible(true);

        hangman.setIcon(modImage(hang1, hang_width, hang_height));

        int rand = new Random().nextInt(words.size());
        playWordText = words.get(rand);
        for(int i=0; i<playWordText.length(); i++){
            word += "__ ";
        }

        playWord.setText(word);

    }

    //resize image
    private ImageIcon modImage(ImageIcon icon, int width, int height){
        Image image = icon.getImage();
        Image image_mod = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        ImageIcon icon_mod = new ImageIcon(image_mod);
        return icon_mod;
    }

    private void checkHang(Character wordPressed){
        boolean isMatch = false;
        char charMatch='z';
        for(int i=0; i<playWordText.length(); i++){
            if(wordPressed == playWordText.charAt(i)){
                isMatch = true;
                charMatch = playWordText.charAt(i);
            }
        }
        if(!isMatch) changePic();
        else{
            String[] usText = playWord.getText().split(" ");
            String[] words_split = playWordText.split("");
            ArrayList<Integer> matchAt = new ArrayList<Integer>();
            String strMatch = String.valueOf(charMatch);
            String[] newWords = word.split(" ");
            for(int i=0; i<words_split.length; i++){
                if(strMatch.equals(words_split[i])){
                    matchAt.add(i);
                }
            }
            for(int i=0; i<usText.length; i++){
                for(int j=0; j<matchAt.size(); j++){
                    if(i == matchAt.get(j)){
                        newWords[i] = strMatch;
                        continue;
                    }
                }
            }
            word = "";
            wordCheck = "";
            for(String s : newWords){
                word += s + " ";
                wordCheck += s;
            }
            playWord.setText(word);
            
            if(playWordText.equals(wordCheck)){
                endGame();
            }
        }
    }

    //change hangman picture
    private void changePic(){
        currentImg++;
        switch(currentImg){
            case 0:
                hangman.setIcon(modImage(img_array[0], hang_width, hang_height));
                break;
            case 1:
                hangman.setIcon(modImage(img_array[1], hang_width, hang_height));
                break;
            case 2:
                hangman.setIcon(modImage(img_array[2], hang_width, hang_height));
                break;
            case 3:
                hangman.setIcon(modImage(img_array[3], hang_width, hang_height));
                break;
            case 4:
                hangman.setIcon(modImage(img_array[4], hang_width, hang_height));
                break;
            case 5:
                hangman.setIcon(modImage(img_array[5], hang_width, hang_height));
                break;
            case 6:
                hangman.setIcon(modImage(img_array[6], hang_width, hang_height));
                break;
            case 7:
                endGame();
                break;
        }
    }

    private void endGame(){
        hangman.setVisible(false);
        text.setVisible(false);
        playWord.setVisible(false);

        currentImg = 0;
        word="";
        playWordText="";
        wordCheck="";
        
        welcome_text.setVisible(true);
        start_button.setVisible(true);
    }

    private class actionListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent event){
            JButton button = (JButton) event.getSource();
            if(button == start_button){
                if(rePlay) startGame();
                else preStartGame();
            }
        }
    }

    private class keycodeListener extends KeyAdapter{
        public void keyPressed(KeyEvent event){
            checkHang(event.getKeyChar());
        }
    }
}