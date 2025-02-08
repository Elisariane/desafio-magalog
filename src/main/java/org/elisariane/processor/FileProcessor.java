package org.elisariane.processor;

import org.elisariane.dtos.OrderLine;
import org.elisariane.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class FileProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileProcessor.class);
    private static final LineParser lineParser = new LineParser();
    private static final DataAggregator dataAggregator = new DataAggregator();

    public static List<User> processFileOrDirectory(String pathStr) throws IOException {
        Path path = Paths.get(pathStr);
        List<User> users = new ArrayList<>();
        if (Files.isDirectory(path)) {
            processDirectory(path, users);
        } else if (Files.isRegularFile(path)) {
            users.addAll(processFile(path.toFile()));
        } else {
            LOGGER.error("Caminho inválido: {}", pathStr);
        }
        return users;
    }

    private static void processDirectory(Path path, List<User> users) throws IOException {
        File directory = path.toFile();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    LOGGER.info("Processando arquivo: {}", file.getName());
                    users.addAll(processFile(file));
                }
            }
        } else {
            LOGGER.error("O diretório não possui arquivos");
        }
    }

    private static List<User> processFile(File file) throws IOException {
        List<User> users = new ArrayList<>();
        LOGGER.info("Lendo arquivo: {}", file.getAbsolutePath());
        try (var lines = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
            lines.forEach(line -> {
                LOGGER.info("Linha do arquivo: {}", line);
                Optional<OrderLine> orderLine = lineParser.parseLine(line);
                if (orderLine.isPresent()) {
                    User user = dataAggregator.aggregate(orderLine.get());
                    users.add(user);
                } else {
                    LOGGER.error("Não foi possível ler a linha: {}", line);
                }
            });
        } catch (Exception e) {
            LOGGER.error("Houve um erro ao tentar processar as linhas do arquivo: {}", e.getMessage(), e);
        }
        return users;
    }
}
