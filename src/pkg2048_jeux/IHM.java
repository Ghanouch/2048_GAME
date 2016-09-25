/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048_jeux;

import com.sun.nio.sctp.AssociationChangeNotification;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Objects;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

/**
 *
 * @author l.IsSaM.l
 */
public class IHM extends javax.swing.JFrame {

    private static boolean MAT_MANIP_2(Integer[][] MAT) {
        boolean DECALAGE = MAT_TEST(MAT);
        boolean MANIP = false;
        for (int j = 0; j <= 3; j++) {
            for (int L = 3; L >= 1; L--) {
                int i = L;

                if (Objects.equals(MAT[i][j], MAT[i - 1][j]) && MAT[i][j] != 0) {

                    MAT[i][j] *= 2;

                    s += MAT[i][j];
                    MANIP = true;
                    for (int k = i - 1; k >= 1; k--) {
                        MAT[k][j] = MAT[k - 1][j];
                    }
                    MAT[0][j] = 0;
                }
            }
        }
        Score.setText(s.toString());
        return MANIP || DECALAGE;
    }

    /**
     * Creates new form IHM
     */
    static Integer s = new Integer(0);

 
    public IHM() {
        initComponents();
        Remplir_Label();
        this.setResizable(false);
        Integer[][] MAT = Gener_MAT(k);
        MAT_Remplir(MAT);
        this.setLocationRelativeTo(null);
        restart.setVisible(false);
        Loose.setVisible(false);
        Best.setText(Return_Best_Score().toString());

    }

