/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgGame.GUI;

import utility.TextAreaOutputStream;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import rpgGame.CombatObject.Player;
import rpgGame.Engine;
import rpgGame.World;
import utility.ButtonInputQueue;

/**
 *
 * @author jestr
 */
public class MainGUI extends javax.swing.JFrame
{
    private ButtonInputQueue buttonInputs;
    private LoadGameFrame loadGameFrame;
    
    /**
     * Creates new form mainGUI
     */
    public MainGUI()
    {
        initComponents();       
        System.setOut(new PrintStream(new TextAreaOutputStream(jTextArea)));
        
        jLabelPlayerHP.setVisible(false);
        jLabelEnemyHP.setVisible(false);
        jProgressBarPlayerHP.setVisible(false);
        jProgressBarPlayerFatigue.setVisible(false);
        jProgressBarEnemyHP.setVisible(false);
        jProgressBarEnemyFatigue.setVisible(false);
        
        // Keeps output window from tearing and overlapping text
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                while (true)
                {
                    try
                    {
                        jTextArea.repaint();
                        Thread.sleep(100);
                    } catch (InterruptedException ex)
                    {
                        Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
        // ---------------------------------------------------------
        
        buttonInputs = new ButtonInputQueue();
        setLocationRelativeTo(null);
        // Please open below code fold to see button action listener implementation.
        // <editor-fold defaultstate="collapsed" desc="Button action listeners"> 
        jButtonOptionOne.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonInputs.addInput(1);
            }
        });
        
