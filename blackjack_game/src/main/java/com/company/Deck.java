package com.company;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Deck {
    private ArrayList<Card> deck;       // Holds deck of cards
    private int deckSize;

    public Deck() {
        deck = new ArrayList<>();
        createDeck();   // Add cards into the deck

        shuffle();      // Shuffles cards in the deck
    }

    public void resetDeck() {      
        deckSize = deck.size() - 1;     // Reset size of the deck and shuffle
        shuffle();
    }

    public Card draw() {
        return deck.get(deckSize--);                // Return top card
    }

    // Shuffles all of the cards in the deck in a random order
    private void shuffle() {
        Random rand = new Random();     // Used to randomize swap order

        // Swap each card with a random card in the deck
        for (int x = 0; x < deck.size(); x++) {
            Card temp;
            int idx = rand.nextInt(52);

            temp = deck.remove(idx);
            deck.add(temp);

        }

        deckSize = deck.size() - 1;     // Reverts size of hand to default size
    }

    // Create a deck of all 52 playing cards with their scores
    private void createDeck() {

        deck.add(new Card("A-S", 1, true));
        deck.add(new Card("2-S", 2));
        deck.add(new Card("3-S", 3));
        deck.add(new Card("4-S", 4));
        deck.add(new Card("5-S", 5));
        deck.add(new Card("6-S", 6));
        deck.add(new Card("7-S", 7));
        deck.add(new Card("8-S", 8));
        deck.add(new Card("9-S", 9));
        deck.add(new Card("10-S", 10));
        deck.add(new Card("J-S", 10));
        deck.add(new Card("Q-S", 10));
        deck.add(new Card("K-S", 10));

        deck.add(new Card("A-C", 1, true));
        deck.add(new Card("2-C", 2));
        deck.add(new Card("3-C", 3));
        deck.add(new Card("4-C", 4));
        deck.add(new Card("5-C", 5));
        deck.add(new Card("6-C", 6));
        deck.add(new Card("7-C", 7));
        deck.add(new Card("8-C", 8));
        deck.add(new Card("9-C", 9));
        deck.add(new Card("10-C", 10));
        deck.add(new Card("J-C", 10));
        deck.add(new Card("Q-C", 10));
        deck.add(new Card("K-C", 10));

        deck.add(new Card("A-H", 1, true));
        deck.add(new Card("2-H", 2));
        deck.add(new Card("3-H", 3));
        deck.add(new Card("4-H", 4));
        deck.add(new Card("5-H", 5));
        deck.add(new Card("6-H", 6));
        deck.add(new Card("7-H", 7));
        deck.add(new Card("8-H", 8));
        deck.add(new Card("9-H", 9));
        deck.add(new Card("10-H", 10));
        deck.add(new Card("J-H", 10));
        deck.add(new Card("Q-H", 10));
        deck.add(new Card("K-H", 10));

        deck.add(new Card("A-D", 1, true));
        deck.add(new Card("2-D", 2));
        deck.add(new Card("3-D", 3));
        deck.add(new Card("4-D", 4));
        deck.add(new Card("5-D", 5));
        deck.add(new Card("6-D", 6));
        deck.add(new Card("7-D", 7));
        deck.add(new Card("8-D", 8));
        deck.add(new Card("9-D", 9));
        deck.add(new Card("10-D", 10));
        deck.add(new Card("J-D", 10));
        deck.add(new Card("Q-D", 10));
        deck.add(new Card("K-D", 10));

        deckSize = deck.size() - 1;
    }

    // Display contents of deck onto the console
    public void showDeck(Card[] deck) {
        for (Card c : deck) {
            System.out.println(c.getName());
        }
    }

}

class Card {    
    final private String name;      // Name of card
    final private int points;       // Points of card
    private boolean isAce = false;  // Special value if ace is in hand

    ImageIcon cardBackImage;         // Image of back of card
    ImageIcon cardImage;             // Image of the actual card

    public Card(String n, int p) {  // Default constructor
        name = n;
        points = p;

        // Set the default back image of the card
        this.cardBackImage = new ImageIcon(getClass().getResource("cards/BACK.png"));

        // Changes the resolution for the card image
        this.cardBackImage.setImage(cardBackImage.getImage().getScaledInstance(125, 182, Image.SCALE_DEFAULT));


        // Obtain file for the card image
        this.cardImage = new ImageIcon(getClass().getResource("cards/" + this.name + ".png"));

        // Changes the resolution for the card image
        this.cardImage.setImage(cardImage.getImage().getScaledInstance(125, 182, Image.SCALE_DEFAULT));
    }

    public Card(String n, int p, boolean a) {   // Constructor for Ace cards
        name = n;
        points = p;
        isAce = a;

        // Set the default back image of the card
        this.cardBackImage = new ImageIcon(getClass().getResource("cards/BACK.png"));

        // Changes the resolution for the card image
        this.cardBackImage.setImage(cardBackImage.getImage().getScaledInstance(125, 182, Image.SCALE_DEFAULT));


        // Obtain file for the card image
        this.cardImage = new ImageIcon(getClass().getResource("cards/" + this.name + ".png"));

        // Changes the resolution for the card image
        this.cardImage.setImage(cardImage.getImage().getScaledInstance(125, 182, Image.SCALE_DEFAULT));
    }

    public int getPoints() {    // Return card points
        return points;
    }

    public boolean isAce() {    // Returns true if card is ace
        return isAce;
    }

    public String getName() {   // Gets name of card
        return name;
    }

    public JLabel getCardLabel() {   // Returns card's image
        return new JLabel(this.cardImage);
    }

    public JLabel getCardBackLabel() {   // Return back of card
        return new JLabel(this.cardBackImage);
    }
}