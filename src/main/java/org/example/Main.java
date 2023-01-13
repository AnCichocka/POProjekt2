package org.example;

import java.awt.event.KeyAdapter;
        import java.awt.event.KeyEvent;
        import javax.swing.JFrame;
        import javax.swing.JTextField;

public class Main {

    public static void main(String[] argv) throws Exception {

        // initialize map
        int numberOfPokemons = 1;
        int numberOfRocks = 5;
        RectangularMap map = new RectangularMap(4,4, numberOfPokemons, numberOfRocks);
        Pokemon pokemon = map.getMyPokemon();

// frame
        JFrame jframe = new JFrame();
        jframe.addKeyListener(new MKeyListener(map, pokemon));
        jframe.setSize(200, 100);
        jframe.setVisible(true);

        System.out.println(map);
    }
}

class MKeyListener extends KeyAdapter {

    private RectangularMap map;
    private Pokemon pokemon;
    public MKeyListener(RectangularMap map, Pokemon pokemon){
        this.map = map;
        this.pokemon = pokemon;
    }
    @Override
    public void keyPressed(KeyEvent event) {

        int keyCode = event.getKeyCode();
        MoveDirection direction = MoveDirection.UP;

        switch (keyCode) {
            case 38 -> direction = MoveDirection.UP;
            case 40 -> direction = MoveDirection.DOWN;
            case 37 -> direction = MoveDirection.LEFT;
            case 39 -> direction = MoveDirection.RIGHT;
            default -> System.out.println("not arrow");
        }

//        System.out.println(keyCode);

        pokemon.move(direction);
        map.moveWildPokemons();
    }
}