    public static Integer Return_Best_Score() {
        ObjectInputStream ois;
        int score = 0;
        try {
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("score.txt"))));
            score = ois.readInt();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return score;
    }

    public static boolean isBestScore(int nv_score) {
        int score = Return_Best_Score();
        boolean isVrai = false;
        if (nv_score > score) {
            isVrai = true;
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("score.txt"))));
                oos.reset();
                oos.writeInt(nv_score);
                oos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isVrai;
    }

    public static void Remplir_Label() {
        Tab_J[0][0] = M_0_0;
        Tab_J[0][1] = M_0_1;
        Tab_J[0][2] = M_0_2;
        Tab_J[0][3] = M_0_3;

        Tab_J[1][0] = M_1_0;
        Tab_J[1][1] = M_1_1;
        Tab_J[1][2] = M_1_2;
        Tab_J[1][3] = M_1_3;

        Tab_J[2][0] = M_2_0;
        Tab_J[2][1] = M_2_1;
        Tab_J[2][2] = M_2_2;
        Tab_J[2][3] = M_2_3;

        Tab_J[3][0] = M_3_0;
        Tab_J[3][1] = M_3_1;
        Tab_J[3][2] = M_3_2;
        Tab_J[3][3] = M_3_3;

    }

    public static void MAT_Color(Integer[][] MAT) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                  if(MAT[i][j]==0) 
                      Tab_J[i][j].setBackground(Color.white);
                 if (MAT[i][j] == 2) {

                    Tab_J[i][j].setBackground(Color.RED);

                }
                if (MAT[i][j] == 4) {
                    Tab_J[i][j].setBackground(Color.BLUE);
                }
                if (MAT[i][j] == 8) {
                    Tab_J[i][j].setBackground(Color.GREEN);
                }
                if (MAT[i][j] == 16) {
                    Tab_J[i][j].setBackground(Color.PINK);
                }
                if (MAT[i][j] == 32) {
                    Tab_J[i][j].setBackground(Color.ORANGE);
                }
                if (MAT[i][j] == 64) {
                    Tab_J[i][j].setBackground(Color.GRAY);
                }
                if (MAT[i][j] == 128) {
                    Tab_J[i][j].setBackground(Color.magenta);
                }
            }
        }
    }

    public static boolean LINE_ZERO(Integer[][] MAT, int colonne) {
        for (int i = 0; i <= 3; i++) {
            if (MAT[i][colonne] != 0) {
                return false;
            }
        }
        return true;

    }

    public static boolean MAT_TEST(Integer[][] MAT) {
        boolean CALCULATE = false;

        for (int k = 0; k <= 3; k++) {
            if (!LINE_ZERO(MAT, k)) {

                for (int i = 3; i >= 1; i--) {
                    if (MAT[i][k] == 0) {
                        for (int j = i - 1; j >= 0; j--) {
                            if (MAT[j][k] == 0) {
                                continue;
                            }
                            MAT[i][k] = MAT[j][k];
                            MAT[j][k] = 0;
                            CALCULATE = true;
                            break;

                        }
                    }
                }
            }
        }
        return CALCULATE;
    }

    public static boolean Lose(Integer[][] MAT) {
        boolean pleine = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((MAT[i][j]) == 0) {
                    pleine = false;
                }
            }
        }
        return pleine && !MAT_MANIP_2(MAT) && !MAT_MANIP_2(MAT_ROT_LEFT(MAT)) && !MAT_MANIP_2(MAT_ROT_RIGHT(MAT)) && !MAT_MANIP_2(MAT_ROT_LEFT(MAT_ROT_LEFT(MAT)));
    }

    public static void Genere_Nv_Var(Integer[][] MAT,int k) {

        boolean pleine = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((MAT[i][j]) == 0) {
                    pleine = false;
                }
            }
        }
        for(int s=0;s<k;s++)
        {
        if (!pleine) {
            int i, j;
            do {
                i = Generer_0_3();
                j = Generer_0_3();
            } while (MAT[i][j] != 0);
            MAT[i][j] = Math.random() < 0.88 ? 2 : 4;
            
        }
        pleine = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((MAT[i][j]) == 0) {
                    pleine = false;
                }
            }
        }
        }
        MAT_Remplir(MAT);
        if (Lose(MAT)) {
            if (isBestScore(Integer.parseInt(Score.getText()))) {
                JOptionPane.showMessageDialog(null, " Félicitation : " + Score.getText() + " est un noveau score !", "Gratulation", JOptionPane.INFORMATION_MESSAGE);
            }
            Loose.setVisible(true);
            restart.setVisible(true);
        }
    }

    public static void Restart_Game() {
        Loose.setVisible(false);
        restart.setVisible(false);
        Integer[][] MAT = Gener_MAT(2);
        MAT_Remplir(MAT);
        Best.setText(Return_Best_Score().toString());
        Score.setText("0");
        s = 0;

    }

    public static Integer[][] MAT_Load() {
        Integer[][] MAT = new Integer[4][4];
        for(int i=0;i<4;i++)
        {
            for(int j=0 ; j<4; j++)
            {
                MAT[i][j]=Integer.parseInt(Tab_J[i][j].getText().length() == 0 ? "0" : Tab_J[i][j].getText());
            }
        }
        return MAT;
    }

    
    public static void MAT_Remplir(Integer[][] MAT) {
        MAT_Color(MAT);
        for(int i=0; i<4;i++)
        {
            for(int j=0;j<4;j++)
             Tab_J[i][j].setText(MAT[i][j] == 0 ? "" : MAT[i][j].toString());
             
            
        }    
    }

    public static Integer[][] Gener_MAT(int k) {

        Integer[][] MATRICE = new Integer[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                MATRICE[i][j] = 0;
            }
        }

        Integer i = Generer_0_3(), j = Generer_0_3();
        MATRICE[i][j] = Math.random() < 0.88 ? 2 : 4;
        for (int l = 0; l < k - 1; l++) {
            do {
                i = Generer_0_3();
                j = Generer_0_3();
            } while (MATRICE[i][j] != 0);
            MATRICE[i][j] = Math.random() < 0.88 ? 2 : 4;
        }

        return MATRICE;
    }

    public static Integer Generer_0_3() {
        double x = Math.random();
        return (x > 0.75) ? 3 : (x > 0.5) ? 2 : (x > 0.25) ? 1 : 0;

    }

    public static Integer[][] MAT_ROT_LEFT(Integer[][] MAT) {
        Integer[][] Nv_MAT = new Integer[4][4];
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                Nv_MAT[i][j] = MAT[j][3 - i];
            }
        }
        return Nv_MAT;
    }

    public static Integer[][] MAT_ROT_RIGHT(Integer[][] MAT) {
        Integer[][] Nv_MAT = new Integer[4][4];
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                Nv_MAT[j][3 - i] = MAT[i][j];
            }
        }
        return Nv_MAT;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        M_0_1 = new javax.swing.JLabel();
        M_0_0 = new javax.swing.JLabel();
        M_0_2 = new javax.swing.JLabel();
        M_0_3 = new javax.swing.JLabel();
        M_1_1 = new javax.swing.JLabel();
        M_1_0 = new javax.swing.JLabel();
        M_1_2 = new javax.swing.JLabel();
        M_1_3 = new javax.swing.JLabel();
        M_2_1 = new javax.swing.JLabel();
        M_2_0 = new javax.swing.JLabel();
        M_2_2 = new javax.swing.JLabel();
        M_2_3 = new javax.swing.JLabel();
        M_3_1 = new javax.swing.JLabel();
        M_3_0 = new javax.swing.JLabel();
        M_3_2 = new javax.swing.JLabel();
        M_3_3 = new javax.swing.JLabel();
        restart = new javax.swing.JButton();
        Loose = new javax.swing.JLabel();
        M_0_4 = new javax.swing.JLabel();
        Score = new javax.swing.JLabel();
        M_0_6 = new javax.swing.JLabel();
        Best = new javax.swing.JLabel();
        dif = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        M_0_1.setBackground(new java.awt.Color(255, 255, 255));
        M_0_1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_0_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_0_1.setAlignmentX(5.0F);
        M_0_1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(255, 204, 204), new java.awt.Color(255, 204, 204), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 153, 0)));
        M_0_1.setOpaque(true);

        M_0_0.setBackground(new java.awt.Color(255, 255, 255));
        M_0_0.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_0_0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_0_0.setAlignmentX(5.0F);
        M_0_0.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(255, 204, 204), new java.awt.Color(255, 204, 204), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 153, 0)));
        M_0_0.setOpaque(true);

        M_0_2.setBackground(new java.awt.Color(255, 255, 255));
        M_0_2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_0_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_0_2.setAlignmentX(5.0F);
        M_0_2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(255, 204, 204), new java.awt.Color(255, 204, 204), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 153, 0)));
        M_0_2.setOpaque(true);

        M_0_3.setBackground(new java.awt.Color(255, 255, 255));
        M_0_3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_0_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_0_3.setAlignmentX(5.0F);
        M_0_3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(255, 204, 204), new java.awt.Color(255, 204, 204), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 153, 0)));
        M_0_3.setOpaque(true);

        M_1_1.setBackground(new java.awt.Color(255, 255, 255));
        M_1_1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_1_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_1_1.setAlignmentX(5.0F);
        M_1_1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(255, 204, 204), new java.awt.Color(255, 204, 204), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 153, 0)));
        M_1_1.setOpaque(true);

        M_1_0.setBackground(new java.awt.Color(255, 255, 255));
        M_1_0.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_1_0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_1_0.setAlignmentX(5.0F);
        M_1_0.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(255, 204, 204), new java.awt.Color(255, 204, 204), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 153, 0)));
        M_1_0.setOpaque(true);

        M_1_2.setBackground(new java.awt.Color(255, 255, 255));
        M_1_2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_1_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_1_2.setAlignmentX(5.0F);
        M_1_2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(255, 204, 204), new java.awt.Color(255, 204, 204), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 153, 0)));
        M_1_2.setOpaque(true);

        M_1_3.setBackground(new java.awt.Color(255, 255, 255));
        M_1_3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_1_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_1_3.setAlignmentX(5.0F);
        M_1_3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(255, 204, 204), new java.awt.Color(255, 204, 204), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 153, 0)));
        M_1_3.setOpaque(true);

        M_2_1.setBackground(new java.awt.Color(255, 255, 255));
        M_2_1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_2_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_2_1.setAlignmentX(5.0F);
        M_2_1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(255, 204, 204), new java.awt.Color(255, 204, 204), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 153, 0)));
        M_2_1.setOpaque(true);

        M_2_0.setBackground(new java.awt.Color(255, 255, 255));
        M_2_0.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_2_0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_2_0.setAlignmentX(5.0F);
        M_2_0.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(255, 204, 204), new java.awt.Color(255, 204, 204), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 153, 0)));
        M_2_0.setOpaque(true);

        M_2_2.setBackground(new java.awt.Color(255, 255, 255));
        M_2_2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_2_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_2_2.setAlignmentX(5.0F);
        M_2_2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(255, 204, 204), new java.awt.Color(255, 204, 204), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 153, 0)));
        M_2_2.setOpaque(true);

        M_2_3.setBackground(new java.awt.Color(255, 255, 255));
        M_2_3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_2_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_2_3.setAlignmentX(5.0F);
        M_2_3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(255, 204, 204), new java.awt.Color(255, 204, 204), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 153, 0)));
        M_2_3.setOpaque(true);

        M_3_1.setBackground(new java.awt.Color(255, 255, 255));
        M_3_1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_3_1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_3_1.setAlignmentX(5.0F);
        M_3_1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(255, 204, 204), new java.awt.Color(255, 204, 204), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 153, 0)));
        M_3_1.setOpaque(true);

        M_3_0.setBackground(new java.awt.Color(255, 255, 255));
        M_3_0.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_3_0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_3_0.setAlignmentX(5.0F);
        M_3_0.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(255, 204, 204), new java.awt.Color(255, 204, 204), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 153, 0)));
        M_3_0.setOpaque(true);

        M_3_2.setBackground(new java.awt.Color(255, 255, 255));
        M_3_2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_3_2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_3_2.setAlignmentX(5.0F);
        M_3_2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(255, 204, 204), new java.awt.Color(255, 204, 204), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 153, 0)));
        M_3_2.setOpaque(true);

        M_3_3.setBackground(new java.awt.Color(255, 255, 255));
        M_3_3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_3_3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_3_3.setAlignmentX(5.0F);
        M_3_3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(255, 204, 204), new java.awt.Color(255, 204, 204), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 153, 0)));
        M_3_3.setOpaque(true);

        restart.setForeground(new java.awt.Color(255, 51, 51));
        restart.setText("Restart");
        restart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartActionPerformed(evt);
            }
        });
        restart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                restartKeyTyped(evt);
            }
        });

        Loose.setFont(new java.awt.Font("Cooper Black", 0, 12)); // NOI18N
        Loose.setForeground(new java.awt.Color(153, 51, 255));
        Loose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Loose.setText("You Lose   !   :P");
        Loose.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                LooseKeyTyped(evt);
            }
        });

        M_0_4.setBackground(new java.awt.Color(255, 204, 204));
        M_0_4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_0_4.setForeground(new java.awt.Color(51, 153, 255));
        M_0_4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_0_4.setText("Your Score :");
        M_0_4.setAlignmentX(5.0F);
        M_0_4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                M_0_4KeyTyped(evt);
            }
        });

        Score.setBackground(new java.awt.Color(255, 204, 204));
        Score.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Score.setForeground(new java.awt.Color(255, 153, 0));
        Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Score.setAlignmentX(5.0F);
        Score.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 255)));
        Score.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ScoreKeyTyped(evt);
            }
        });

        M_0_6.setBackground(new java.awt.Color(255, 204, 204));
        M_0_6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        M_0_6.setForeground(new java.awt.Color(51, 153, 255));
        M_0_6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        M_0_6.setText("Best Score :");
        M_0_6.setAlignmentX(5.0F);
        M_0_6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                M_0_6KeyTyped(evt);
            }
        });

        Best.setBackground(new java.awt.Color(255, 204, 204));
        Best.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Best.setForeground(new java.awt.Color(255, 153, 0));
        Best.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Best.setAlignmentX(5.0F);
        Best.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 255)));
        Best.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                BestKeyTyped(evt);
            }
        });

        dif.setForeground(new java.awt.Color(102, 204, 255));
        dif.setText("Difficulté");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Loose, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(M_0_6, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Best, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(M_0_4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dif, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(Score, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                        .addComponent(restart, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(M_1_0, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(M_1_1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(M_1_2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(M_1_3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(M_2_0, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(M_2_1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(M_2_2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(M_2_3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(M_3_0, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(M_3_1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(M_3_2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(M_3_3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(M_0_0, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(M_0_1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(M_0_2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(M_0_3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(M_0_6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Best, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Score, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(M_0_4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addComponent(dif, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Loose)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(restart)))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(M_0_1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M_0_3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M_0_0, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M_0_2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(M_1_1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(M_1_2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(M_1_3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(M_1_0, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(110, 110, 110))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(M_2_1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(M_2_2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(M_2_3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(M_2_0, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(M_3_1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M_3_2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M_3_3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M_3_0, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
       

        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyTyped

    private void restartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartActionPerformed
        Restart_Game();        // TODO add your handling code here:
    }//GEN-LAST:event_restartActionPerformed

    private void restartKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_restartKeyTyped
          // TODO add your handling code here:
    }//GEN-LAST:event_restartKeyTyped

    private void LooseKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LooseKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_LooseKeyTyped

    private void BestKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BestKeyTyped
       // TODO add your handling code here:
    }//GEN-LAST:event_BestKeyTyped

    private void ScoreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ScoreKeyTyped
           // TODO add your handling code here:
    }//GEN-LAST:event_ScoreKeyTyped

    private void M_0_6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_M_0_6KeyTyped
              // TODO add your handling code here:
    }//GEN-LAST:event_M_0_6KeyTyped

    private void M_0_4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_M_0_4KeyTyped
         // TODO add your handling code here:
    }//GEN-LAST:event_M_0_4KeyTyped

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
     //   System.out.println(evt.getKeyCode() + "----------------------------");
        if (evt.getKeyChar() == KeyEvent.VK_R) {
            Restart_Game();
        }

        if (evt.getKeyCode() == 40) {
            Integer[][] MAT = MAT_Load();

            if (MAT_MANIP_2(MAT)) {
                Genere_Nv_Var(MAT,k);
            }

            // Affichage(MAT);
            MAT_Remplir(MAT);
        }

        if (evt.getKeyCode() == 38) {
            Integer[][] MAT = MAT_Load();

            Integer[][] MAT_UP = MAT_ROT_RIGHT(MAT_ROT_RIGHT(MAT));

            if (MAT_MANIP_2(MAT_UP)) {
                Genere_Nv_Var(MAT_UP,k);
            }

            MAT = MAT_ROT_LEFT(MAT_ROT_LEFT(MAT_UP));
            // Affichage(MAT);
            MAT_Remplir(MAT);
        }

        if (evt.getKeyCode() == 37) {
            Integer[][] MAT = MAT_Load();

            Integer[][] MAT_LEFT = MAT_ROT_LEFT(MAT);

            if (MAT_MANIP_2(MAT_LEFT)) {
                Genere_Nv_Var(MAT_LEFT,k);
            }

            MAT = MAT_ROT_RIGHT(MAT_LEFT);
            // Affichage(MAT);
            MAT_Remplir(MAT);
        }

        if (evt.getKeyCode() == 39) {
            Integer[][] MAT = MAT_Load();

          //  VERITER(MAT);
            Integer[][] MAT_RIGHT = MAT_ROT_RIGHT(MAT);

            if (MAT_MANIP_2(MAT_RIGHT)) {
                Genere_Nv_Var(MAT_RIGHT,k);
            }

            MAT = MAT_ROT_LEFT(MAT_RIGHT);
            // Affichage(MAT);
            MAT_Remplir(MAT);
        }
    }//GEN-LAST:event_formKeyPressed
    double x, y;
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        x = evt.getLocationOnScreen().x;
        y = evt.getLocationOnScreen().y;
        c = false;

    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // c = true;
    }//GEN-LAST:event_formMouseReleased
    boolean c = false;
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int xx, yy;
        xx = evt.getXOnScreen();
        yy = evt.getYOnScreen();
        Integer[][] MAT = MAT_Load();
        boolean interdit = false;
        if (xx - x > 100 && c == false && !interdit) {
            c = true;
            interdit = true;
            Integer[][] MAT_RIGHT = MAT_ROT_RIGHT(MAT);

            if (MAT_MANIP_2(MAT_RIGHT)) {
                Genere_Nv_Var(MAT_RIGHT,k);
            }

            MAT = MAT_ROT_LEFT(MAT_RIGHT);
            // Affichage(MAT);

        }

        if (x - xx > 100 && c == false && !interdit) {
            c = true;
            Integer[][] MAT_LEFT = MAT_ROT_LEFT(MAT);

            if (MAT_MANIP_2(MAT_LEFT)) {
                Genere_Nv_Var(MAT_LEFT,k);
            }

            MAT = MAT_ROT_RIGHT(MAT_LEFT);
        }
        if (y - yy > 100 && c == false && !interdit) {
            c = true;
            Integer[][] MAT_UP = MAT_ROT_RIGHT(MAT_ROT_RIGHT(MAT));

            if (MAT_MANIP_2(MAT_UP)) {
                Genere_Nv_Var(MAT_UP,k);
            }

            MAT = MAT_ROT_LEFT(MAT_ROT_LEFT(MAT_UP));
        }

        if (yy - y > 100 && c == false && !interdit) {
            c = true;
            if (MAT_MANIP_2(MAT)) {
                Genere_Nv_Var(MAT,k);
            }
        }

        MAT_Remplir(MAT);

    }//GEN-LAST:event_formMouseDragged

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
           // TODO add your handling code here:
    }//GEN-LAST:event_formKeyReleased
    int xj =770 ;
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IHM().setVisible(true);
            }
        });
    }

    protected static JLabel[][] Tab_J = new JLabel[4][4];
    public static int k = 1 ;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected static javax.swing.JLabel Best;
    private static javax.swing.JLabel Loose;
    protected static javax.swing.JLabel M_0_0;
    protected static javax.swing.JLabel M_0_1;
    protected static javax.swing.JLabel M_0_2;
    protected static javax.swing.JLabel M_0_3;
    protected static javax.swing.JLabel M_0_4;
    protected static javax.swing.JLabel M_0_6;
    protected static javax.swing.JLabel M_1_0;
    protected static javax.swing.JLabel M_1_1;
    protected static javax.swing.JLabel M_1_2;
    protected static javax.swing.JLabel M_1_3;
    protected static javax.swing.JLabel M_2_0;
    protected static javax.swing.JLabel M_2_1;
    protected static javax.swing.JLabel M_2_2;
    protected static javax.swing.JLabel M_2_3;
    protected static javax.swing.JLabel M_3_0;
    protected static javax.swing.JLabel M_3_1;
    protected static javax.swing.JLabel M_3_2;
    protected static javax.swing.JLabel M_3_3;
    protected static javax.swing.JLabel Score;
    private javax.swing.JLabel dif;
    private static javax.swing.JButton restart;
    // End of variables declaration//GEN-END:variables
}
