package uk.co.hexeption.client.utils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import uk.co.hexeption.client.Client;
import uk.co.hexeption.client.event.Event;
import uk.co.hexeption.client.event.events.EventKeybind;
import uk.co.hexeption.client.event.events.EventKeyboard;
import uk.co.hexeption.client.managers.EventManager;
import uk.co.hexeption.client.mod.Mod;

/**
 * Created by Keir on 21/04/2017.
 */
public class InputHandler {

    public static void handleBind(boolean state, int key, char character) {

        EventKeybind event = new EventKeybind(Event.Type.PRE, state, key, character);
        EventManager.handleEvent(event);

        for (Mod mod : Client.INSTANCE.modManager.getMods()) {
            if (Keyboard.getEventKey() == mod.getBind()) {
                mod.toggle();
            }
        }

        event.setType(Event.Type.POST);
        EventManager.handleEvent(event);
    }

    public static void handleKeyboard() {

        boolean state = Keyboard.getEventKeyState();
        int key = Keyboard.getEventKey();
        char character = Keyboard.getEventCharacter();
        if (key != Keyboard.KEY_NONE) {
            handleBind(state, key, character);
        }
        EventKeyboard event = new EventKeyboard(Event.Type.PRE, key);
        EventManager.handleEvent(event);

    }

    public static void handleMouse() {

        int button = Mouse.getEventButton();
        boolean state = Mouse.getEventButtonState();

        if (button >= 0) {
            handleBind(state, button - 100, '\0');
        }
    }
}