        jButtonOptionTwo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonInputs.addInput(2);
            }
        });
        
        jButtonOptionThree.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonInputs.addInput(3);
            }
        });
        
        jButtonOptionFour.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonInputs.addInput(4);
            }
        });
        
        jButtonOptionFive.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonInputs.addInput(5);
            }
        });
        
        jButtonOptionSix.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonInputs.addInput(6);
            }
        });
        
        jButtonQuit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    Engine.get().shutdown();
                } catch (IOException ex)
                {
                    Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        // </editor-fold>
    }
    
    public boolean loadGameDatabase()
    {
        CountDownLatch latch = new CountDownLatch(1);
        if (loadGameFrame == null)
            loadGameFrame = new LoadGameFrame(latch);
        else
        {
            loadGameFrame.setLatch(latch);
            loadGameFrame.setList();
            loadGameFrame.setVisible(true);
        }
        try
        {
            latch.await();
        } catch (InterruptedException ex)
        {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return loadGameFrame.getSuccess();
    }
    
    public String getStringPrompt(String message)
    {   
        String out = JOptionPane.showInputDialog(this, message);

        return out;
    }
    
    public ButtonInputQueue getButtonInputs()
    {
        return buttonInputs;
    }

    public void setHpBars()
    {
        jLabelPlayerHP.setVisible(true);
        jLabelEnemyHP.setVisible(true);
        jProgressBarPlayerHP.setVisible(true);
        jProgressBarPlayerFatigue.setVisible(true);
        jProgressBarEnemyHP.setVisible(true);
        jProgressBarEnemyFatigue.setVisible(true);
        jProgressBarPlayerHP.setMaximum(Player.get().getMaxHealthPoints());
        jProgressBarPlayerFatigue.setMaximum(Player.get().getMaxFatiguePoints());
        jProgressBarPlayerHP.setValue(Player.get().getHealthPoints());
        jProgressBarPlayerFatigue.setValue(Player.get().getFatiguePoints());
        
        if (World.get().getArena().getEnemy() != null)
        {
            jProgressBarEnemyHP.setMaximum(World.get().getArena().getEnemy().getMaxHealthPoints());
            jProgressBarEnemyFatigue.setMaximum(World.get().getArena().getEnemy().getMaxFatiguePoints());
            jProgressBarEnemyHP.setValue(World.get().getArena().getEnemy().getHealthPoints());
            jProgressBarEnemyFatigue.setValue(World.get().getArena().getEnemy().getFatiguePoints());
        }
    }
    
    public void mainMenu()
    {
        jLabelPlayerHP.setVisible(false);
        jLabelEnemyHP.setVisible(false);
        jProgressBarPlayerHP.setVisible(false);
        jProgressBarPlayerFatigue.setVisible(false);
        jProgressBarEnemyHP.setVisible(false);
        jProgressBarEnemyFatigue.setVisible(false);
        try
        {
            imagePanel.setImage(ImageIO.read(new File("./resources/gladiator_arena.jpg")));
        } catch (IOException ex)
        {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextArea.setText("");
        jButtonOptionOne.setVisible(true);
        jButtonOptionOne.setText("<html>New Game<html>");
        
        jButtonOptionTwo.setVisible(true);
        jButtonOptionTwo.setText("<html>Load Game<html>");
        
        jButtonOptionThree.setVisible(false);
        jButtonOptionFour.setVisible(false);
        jButtonOptionFive.setVisible(false);
        jButtonOptionSix.setVisible(true);
        jButtonOptionSix.setText("<html>Import Save<html>");
    }
    
    public void townCenter()
    {
        try
        {
            imagePanel.setImage(ImageIO.read(new File("./resources/town_center.jpg")));
        } catch (IOException ex)
        {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        jLabelPlayerHP.setVisible(false);
        jLabelEnemyHP.setVisible(false);
        jProgressBarPlayerHP.setVisible(false);
        jProgressBarPlayerFatigue.setVisible(false);
        jProgressBarEnemyHP.setVisible(false);
        jProgressBarEnemyFatigue.setVisible(false);
        jTextArea.setText("");
        jButtonOptionOne.setVisible(true);
        jButtonOptionOne.setText("<html>Enter arena<html>");

        jButtonOptionTwo.setVisible(true);
        jButtonOptionTwo.setText("<html>Enter shop<html>");

        jButtonOptionThree.setVisible(true);
        jButtonOptionThree.setText("<html>Manage inventory<html>");
        
        jButtonOptionFour.setVisible(true);
        jButtonOptionFour.setText("<html>Manage stats and assign skill points<html>");
        
        jButtonOptionFive.setVisible(true);
        jButtonOptionFive.setText("<html>Export Save<html>");
        
        jButtonOptionSix.setVisible(true);
        jButtonOptionSix.setText("<html>Main Menu<html>");
    }
    
    public void arena()
    {
        try
        {
            imagePanel.setImage(ImageIO.read(new File("./resources/arena.jpg")));
        } catch (IOException ex)
        {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextArea.setText("");
        jButtonOptionOne.setVisible(true);
        jButtonOptionOne.setText("<html>Attack!<html>");

        jButtonOptionTwo.setVisible(true);
        jButtonOptionTwo.setText("<html>Dodge!<html>");

        jButtonOptionThree.setVisible(true);
        jButtonOptionThree.setText("<html>Rest!<html>");

        jButtonOptionFour.setVisible(false);
        jButtonOptionFive.setVisible(false);
        
        jButtonOptionSix.setVisible(true);
        jButtonOptionSix.setText("<html>Main Menu<html>");
    }
    
    public void shop()
    {
        try
        {
            imagePanel.setImage(ImageIO.read(new File("./resources/shop.jpg")));
        } catch (IOException ex)
        {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextArea.setText("");
        jButtonOptionOne.setVisible(true);
        jButtonOptionOne.setText("<html>Buy<html>");

        jButtonOptionTwo.setVisible(true);
        jButtonOptionTwo.setText("<html>Sell<html>");

        jButtonOptionThree.setVisible(true);
        jButtonOptionThree.setText("<html>Back<html>");

        jButtonOptionFour.setVisible(false);
        jButtonOptionFive.setVisible(false);
        
        jButtonOptionSix.setVisible(true);
        jButtonOptionSix.setText("<html>Main Menu<html>");
    }
    
    public void shopBuy()
    {
        jButtonOptionOne.setVisible(true);
        jButtonOptionOne.setText("<html>Buy an item:<html>");

        jButtonOptionTwo.setVisible(true);
        jButtonOptionTwo.setText("<html>Cancel<html>");

        jButtonOptionThree.setVisible(false);
        jButtonOptionFour.setVisible(false);
        jButtonOptionFive.setVisible(false);
        
        jButtonOptionSix.setVisible(true);
        jButtonOptionSix.setText("<html>Main Menu<html>");
    }
    
    public void shopSell()
    {
        jButtonOptionOne.setVisible(true);
        jButtonOptionOne.setText("<html>Sell an item:<html>");

        jButtonOptionTwo.setVisible(true);
        jButtonOptionTwo.setText("<html>Cancel<html>");

        jButtonOptionThree.setVisible(false);
        jButtonOptionFour.setVisible(false);
        jButtonOptionFive.setVisible(false);
        
        jButtonOptionSix.setVisible(true);
        jButtonOptionSix.setText("<html>Main Menu<html>");
    }
    
    public void manageStats()
    {
        try
        {
            imagePanel.setImage(ImageIO.read(new File("./resources/manage_stats.jpg")));
        } catch (IOException ex)
        {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        jButtonOptionOne.setVisible(true);
        jButtonOptionOne.setText("<html>Yes<html>");

        jButtonOptionTwo.setVisible(true);
        jButtonOptionTwo.setText("<html>Back<html>");

        jButtonOptionThree.setVisible(false);
        jButtonOptionFour.setVisible(false);
        jButtonOptionFive.setVisible(false);
        
        jButtonOptionSix.setVisible(true);
        jButtonOptionSix.setText("<html>Main Menu<html>");
    }
    
    public void allocStats()
    {
        jButtonOptionOne.setVisible(true);
        jButtonOptionOne.setText("<html>Strength<html>");

        jButtonOptionTwo.setVisible(true);
        jButtonOptionTwo.setText("<html>Dexterity<html>");

        jButtonOptionThree.setVisible(true);
        jButtonOptionThree.setText("<html>Vitality<html>");
        
        jButtonOptionFour.setVisible(true);
        jButtonOptionFour.setText("<html>Stamina<html>");
        
        jButtonOptionFive.setVisible(true);
        jButtonOptionFive.setText("<html>Cancel<html>");
        
        jButtonOptionSix.setVisible(true);
        jButtonOptionSix.setText("<html>Main Menu<html>");
    }
    
    public void manageInventory()
    {
        try
        {
            imagePanel.setImage(ImageIO.read(new File("./resources/manage_inventory.jpg")));
        } catch (IOException ex)
        {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        jButtonOptionOne.setVisible(true);
        jButtonOptionOne.setText("<html>Equip/Dequip an item:<html>");

        jButtonOptionTwo.setVisible(true);
        jButtonOptionTwo.setText("<html>Cancel<html>");

        jButtonOptionThree.setVisible(false);
        jButtonOptionFour.setVisible(false);
        jButtonOptionFive.setVisible(false);
        
        jButtonOptionSix.setVisible(true);
        jButtonOptionSix.setText("<html>Main Menu<html>");
    }
    
    
    public void deadScreen()
    {
        try
        {
            imagePanel.setImage(ImageIO.read(new File("./resources/dead.jpg")));
        } catch (IOException ex)
        {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        jButtonOptionOne.setVisible(false);
        jButtonOptionTwo.setVisible(false);
        jButtonOptionThree.setVisible(false);
        jButtonOptionFour.setVisible(false);
        jButtonOptionFive.setVisible(false);
        jButtonOptionSix.setVisible(false);
    }
    
    public int integerQuery()
    {
        int ret = 0;
        
        try
        {
        String out = JOptionPane.showInputDialog(this, "Enter a number:");
        if (out != null)
        {
            ret = Integer.parseInt(out);
        }
        } catch (java.lang.NumberFormatException e)
        {
            ret = 0;
        }
        
        return ret;
    }
    

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanelPicture = new ImagePanel();
        imagePanel = new rpgGame.GUI.ImagePanel();
        jPanel2 = new javax.swing.JPanel();
        jButtonQuit = new javax.swing.JButton();
        jButtonOptionThree = new javax.swing.JButton();
        jButtonOptionFour = new javax.swing.JButton();
        jButtonOptionFive = new javax.swing.JButton();
        jButtonOptionOne = new javax.swing.JButton();
        jButtonOptionSix = new javax.swing.JButton();
        jButtonOptionTwo = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea = new javax.swing.JTextArea();
        jPanelHPBars = new javax.swing.JPanel();
        jProgressBarPlayerHP = new javax.swing.JProgressBar();
        jProgressBarPlayerFatigue = new javax.swing.JProgressBar();
        jLabelEnemyHP = new javax.swing.JLabel();
        jProgressBarEnemyHP = new javax.swing.JProgressBar();
        jProgressBarEnemyFatigue = new javax.swing.JProgressBar();
        jLabelPlayerHP = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Arena Battler");
        setBackground(new java.awt.Color(255, 204, 204));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("mainFrame"); // NOI18N
        setResizable(false);

        javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 191, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelPictureLayout = new javax.swing.GroupLayout(jPanelPicture);
        jPanelPicture.setLayout(jPanelPictureLayout);
        jPanelPictureLayout.setHorizontalGroup(
            jPanelPictureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPictureLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelPictureLayout.setVerticalGroup(
            jPanelPictureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPictureLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonQuit.setText("<html>Save and Quit<html>");
        jPanel2.add(jButtonQuit, new org.netbeans.lib.awtextra.AbsoluteConstraints(796, 11, 114, 88));

        jButtonOptionThree.setText("Option 3");
        jPanel2.add(jButtonOptionThree, new org.netbeans.lib.awtextra.AbsoluteConstraints(276, 11, 114, 88));

        jButtonOptionFour.setText("Option 4");
        jPanel2.add(jButtonOptionFour, new org.netbeans.lib.awtextra.AbsoluteConstraints(408, 11, 114, 88));

        jButtonOptionFive.setText("Option 5");
        jPanel2.add(jButtonOptionFive, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 11, 114, 88));

        jButtonOptionOne.setText("Option 1");
        jPanel2.add(jButtonOptionOne, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 114, 88));

        jButtonOptionSix.setText("Option 6");
        jPanel2.add(jButtonOptionSix, new org.netbeans.lib.awtextra.AbsoluteConstraints(664, 11, 114, 88));

        jButtonOptionTwo.setText("Option 2");
        jPanel2.add(jButtonOptionTwo, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 11, 114, 88));

        jTextArea.setEditable(false);
        jTextArea.setColumns(20);
        jTextArea.setLineWrap(true);
        jTextArea.setRows(5);
        jScrollPane1.setViewportView(jTextArea);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
        );

        jProgressBarPlayerHP.setForeground(new java.awt.Color(255, 0, 51));
        jProgressBarPlayerHP.setStringPainted(true);

        jProgressBarPlayerFatigue.setForeground(new java.awt.Color(51, 153, 0));
        jProgressBarPlayerFatigue.setStringPainted(true);

        jLabelEnemyHP.setText("Enemy");

        jProgressBarEnemyHP.setForeground(new java.awt.Color(255, 0, 51));
        jProgressBarEnemyHP.setStringPainted(true);

        jProgressBarEnemyFatigue.setForeground(new java.awt.Color(51, 153, 0));
        jProgressBarEnemyFatigue.setStringPainted(true);

        jLabelPlayerHP.setText("Player");

        javax.swing.GroupLayout jPanelHPBarsLayout = new javax.swing.GroupLayout(jPanelHPBars);
        jPanelHPBars.setLayout(jPanelHPBarsLayout);
        jPanelHPBarsLayout.setHorizontalGroup(
            jPanelHPBarsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHPBarsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelHPBarsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBarPlayerHP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBarPlayerFatigue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBarEnemyHP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBarEnemyFatigue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelHPBarsLayout.createSequentialGroup()
                        .addGroup(jPanelHPBarsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelEnemyHP)
                            .addComponent(jLabelPlayerHP))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelHPBarsLayout.setVerticalGroup(
            jPanelHPBarsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHPBarsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelPlayerHP)
                .addGap(12, 12, 12)
                .addComponent(jProgressBarPlayerHP, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBarPlayerFatigue, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jLabelEnemyHP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBarEnemyHP, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBarEnemyFatigue, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelPicture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanelHPBars, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelPicture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelHPBars, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new MainGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rpgGame.GUI.ImagePanel imagePanel;
    private javax.swing.JButton jButtonOptionFive;
    private javax.swing.JButton jButtonOptionFour;
    private javax.swing.JButton jButtonOptionOne;
    private javax.swing.JButton jButtonOptionSix;
    private javax.swing.JButton jButtonOptionThree;
    private javax.swing.JButton jButtonOptionTwo;
    private javax.swing.JButton jButtonQuit;
    private javax.swing.JLabel jLabelEnemyHP;
    private javax.swing.JLabel jLabelPlayerHP;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelHPBars;
    private javax.swing.JPanel jPanelPicture;
    private javax.swing.JProgressBar jProgressBarEnemyFatigue;
    private javax.swing.JProgressBar jProgressBarEnemyHP;
    private javax.swing.JProgressBar jProgressBarPlayerFatigue;
    private javax.swing.JProgressBar jProgressBarPlayerHP;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea;
    // End of variables declaration//GEN-END:variables
}
