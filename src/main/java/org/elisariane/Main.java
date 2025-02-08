package org.elisariane;

import org.elisariane.models.User;
import org.elisariane.processor.FileProcessor;
import org.elisariane.utils.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Logger LOGGER = LoggerFactory.getLogger(Main.class);
        if (args.length == 0) {
            LOGGER.info("Por favor, informe o caminho do arquivo ou diretório.");
            return;
        }
        String path = args[0];
        try {
            List<User> users = FileProcessor.processFileOrDirectory(path);

            BufferedWriter writer = new BufferedWriter(new FileWriter("output-file.txt"));
            writer.write(JsonConverter.toJson(users));
            writer.close();

        } catch (IOException e) {
            LOGGER.error("Erro ao processar o arquivo ou diretório: {}", e.getMessage(), e);
        }
    }
}
