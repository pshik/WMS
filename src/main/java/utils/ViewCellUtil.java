package utils;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.List;

public class ViewCellUtil {
    private static final int TEXT_SIZE = 9;
    private static final int HEIGHT = 29;

    private final static Color BUSY_COLOR = Color.web("#7B68EE");
    private final static Color BUSY_COLOR_FREE_CELL = Color.web("#C0C0C0");
    //private final static Color BUSY_TEXT_COLOR = Color.color();
    private final static Color BUSY_TEXT_COLOR = Color.web("#7B68EE");
    private final static Color AVAILABLE_CEll = Color.web("#32CD32");
    private final static Color DEFAULT_COLOR = Color.web("#FFE4E1");
    private final static Color HIGHLIGHT_FIFO = Color.web("#FFA500");
    private final static Color HIGHLIGHT = Color.web("#2E8B57");
    private final static Color BLOCKED_COLOR = Color.web("#000000");
    private static Image iconPallet = null;
    private static Image iconEmpty = null;

    private Color prevFillColorB1 =null;
    private Color prevTextColorB1 =null;
    private Color prevFillColorB2 =null;
    private Color prevTextColorB2 =null;
    private Color prevFillColorB3 =null;
    private Color prevTextColorB3 =null;
    private Color prevFillColorB4 =null;
    private Color prevTextColorB4 =null;
    private Color prevFillColorB5 =null;
    private Color prevTextColorB5 =null;
    private Color prevFillColorB6 =null;
    private Color prevTextColorB6 =null;


    public ViewCellUtil() {
        this.iconPallet = new Image(getClass().getResource("/icons/pallet.png").toString());
        this.iconEmpty = new Image(getClass().getResource("/icons/empty.png").toString());
    }
    String colorToHex(Color color) {
        String hex1;
        String hex2;

        hex1 = Integer.toHexString(color.hashCode()).toUpperCase();

        switch (hex1.length()) {
            case 2:
                hex2 = "000000";
                break;
            case 3:
                hex2 = String.format("00000%s", hex1.substring(0,1));
                break;
            case 4:
                hex2 = String.format("0000%s", hex1.substring(0,2));
                break;
            case 5:
                hex2 = String.format("000%s", hex1.substring(0,3));
                break;
            case 6:
                hex2 = String.format("00%s", hex1.substring(0,4));
                break;
            case 7:
                hex2 = String.format("0%s", hex1.substring(0,5));
                break;
            default:
                hex2 = hex1.substring(0, 6);
        }
        return hex2;
    }
    public void setDefaultText(Button button) {
        button.setText("");
    }

    public void setDefaultText(List<Button> buttons) {
        buttons.forEach(button -> button.setText(""));
    }

    public void setUnAvailable(Button btn){
        btn.setGraphic(new ImageView(iconEmpty));
        btn.setStyle("-fx-background-color: #" + colorToHex(BUSY_COLOR_FREE_CELL));

    }
//    public void selectCell(int i) {
//      //  prevNum = i;
//        switch (i){
//            case 0:
//                prevFillColorB1 = button1.getBackground();
//                prevTextColorB1 = button1.getForeground();
//                removeSelection();
//                button1.setBackground(Color.cyan);
//                button1.setForeground(Color.cyan);
//                break;
//            case 1:
//                prevFillColorB2 = button2.getBackground();
//                prevTextColorB2 = button2.getForeground();
//                removeSelection();
//                button2.setBackground(Color.cyan);
//                button2.setForeground(Color.cyan);
//                break;
//            case 2:
//                prevFillColorB3 = button3.getBackground();
//                prevTextColorB3 = button3.getForeground();
//                removeSelection();
//                button3.setBackground(Color.cyan);
//                button3.setForeground(Color.cyan);
//                break;
//            case 3:
//                prevFillColorB4 = button4.getBackground();
//                prevTextColorB4 = button4.getForeground();
//                removeSelection();
//                button4.setBackground(Color.cyan);
//                button4.setForeground(Color.cyan);
//                break;
//            case 4:
//                prevFillColorB5 = button5.getBackground();
//                prevTextColorB5 = button5.getForeground();
//                removeSelection();
//                button5.setBackground(Color.cyan);
//                button5.setForeground(Color.cyan);
//                break;
//            case 5:
//                prevFillColorB6 = button6.getBackground();
//                prevTextColorB6 = button6.getForeground();
//                removeSelection();
//                button6.setBackground(Color.cyan);
//                button6.setForeground(Color.cyan);
//                break;
//        }
//    }

//    public void removeSelection(){
//        if(button1.getBackground() == Color.cyan){
//            button1.setBackground(prevFillColorB1);
//            button1.setForeground(prevTextColorB1);
//        } else if (button2.getBackground() == Color.cyan) {
//            button2.setBackground(prevFillColorB2);
//            button2.setForeground(prevTextColorB2);
//        } else if (button3.getBackground() == Color.cyan) {
//            button3.setBackground(prevFillColorB3);
//            button3.setForeground(prevTextColorB3);
//        } else if (button4.getBackground() == Color.cyan) {
//            button4.setBackground(prevFillColorB4);
//            button4.setForeground(prevTextColorB4);
//        } else if (button5.getBackground() == Color.cyan) {
//            button5.setBackground(prevFillColorB5);
//            button5.setForeground(prevTextColorB5);
//        } else if (button6.getBackground() == Color.cyan) {
//            button6.setBackground(prevFillColorB6);
//            button6.setForeground(prevTextColorB6);
//        }
//    }



    public void setDefault(Button btn) {
        btn.setText("");
        btn.setGraphic(new ImageView(iconEmpty));
        btn.setStyle("-fx-background-color: #" + colorToHex(DEFAULT_COLOR));
    }
}
