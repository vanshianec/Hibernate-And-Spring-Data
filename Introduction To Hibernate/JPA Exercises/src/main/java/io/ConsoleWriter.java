package io;

import contracts.Writer;

public class ConsoleWriter implements Writer {

    public ConsoleWriter() {
    }

    @Override
    public void writeLine(String text) {
        System.out.println(text);
    }
}
