package utils;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
    private final static Color SELECTED_CELL = Color.web("#00FFFF");
    private static final String BACKGROUND_COLOR_STYLE_STRING = "-fx-background-color: #";
    private static final String TEXT_COLOR_STYLE_STRING = "-fx-text-inner-color: #";
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

    public  boolean checkAvailableButton(Button btn) {
        String hexColor = btn.getStyle().split("#")[1];
        return !hexColor.equals(colorToHex(BUSY_COLOR_FREE_CELL)) && !hexColor.equals(colorToHex(BUSY_COLOR)) && !hexColor.equals(colorToHex(BLOCKED_COLOR));
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
        btn.setStyle(BACKGROUND_COLOR_STYLE_STRING + colorToHex(BUSY_COLOR_FREE_CELL));
    }



    public void setDefault(Button btn) {
        btn.setText("");
        btn.setGraphic(new ImageView(iconEmpty));
        btn.setStyle(BACKGROUND_COLOR_STYLE_STRING + colorToHex(DEFAULT_COLOR));
    }

    public void selectedCell(Button button) {

        button.setStyle(BACKGROUND_COLOR_STYLE_STRING + colorToHex(SELECTED_CELL));
    }

    public void setButtonBackground(Button button, String hexColor) {
        button.setStyle(BACKGROUND_COLOR_STYLE_STRING + hexColor);
    }

    public void setBusyStatus(Button btn) {
        btn.setGraphic(new ImageView(iconPallet));
        btn.setStyle(BACKGROUND_COLOR_STYLE_STRING + colorToHex(BUSY_COLOR));
        btn.setFont(new Font(0));
        // btn.setStyle(TEXT_COLOR_STYLE_STRING + colorToHex(BUSY_TEXT_COLOR));
    }
}
