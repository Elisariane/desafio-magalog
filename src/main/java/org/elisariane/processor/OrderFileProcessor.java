package org.elisariane.processor;

import org.elisariane.aggregator.OrderAggregator;
import org.elisariane.dtos.OrderLine;
import org.elisariane.exceptions.UnparseableLineException;
import org.elisariane.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class OrderFileProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderFileProcessor.class);
    private static final OrderLineParser ORDER_FILE_PROCESSOR = new OrderLineParser();
    private static final OrderAggregator ORDER_AGGREGATOR = new OrderAggregator();

    public static List<User> processFileOrDirectory(String pathStr) throws IOException {
        Path path = Paths.get(pathStr);
        List<User> users = new ArrayList<>();

        if (Files.isDirectory(path)) {
            processDirectory(path, users);
        } else if (Files.isRegularFile(path)) {
            users.addAll(processFile(path.toFile()));
        } else {
            LOGGER.error("Caminho inválido: {}", pathStr);
            throw new FileNotFoundException("Caminho não encontrado ou inválido: {}" + pathStr);
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
            throw new NoSuchFileException("Arquivos dentro do diretório: {} não foram encontrados" + directory);
        }

    }

    private static List<User> processFile(File file) throws IOException {
        LOGGER.info("Lendo arquivo: {}", file.getAbsolutePath());
        try (var lines = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
            lines.forEach(line -> {
                LOGGER.info("Linha do arquivo: {}", line);
                OrderLine orderLine = ORDER_FILE_PROCESSOR.parse(line);

                ORDER_AGGREGATOR.aggregate(orderLine);

            });
        } catch (UnparseableLineException unparseableLineException) {
            throw new UnparseableLineException("Erro ao tentar converter linha: {}" + unparseableLineException.getMessage());
        }

        return ORDER_AGGREGATOR.getAggregatedUsers();
    }
}
