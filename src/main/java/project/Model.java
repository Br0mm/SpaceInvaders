package project;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.io.IOException;

public class Model {
    enum Direction {
        RIGHT,
        LEFT,
        NOWHERE;
    }

    static final int width = 510;
    static final int height = 500;
    static boolean gameOver;
    private static int globalCounter = 0;
    static String nickname = "Player";
    static Node motherShip;
    static Node defendersAttack;
    static ObservableList<Node> invadersTeam;
    static ObservableList<Node> invadersAttack;
    static Direction motherShipDirection = Direction.NOWHERE;
    private static int invadersMovesCounter = 0;
    private static int attackCounter = 0;

    public static void moveMotherShip() throws IOException {
        globalCounter++;
        double motherShipX = motherShip.getTranslateX();

        switch (motherShipDirection) {
            case LEFT:
                if (motherShipX > 0)
                    motherShip.setTranslateX(motherShipX - 10);
                break;
            case RIGHT:
                if (motherShipX < 460)
                    motherShip.setTranslateX(motherShipX + 10);
                break;
        }

        if (globalCounter % 6 == 0) {
            moveInvader();
            //баланс количества атак от количества вторженцев
            if (invadersTeam.size() <= 14) {
                attackCounter++;
                if (attackCounter == 16 - invadersTeam.size()) {
                 attackCounter = 0;
                 createInvaderAttack();
                }
            } else createInvaderAttack();
        }
        motherShipAttack();
        invadersAttack();
        gameOverCheck();
    }

    private static void moveInvader() {
        invadersMovesCounter++;
        if (invadersMovesCounter <= 13) {
            for (Node invader : invadersTeam) {
                if (invadersMovesCounter != 13) invader.setTranslateX(invader.getTranslateX() + 10);
                else invader.setTranslateY(invader.getTranslateY() + 20);
            }
        } else {
            for (Node invader : invadersTeam) {
                if (invadersMovesCounter != 26) invader.setTranslateX(invader.getTranslateX() - 10);
                else invader.setTranslateY(invader.getTranslateY() + 20);
            }
        }
        if (invadersMovesCounter == 26) invadersMovesCounter = 0;
    }

    private static void motherShipAttack() {
        if (defendersAttack.getTranslateY() <= -20) return;
        defendersAttack.setTranslateY(defendersAttack.getTranslateY() - 10);
        //как вариант заменить foreach на явный итератор и убирать через него
        boolean shoot = false;
        int brokenInvader = -1;
        for (Node invader : invadersTeam) {
            brokenInvader++;
            if (defendersAttack.getTranslateX() >= invader.getTranslateX() && defendersAttack.getTranslateX() <= invader.getTranslateX() + 20)
                if (defendersAttack.getTranslateY() > invader.getTranslateY() && defendersAttack.getTranslateY() <= invader.getTranslateY() + 20) {
                    shoot = true;
                    break;
                }
        }
        if (shoot) {
            invadersTeam.remove(brokenInvader);
            defendersAttack.setTranslateY(-20);
        }
    }

    private static void invadersAttack() {
        if (invadersAttack.size() == 0) return;
        for (Node attack : invadersAttack) {
            attack.setTranslateY(attack.getTranslateY() + 10);
        }
        //как вариант заменить foreach на явный итератор и убирать через него
        invadersAttack.removeIf(i -> i.getTranslateY() > 500);
    }

    private static void gameOverCheck() {
        double highestY = 0;
        for (Node invader : invadersTeam) {
            if (invader.getTranslateY() > highestY) highestY = invader.getTranslateY();
        }
        if (highestY >= 440.0) Controller.gameOver();
        for (Node attack : invadersAttack) {
            if(attack.getTranslateX() == motherShip.getTranslateX() || attack.getTranslateX() == motherShip.getTranslateX() + 40)
                if (attack.getTranslateY() >= 480 && attack.getTranslateY() <= 500)
                    Controller.gameOver();
            if(attack.getTranslateX() == motherShip.getTranslateX() + 10 || attack.getTranslateX() == motherShip.getTranslateX() + 30)
                if (attack.getTranslateY() >= 470 && attack.getTranslateY() <= 500)
                    Controller.gameOver();
            if(attack.getTranslateX() == motherShip.getTranslateX() + 20)
                if (attack.getTranslateY() >= 460 && attack.getTranslateY() <= 500)
                    Controller.gameOver();
        }
    }

    private static void createInvaderAttack() throws IOException {
        Node invader = invadersTeam.get((int) Math.floor(Math.random() * invadersTeam.size()));
        Node enemyAttack = View.createAttack();
        enemyAttack.setTranslateY(invader.getTranslateY() + 30);
        enemyAttack.setTranslateX(invader.getTranslateX() + 10);
        invadersAttack.add(enemyAttack);
    }
}
