package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.common.Message;

public interface MessageDecoder {
    void decodeMessage(Message m);
    void update();
}
