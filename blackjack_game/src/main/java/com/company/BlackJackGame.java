package com.company;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BlackJackGame {

    BlackJackGame(JFrame frame) { 

        // Creates deck of cards
        Deck currDeck = new Deck();

        // Create panel and array list for the dealer's hand
        JPanel DealerPanel = new JPanel();
        DealerPanel.setLayout(new FlowLayout());
        ArrayList<Card> dealerHand = new ArrayList<>();

        // Create panel and array list for the player's hand
        JPanel PlayerPanel = new JPanel();
        DealerPanel.setLayout(new FlowLayout());
        ArrayList<Card> playerHand = new ArrayList<>();

        // Add panels to the frame
        frame.add(DealerPanel, BorderLayout.NORTH);
        frame.add(PlayerPanel, BorderLayout.CENTER);

        // Create JPanel for hit and stand buttons
        JPanel buttonPanel = new JPanel();

        // Create hit button
        JButton hit = new JButton("Hit!");
        hit.setBackground(Color.WHITE);
        
        // Create stand button
        JButton stand = new JButton("Stand!");
        stand.setBackground(Color.WHITE);

        // Create new game button
        JButton newGame = new JButton("New Game");
        newGame.setBackground(Color.WHITE);

        // If player presses the hit button, add a card onto their hand
        hit.addActionListener((ActionEvent e) -> {

            // Disable the button for a short while
            hit.setEnabled(false);

            // Draw a card from the deck and add it to the user's hand face down
            playerHand.add(currDeck.draw());
            PlayerPanel.add(playerHand.get(playerHand.size() - 1).getCardBackLabel());
            
            // Show changes to the player
            PlayerPanel.revalidate();
            PlayerPanel.repaint();
            
            // Reveal the card
            Timer showCard = new Timer(500, (ActionEvent e1) -> {
                // Removes face down card and sets it face up
                PlayerPanel.remove(PlayerPanel.getComponentCount() - 1);
                PlayerPanel.add(playerHand.get(playerHand.size() - 1).getCardLabel());
                
                // Show changes to the player
                PlayerPanel.revalidate();   
                PlayerPanel.repaint();

                // If the hand is over 21
                if (getScore(playerHand) > 21) {
                    showMessage("You have gone bust...", "Game Over!");
                    hit.setEnabled(false);
                    stand.setEnabled(false);
                    newGame.setVisible(true);
                }

                
            });

            // Enable the button
            hit.setEnabled(true);
            
            // Start the timer
            showCard.setRepeats(false);
            showCard.start();
            

        });

        // If the player presses the stand button, start dealer's turn
        stand.addActionListener((ActionEvent e) -> {

            // Disable both buttons while the dealer is playing
            stand.setEnabled(false);
            hit.setEnabled(false);

            // Reveal the card
            Timer showCard = new Timer(500, (ActionEvent e1) -> {
                
                // Dealer draws cards until their score is 17 or higher
                if (getScore(dealerHand) < 17) {
                    
                    // Adds card to the deck and the panel
                    dealerHand.add(currDeck.draw());
                    DealerPanel.add(dealerHand.get(dealerHand.size() - 1).getCardLabel());
                    DealerPanel.revalidate();
                    DealerPanel.repaint();
                    
                } else {
                    ((Timer) e1.getSource()).stop();

                    // If the dealer goes over 21, the player wins
                    if (getScore(dealerHand) > 21) {
                        showMessage("Dealer has gone bust! You win!", "Victory!");

                    } else {
                        // If the player has a higher score than the dealer, the player wins
                        if (getScore(playerHand) > getScore(dealerHand)) {
                            showMessage("You win!", "Victory");
                        } else {
                            // If the dealer has a higher score than the player, the dealer wins
                            if (getScore(playerHand) < getScore(dealerHand)) {
                                showMessage("Dealer wins!", "Game Over!");
                            } else {
                                // If the scores are equal, it is a draw
                                showMessage("It's a draw!", "Tie!");
                            }
                        }
                    }
                }
            });

            // Start the timer
            showCard.start();   

            // Reveal the dealer's hand
            DealerPanel.removeAll();
            for (Card c : dealerHand) {
                DealerPanel.add(c.getCardLabel());
            }
            DealerPanel.revalidate();
            DealerPanel.repaint();

            // Game ends, enable button allowing for a new game
            hit.setEnabled(false);
            stand.setEnabled(false);
            newGame.setVisible(true);

        });

        newGame.addActionListener((ActionEvent e) -> {
            currDeck.resetDeck();   // 'Adds' cards back into deck and reshuffles deck

            PlayerPanel.removeAll();    // Removes all cards from the panel
            playerHand.clear();         // Removes cards from array

            DealerPanel.removeAll();    // Removes all cards from the panel
            dealerHand.clear();         // Removes cards from array


            // Adds cards to the each frame to start the new game
            startGame(playerHand, dealerHand, currDeck, DealerPanel, PlayerPanel);

            // Refreshes the player and dealer panels to show new game
            PlayerPanel.repaint();
            DealerPanel.repaint();

            // Buttons are reenabled in order to start the new game (except for the new game button)
            hit.setEnabled(true);
            stand.setEnabled(true);
            newGame.setVisible(false);
        });

        // Adds buttons to the panel
        buttonPanel.add(hit);
        buttonPanel.add(stand);
        buttonPanel.add(newGame);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // New game button is not visible until the round is over
        newGame.setVisible(false);

        // Makes frame window visible
        frame.setVisible(true);

        // Gives cards to each player and starts the game
        startGame(playerHand, dealerHand, currDeck, DealerPanel, PlayerPanel);

    }

    void startGame(ArrayList<Card> playerHand,
    ArrayList<Card> dealerHand, Deck currDeck, JPanel DealerPanel, JPanel PlayerPanel) {

        // Add 2 cards to each player's hand
        playerHand.add(currDeck.draw());
        playerHand.add(currDeck.draw());
        dealerHand.add(currDeck.draw());
        dealerHand.add(currDeck.draw());

        // Give cards to each player
        for (Card c : dealerHand) {
            DealerPanel.add(c.getCardBackLabel());
        }
        for (Card c : playerHand) {
            PlayerPanel.add(c.getCardLabel());
        }
    }

    // Calculates user's score
    int getScore(ArrayList<Card> hand) {
        int score = 0;      // Holds card points (excluding aces)
        int numAces = 0;    // Total number of aces in hand
        ArrayList<Integer> scores = new ArrayList<>();      // Holds all possible ace values

        // Adds all scores of cards in deck
        for (Card c : hand) {
            if (c.isAce()) {
                numAces++;  // If card is ace, increase ace counter
            } else {
                score += c.getPoints(); // Otherwise, add points to total
            }
        }

        // If there are aces, calculate all possible points for the hand
        if (numAces > 0) {
            // Calculate each possible hand value
            for (int x = 0; x <= numAces; x++) {
                if (scores.get(x) <= numAces) {
                    return scores.get(x);
                }
            }

            // Return the biggest hand score that is smaller than or equal to 21
            for (int x = scores.size() - 1; x >=0; x--) {
                if (scores.get(x) <= 21) {
                    return scores.get(x);
                }
            } 

            // If none are smaller than 21, return smallest score
            return scores.get(0);
        }
        return score;   // Return calculated score
    }

    // Creates popup telling the player that they have lost
    void showMessage (String text, String title) {
        JFrame messageFrame = new JFrame(title);
        messageFrame.setSize(400, 200);
        messageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel messagePanel = new JPanel();
        JLabel message = new JLabel(text);
        messagePanel.add(message, BorderLayout.CENTER);

        messageFrame.add(messagePanel);
        messageFrame.setVisible(true);
    }
}