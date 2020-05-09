package it.polimi.ingsw.PSP38.common;

/**
 * Enumeration which represents the types of messages that can be sent by the server to the clients:<br>
 *     -<code>NOTIFY_MESSAGE</code> is used if the server needs to send to one or more client a notification message
 *     (e. g. if someone has won the game)<br>
 *     -<code>ASK_INT</code> is used if the server needs to use an integer defined by the user through the client<br>
 *     -<code>ASK_STRING</code> is used if the server needs to use a string defined by the user through the client<br>
 *     -<code>DISPLAY_BOARD</code> is used when the server asks the client to show the game board to the user
 */
public enum Protocol {
    NOTIFY_MESSAGE,
    ASK_INT,
    ASK_STRING,
    DISPLAY_BOARD
}
